// AbroadHierarchyServiceImpl.java - REVISED
package com.abroad.Serviceimpl;

import com.abroad.DTO.*;
import com.abroad.Entity.*;
import com.abroad.Repository.*;
import com.abroad.Service.AbroadHierarchyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AbroadHierarchyServiceImpl implements AbroadHierarchyService {

    private final ContinentRepository continentRepo;
    private final CountryRepository countryRepo;
    private final StateRepository stateRepo;
    private final CityRepository cityRepo;
    private final UniversityRepository universityRepo;
    private final CollegeRepository collegeRepo;
    private final CourseRepository courseRepo;

    // ------------------------
    // ROOT HIERARCHY
    // ------------------------
    @Override
    public List<AbroadContinentDTO> getAllHierarchies() {
        return continentRepo.findAll().stream()
                .map(this::mapContinentToFullDTO)
                .toList();
    }

    @Override
    public AbroadContinentDTO getHierarchyByContinentId(Long continentId) {
        AbroadContinent continent = continentRepo.findById(continentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Continent not found with id: " + continentId));
        return mapContinentToFullDTO(continent);
    }

    // ------------------------
    // FILTERED HIERARCHY (Main Logic)
    // ------------------------
    @Override
    public List<AbroadContinentDTO> getFilteredHierarchy(
            Long continentId,
            List<String> continentNames,
            List<String> countryNames,
            List<String> stateNames,
            List<String> cityNames,
            List<String> universityNames,
            List<String> collegeNames,
            List<String> courseNames) {

        // Use Sets for efficient filtering and avoiding duplicates
        Set<AbroadCourse> matchedCourses = new HashSet<>();
        Set<AbroadCollege> matchedColleges = new HashSet<>();
        Set<AbroadUniversity> matchedUniversities = new HashSet<>();
        Set<AbroadCity> matchedCities = new HashSet<>();
        Set<AbroadState> matchedStates = new HashSet<>();
        Set<AbroadCountry> matchedCountries = new HashSet<>();
        Set<AbroadContinent> matchedContinents = new HashSet<>();

        // Flags to indicate if a filter was explicitly provided for each level
        boolean continentNameFilterProvided = continentNames != null && !continentNames.isEmpty();
        boolean continentIdFilterProvided = continentId != null;
        boolean countryNameFilterProvided = countryNames != null && !countryNames.isEmpty();
        boolean stateNameFilterProvided = stateNames != null && !stateNames.isEmpty();
        boolean cityNameFilterProvided = cityNames != null && !cityNames.isEmpty();
        boolean universityNameFilterProvided = universityNames != null && !universityNames.isEmpty();
        boolean collegeNameFilterProvided = collegeNames != null && !collegeNames.isEmpty();
        boolean courseNameFilterProvided = courseNames != null && !courseNames.isEmpty();

        boolean anyFilterApplied = continentIdFilterProvided || continentNameFilterProvided ||
                countryNameFilterProvided || stateNameFilterProvided ||
                cityNameFilterProvided || universityNameFilterProvided ||
                collegeNameFilterProvided || courseNameFilterProvided;

        // If no filter, return all hierarchies
        if (!anyFilterApplied) {
            return getAllHierarchies();
        }

        // Apply direct filters and expand upwards (child to parent)
        if (courseNameFilterProvided) {
            courseRepo.findByCourseNameIn(courseNames).forEach(course -> {
                matchedCourses.add(course);
                if (course.getAbroadCollege() != null) matchedColleges.add(course.getAbroadCollege());
            });
        }

        if (collegeNameFilterProvided) {
            collegeRepo.findByCollegeNameIn(collegeNames).forEach(college -> {
                matchedColleges.add(college);
                if (college.getAbroadUniversity() != null) matchedUniversities.add(college.getAbroadUniversity());
            });
        }

        if (universityNameFilterProvided) {
            universityRepo.findByUniversityNameIn(universityNames).forEach(university -> {
                matchedUniversities.add(university);
                if (university.getAbroadCity() != null) matchedCities.add(university.getAbroadCity());
            });
        }

        if (cityNameFilterProvided) {
            cityRepo.findByCityIn(cityNames).forEach(city -> {
                matchedCities.add(city);
                if (city.getAbroadState() != null) matchedStates.add(city.getAbroadState());
            });
        }

        if (stateNameFilterProvided) {
            stateRepo.findByStateIn(stateNames).forEach(state -> {
                matchedStates.add(state);
                if (state.getAbroadCountry() != null) matchedCountries.add(state.getAbroadCountry());
            });
        }

        if (countryNameFilterProvided) {
            countryRepo.findByCountryIn(countryNames).forEach(country -> {
                matchedCountries.add(country);
                if (country.getAbroadContinent() != null) matchedContinents.add(country.getAbroadContinent());
            });
        }

        if (continentNameFilterProvided) {
            matchedContinents.addAll(continentRepo.findByContinentnameIn(continentNames));
        }

        if (continentIdFilterProvided) {
            continentRepo.findById(continentId).ifPresent(matchedContinents::add);
        }

        // --- Propagate Upwards (from collected matches) ---
        // Courses -> Colleges -> Universities -> Cities -> States -> Countries -> Continents
        // This ensures parents are included for child-level filters
        if (!matchedCourses.isEmpty()) {
            matchedColleges.addAll(matchedCourses.stream().map(AbroadCourse::getAbroadCollege).collect(Collectors.toSet()));
        }
        if (!matchedColleges.isEmpty()) {
            matchedUniversities.addAll(matchedColleges.stream().map(AbroadCollege::getAbroadUniversity).collect(Collectors.toSet()));
        }
        if (!matchedUniversities.isEmpty()) {
            matchedCities.addAll(matchedUniversities.stream().map(AbroadUniversity::getAbroadCity).collect(Collectors.toSet()));
        }
        if (!matchedCities.isEmpty()) {
            matchedStates.addAll(matchedCities.stream().map(AbroadCity::getAbroadState).collect(Collectors.toSet()));
        }
        if (!matchedStates.isEmpty()) {
            matchedCountries.addAll(matchedStates.stream().map(AbroadState::getAbroadCountry).collect(Collectors.toSet()));
        }
        if (!matchedCountries.isEmpty()) {
            matchedContinents.addAll(matchedCountries.stream().map(AbroadCountry::getAbroadContinent).collect(Collectors.toSet()));
        }

        // --- Propagate Downwards (to fill in children for parent-level filters) ---
        // This is crucial for when only a parent (e.g., country) is filtered,
        // we still need to collect its children to pass to the mapping.
        Set<AbroadContinent> baseContinents = new HashSet<>(matchedContinents);

        if (countryNameFilterProvided || stateNameFilterProvided || cityNameFilterProvided || universityNameFilterProvided || collegeNameFilterProvided || courseNameFilterProvided) {
            // If any lower-level filter was applied, we only consider the matched countries, states, etc.
            // and their children. No need to re-add ALL children from parent.
        } else {
            // If only continent filters were applied, fetch all children for those continents
            matchedCountries.addAll(baseContinents.stream().flatMap(c -> c.getAbroadCountries().stream()).collect(Collectors.toSet()));
            matchedStates.addAll(matchedCountries.stream().flatMap(c -> c.getAbroadStates().stream()).collect(Collectors.toSet()));
            matchedCities.addAll(matchedStates.stream().flatMap(s -> s.getAbroadCities().stream()).collect(Collectors.toSet()));
            matchedUniversities.addAll(matchedCities.stream().flatMap(c -> c.getAbroadUniversities().stream()).collect(Collectors.toSet()));
            matchedColleges.addAll(matchedUniversities.stream().flatMap(u -> u.getAbroadColleges().stream()).collect(Collectors.toSet()));
            matchedCourses.addAll(matchedColleges.stream().flatMap(col -> col.getAbroadCourses().stream()).collect(Collectors.toSet()));
        }

        // Build DTO tree with strict branch restriction
        List<AbroadContinent> continentsToProcess = matchedContinents.stream().toList();

        return continentsToProcess.stream()
                .map(c -> mapFilteredToDTO(
                        c,
                        continentNameFilterProvided || continentIdFilterProvided, // Was continent explicitly filtered?
                        countryNameFilterProvided, matchedCountries,
                        stateNameFilterProvided, matchedStates,
                        cityNameFilterProvided, matchedCities,
                        universityNameFilterProvided, matchedUniversities,
                        collegeNameFilterProvided, matchedColleges,
                        courseNameFilterProvided, matchedCourses))
                .filter(java.util.Objects::nonNull) // Remove any continents that ended up empty after filtering children
                .toList();
    }


    // ------------------------
    // MAPPERS for full hierarchy (used by getAllHierarchies, getHierarchyByContinentId)
    // ------------------------
    private AbroadContinentDTO mapContinentToFullDTO(AbroadContinent continent) {
        List<AbroadCountryDTO> countries = continent.getAbroadCountries().stream()
                .map(this::mapCountryToFullDTO)
                .toList();
        return new AbroadContinentDTO(continent.getId(), continent.getContinentname(), countries);
    }

    private AbroadCountryDTO mapCountryToFullDTO(AbroadCountry country) {
        List<AbroadStateDTO> states = country.getAbroadStates().stream()
                .map(this::mapStateToFullDTO)
                .toList();
        return new AbroadCountryDTO(country.getId(), country.getCountry(), states);
    }

    private AbroadStateDTO mapStateToFullDTO(AbroadState state) {
        List<AbroadCityDTO> cities = state.getAbroadCities().stream()
                .map(this::mapCityToFullDTO)
                .toList();
        return new AbroadStateDTO(state.getId(), state.getState(), cities);
    }

    private AbroadCityDTO mapCityToFullDTO(AbroadCity city) {
        List<AbroadUniversityDTO> universities = city.getAbroadUniversities().stream()
                .map(this::mapUniversityToFullDTO)
                .toList();
        return new AbroadCityDTO(city.getId(), city.getCity(), universities);
    }

    private AbroadUniversityDTO mapUniversityToFullDTO(AbroadUniversity university) {
        List<AbroadCollegeDTO> colleges = university.getAbroadColleges().stream()
                .map(this::mapCollegeToFullDTO)
                .toList();
        return new AbroadUniversityDTO(university.getId(), university.getUniversityName(), colleges);
    }

    private AbroadCollegeDTO mapCollegeToFullDTO(AbroadCollege college) {
        List<AbroadCourseDTO> courses = college.getAbroadCourses().stream()
                .map(this::mapCourseToDTO)
                .toList();
        return new AbroadCollegeDTO(college.getId(), college.getCollegeName(), courses);
    }


    // ------------------------
    // MAPPERS for filtered hierarchy (with strict branch restriction)
    // ------------------------
    private AbroadContinentDTO mapFilteredToDTO(
            AbroadContinent continent,
            boolean continentFilterProvided, // Flag if continent filter was initially set
            boolean countryFilterProvided, Set<AbroadCountry> allowedCountries,
            boolean stateFilterProvided, Set<AbroadState> allowedStates,
            boolean cityFilterProvided, Set<AbroadCity> allowedCities,
            boolean universityFilterProvided, Set<AbroadUniversity> allowedUniversities,
            boolean collegeFilterProvided, Set<AbroadCollege> allowedColleges,
            boolean courseFilterProvided, Set<AbroadCourse> allowedCourses) {

        // Only process countries if the continent itself was directly targeted
        // OR if no continent filter was specified but this continent contains an allowed country.
        List<AbroadCountryDTO> countryDTOs = continent.getAbroadCountries().stream()
                // Check 1: Is this country in the 'allowed' set? (always true if no country filter was provided)
                .filter(country -> !countryFilterProvided || allowedCountries.contains(country))
                .map(country -> {
                    List<AbroadStateDTO> stateDTOs = country.getAbroadStates().stream()
                            .filter(state -> !stateFilterProvided || allowedStates.contains(state))
                            .map(state -> {
                                List<AbroadCityDTO> cityDTOs = state.getAbroadCities().stream()
                                        .filter(city -> !cityFilterProvided || allowedCities.contains(city))
                                        .map(city -> {
                                            List<AbroadUniversityDTO> universityDTOs = city.getAbroadUniversities().stream()
                                                    .filter(university -> !universityFilterProvided || allowedUniversities.contains(university))
                                                    .map(university -> {
                                                        List<AbroadCollegeDTO> collegeDTOs = university.getAbroadColleges().stream()
                                                                .filter(college -> !collegeFilterProvided || allowedColleges.contains(college))
                                                                .map(college -> {
                                                                    List<AbroadCourseDTO> courseDTOs = college.getAbroadCourses().stream()
                                                                            .filter(course -> !courseFilterProvided || allowedCourses.contains(course))
                                                                            .map(this::mapCourseToDTO)
                                                                            .toList();

                                                                    // Only return college if it was directly targeted OR it has matching courses
                                                                    return (!collegeFilterProvided && courseDTOs.isEmpty()) ? null :
                                                                            (courseDTOs.isEmpty() && !allowedColleges.contains(college)) ? null :
                                                                                    new AbroadCollegeDTO(college.getId(), college.getCollegeName(), courseDTOs);
                                                                })
                                                                .filter(java.util.Objects::nonNull)
                                                                .toList();

                                                        // Only return university if it was directly targeted OR it has matching colleges/courses
                                                        return (!universityFilterProvided && collegeDTOs.isEmpty()) ? null :
                                                                (collegeDTOs.isEmpty() && !allowedUniversities.contains(university)) ? null :
                                                                        new AbroadUniversityDTO(university.getId(), university.getUniversityName(), collegeDTOs);
                                                    })
                                                    .filter(java.util.Objects::nonNull)
                                                    .toList();

                                            // Only return city if it was directly targeted OR it has matching universities/colleges/courses
                                            return (!cityFilterProvided && universityDTOs.isEmpty()) ? null :
                                                    (universityDTOs.isEmpty() && !allowedCities.contains(city)) ? null :
                                                            new AbroadCityDTO(city.getId(), city.getCity(), universityDTOs);
                                        })
                                        .filter(java.util.Objects::nonNull)
                                        .toList();

                                // Only return state if it was directly targeted OR it has matching cities/universities/colleges/courses
                                return (!stateFilterProvided && cityDTOs.isEmpty()) ? null :
                                        (cityDTOs.isEmpty() && !allowedStates.contains(state)) ? null :
                                                new AbroadStateDTO(state.getId(), state.getState(), cityDTOs);
                            })
                            .filter(java.util.Objects::nonNull)
                            .toList();

                    // Only return country if it was directly targeted OR it has matching states/cities/universities/colleges/courses
                    return (!countryFilterProvided && stateDTOs.isEmpty()) ? null :
                            (stateDTOs.isEmpty() && !allowedCountries.contains(country)) ? null :
                                    new AbroadCountryDTO(country.getId(), country.getCountry(), stateDTOs);
                })
                .filter(java.util.Objects::nonNull)
                .toList();

        // Only return continent if it was directly targeted OR it has matching children after all filtering
        return (!continentFilterProvided && countryDTOs.isEmpty()) ? null :
                new AbroadContinentDTO(continent.getId(), continent.getContinentname(), countryDTOs);
    }


    // Mapper for Course entity to DTO (used by both full and filtered mappings)
    private AbroadCourseDTO mapCourseToDTO(AbroadCourse course) {
        return new AbroadCourseDTO(
                course.getId(),
                course.getCourseName(),
                course.getTutionFees(),
                course.getApplicationFees(),
                course.getDescription(),
                course.getDate(),
                course.getDuration(),
                course.getInstituteRank(),
                course.getThumbnail(),
                course.getIntake(),
                course.getWebsiteLink(),
                course.getAcademicRequirements(),
                course.getEnglishExamRequirements(),
                course.getExamScore(),
                course.getCity(),
                course.getLocation(),
                course.getAdditionalRequirements(),
                course.getCourseDetials(),
                course.getScholarship(),
                course.getHostel(),
                course.getHostelFees(),
                course.getContractType(),
                course.getExamType(),
                course.getImage(),
                course.getApplicationLink(),
                course.getStreamName(),
                course.getTutionFeesINR(),
                course.getFeesINR(),
                course.getCreatedByEmail(),
                course.getRole()
        );
    }
}