package com.abroad.Serviceimpl;

import com.abroad.DTO.*;
import com.abroad.Entity.*;
import com.abroad.Repository.*;
import com.abroad.Service.AbroadHierarchyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

    @Override
    public AbroadContinentDTO getHierarchyByContinentId(Long continentId) {
        return null;
    }

    // ------------------------
    // ROOT HIERARCHY
    // ------------------------
    @Override
    public List<AbroadContinentDTO> getAllHierarchies() {
        return continentRepo.findAll().stream()
                .map(this::mapToDTO)
                .toList();
    }

    // ------------------------
    // FILTERED HIERARCHY
    // ------------------------
    @Override
    public List<AbroadContinentDTO> getContinentsHierarchy(List<String> continentNames) {
        List<AbroadContinent> continents = continentRepo.findByContinentnameIn(continentNames);
        return continents.stream().map(this::mapToDTO).toList();
    }

    @Override
    public List<AbroadCountryDTO> getCountriesHierarchy(List<String> countryNames) {
        List<AbroadCountry> countries = countryRepo.findByCountryIn(countryNames);
        return countries.stream().map(c -> mapCountryToDTO(c, true)).toList();
    }

    @Override
    public List<AbroadStateDTO> getStatesHierarchy(List<String> stateNames) {
        List<AbroadState> states = stateRepo.findByStateIn(stateNames);
        return states.stream().map(s -> mapStateToDTO(s, true)).toList();
    }

    @Override
    public List<AbroadCityDTO> getCitiesHierarchy(List<String> cityNames) {
        List<AbroadCity> cities = cityRepo.findByCityIn(cityNames);
        return cities.stream().map(c -> mapCityToDTO(c, true)).toList();
    }

    @Override
    public List<AbroadUniversityDTO> getUniversitiesHierarchy(List<String> universityNames) {
        List<AbroadUniversity> universities = universityRepo.findByUniversityNameIn(universityNames);
        return universities.stream().map(u -> mapUniversityToDTO(u, true)).toList();
    }

    @Override
    public List<AbroadCollegeDTO> getCollegesHierarchy(List<String> collegeNames) {
        List<AbroadCollege> colleges = collegeRepo.findByCollegeNameIn(collegeNames);
        return colleges.stream().map(col -> mapCollegeToDTO(col, true)).toList();
    }

    // ------------------------
    // MAPPERS (Deep)
    // ------------------------
    private AbroadContinentDTO mapToDTO(AbroadContinent continent) {
        List<AbroadCountryDTO> countries = continent.getAbroadCountries().stream()
                .map(c -> mapCountryToDTO(c, true))
                .toList();
        return new AbroadContinentDTO(continent.getId(), continent.getContinentname(), countries);
    }

    private AbroadCountryDTO mapCountryToDTO(AbroadCountry country, boolean deep) {
        List<AbroadStateDTO> states = deep
                ? country.getAbroadStates().stream().map(s -> mapStateToDTO(s, true)).toList()
                : List.of();
        return new AbroadCountryDTO(country.getId(), country.getCountry(), states);
    }

    private AbroadStateDTO mapStateToDTO(AbroadState state, boolean deep) {
        List<AbroadCityDTO> cities = deep
                ? state.getAbroadCities().stream().map(c -> mapCityToDTO(c, true)).toList()
                : List.of();
        return new AbroadStateDTO(state.getId(), state.getState(), cities);
    }

    private AbroadCityDTO mapCityToDTO(AbroadCity city, boolean deep) {
        List<AbroadUniversityDTO> universities = deep
                ? city.getAbroadUniversities().stream().map(u -> mapUniversityToDTO(u, true)).toList()
                : List.of();
        return new AbroadCityDTO(city.getId(), city.getCity(), universities);
    }

    private AbroadUniversityDTO mapUniversityToDTO(AbroadUniversity university, boolean deep) {
        List<AbroadCollegeDTO> colleges = deep
                ? university.getAbroadColleges().stream().map(col -> mapCollegeToDTO(col, true)).toList()
                : List.of();
        return new AbroadUniversityDTO(university.getId(), university.getUniversityName(), colleges);
    }

    private AbroadCollegeDTO mapCollegeToDTO(AbroadCollege college, boolean deep) {
        List<AbroadCourseDTO> courses = deep
                ? college.getAbroadCourses().stream().map(course -> new AbroadCourseDTO(
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
        )).toList()
                : List.of();
        return new AbroadCollegeDTO(college.getId(), college.getCollegeName(), courses);
    }
    @Override
    public List<AbroadCourseDTO> getCoursesHierarchy(List<String> courseNames) {
        List<AbroadCourse> courses = courseRepo.findByCourseNameIn(courseNames);
        return courses.stream().map(course -> new AbroadCourseDTO(
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
        )).toList();
    }

    ///___________________________________________________________________________

    @Override
    public List<AbroadContinentDTO> getFilteredHierarchy(
            List<String> continentNames,
            List<String> countryNames,
            List<String> stateNames,
            List<String> cityNames,
            List<String> universityNames,
            List<String> collegeNames,
            List<String> courseNames) {

        // 1. Find matching entities
        Set<AbroadCourse> courses = (courseNames != null && !courseNames.isEmpty())
                ? new HashSet<>(courseRepo.findByCourseNameIn(courseNames))
                : new HashSet<>();

        Set<AbroadCollege> colleges = (collegeNames != null && !collegeNames.isEmpty())
                ? new HashSet<>(collegeRepo.findByCollegeNameIn(collegeNames))
                : courses.stream().map(AbroadCourse::getAbroadCollege).collect(Collectors.toSet());

        Set<AbroadUniversity> universities = (universityNames != null && !universityNames.isEmpty())
                ? new HashSet<>(universityRepo.findByUniversityNameIn(universityNames))
                : colleges.stream().map(AbroadCollege::getAbroadUniversity).collect(Collectors.toSet());

        Set<AbroadCity> cities = (cityNames != null && !cityNames.isEmpty())
                ? new HashSet<>(cityRepo.findByCityIn(cityNames))
                : universities.stream().map(AbroadUniversity::getAbroadCity).collect(Collectors.toSet());

        Set<AbroadState> states = (stateNames != null && !stateNames.isEmpty())
                ? new HashSet<>(stateRepo.findByStateIn(stateNames))
                : cities.stream().map(AbroadCity::getAbroadState).collect(Collectors.toSet());

        Set<AbroadCountry> countries = (countryNames != null && !countryNames.isEmpty())
                ? new HashSet<>(countryRepo.findByCountryIn(countryNames))
                : states.stream().map(AbroadState::getAbroadCountry).collect(Collectors.toSet());

        Set<AbroadContinent> continents = (continentNames != null && !continentNames.isEmpty())
                ? new HashSet<>(continentRepo.findByContinentnameIn(continentNames))
                : countries.stream().map(AbroadCountry::getAbroadContinent).collect(Collectors.toSet());

        if (continents.isEmpty() && countries.isEmpty() && states.isEmpty()
                && cities.isEmpty() && universities.isEmpty()
                && colleges.isEmpty() && courses.isEmpty()) {
            // nothing matched → return empty list
            return List.of();
        }

        // 2. Map to DTO tree but keep only relevant children
        return continents.stream()
                .map(c -> mapFilteredToDTO(c, countries, states, cities, universities, colleges, courses))
                .toList();
    }
    private AbroadContinentDTO mapFilteredToDTO(
            AbroadContinent continent,
            Set<AbroadCountry> allowedCountries,
            Set<AbroadState> allowedStates,
            Set<AbroadCity> allowedCities,
            Set<AbroadUniversity> allowedUniversities,
            Set<AbroadCollege> allowedColleges,
            Set<AbroadCourse> allowedCourses) {

        List<AbroadCountryDTO> countryDTOs = continent.getAbroadCountries().stream()
                .filter(c -> allowedCountries.isEmpty() || allowedCountries.contains(c))
                .map(country -> {
                    List<AbroadStateDTO> stateDTOs = country.getAbroadStates().stream()
                            .filter(s -> allowedStates.isEmpty() || allowedStates.contains(s))
                            .map(state -> {
                                List<AbroadCityDTO> cityDTOs = state.getAbroadCities().stream()
                                        .filter(ci -> allowedCities.isEmpty() || allowedCities.contains(ci))
                                        .map(city -> {
                                            List<AbroadUniversityDTO> universityDTOs = city.getAbroadUniversities().stream()
                                                    .filter(u -> allowedUniversities.isEmpty() || allowedUniversities.contains(u))
                                                    .map(university -> {
                                                        List<AbroadCollegeDTO> collegeDTOs = university.getAbroadColleges().stream()
                                                                .filter(co -> allowedColleges.isEmpty() || allowedColleges.contains(co))
                                                                .map(college -> {
                                                                    List<AbroadCourseDTO> courseDTOs = college.getAbroadCourses().stream()
                                                                            .filter(cou -> allowedCourses.isEmpty() || allowedCourses.contains(cou))
                                                                            .map(this::mapCourseToDTO)
                                                                            .toList();
                                                                    return new AbroadCollegeDTO(college.getId(), college.getCollegeName(), courseDTOs);
                                                                }).toList();
                                                        return new AbroadUniversityDTO(university.getId(), university.getUniversityName(), collegeDTOs);
                                                    }).toList();
                                            return new AbroadCityDTO(city.getId(), city.getCity(), universityDTOs);
                                        }).toList();
                                return new AbroadStateDTO(state.getId(), state.getState(), cityDTOs);
                            }).toList();
                    return new AbroadCountryDTO(country.getId(), country.getCountry(), stateDTOs);
                }).toList();

        return new AbroadContinentDTO(continent.getId(), continent.getContinentname(), countryDTOs);
    }

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
