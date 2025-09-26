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
import java.util.Objects;
import java.util.Set;
import java.util.ArrayList;
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

    // Mapper for Continent entity to DTO (used for full hierarchy)
    private AbroadContinentDTO mapContinentToFullDTO(AbroadContinent continent) {
        List<AbroadCountryDTO> countryDTOs = continent.getAbroadCountries().stream()
                .map(country -> {
                    List<AbroadStateDTO> stateDTOs = country.getAbroadStates().stream()
                            .map(state -> {
                                List<AbroadCityDTO> cityDTOs = state.getAbroadCities().stream()
                                        .map(city -> {
                                            List<AbroadUniversityDTO> universityDTOs = city.getAbroadUniversities().stream()
                                                    .map(university -> {
                                                        List<AbroadCollegeDTO> collegeDTOs = university.getAbroadColleges().stream()
                                                                .map(college -> {
                                                                    List<AbroadCourseDTO> courseDTOs = college.getAbroadCourses().stream()
                                                                            .map(this::mapCourseToDTO)
                                                                            .collect(Collectors.toList());
                                                                    return new AbroadCollegeDTO(college.getId(), college.getCollegeName(), courseDTOs);
                                                                })
                                                                .collect(Collectors.toList());
                                                        return new AbroadUniversityDTO(university.getId(), university.getUniversityName(), collegeDTOs);
                                                    })
                                                    .collect(Collectors.toList());
                                                return new AbroadCityDTO(city.getId(), city.getCity(), universityDTOs);
                                            })
                                            .collect(Collectors.toList());
                                    return new AbroadStateDTO(state.getId(), state.getState(), cityDTOs);
                                })
                                .collect(Collectors.toList());
                        return new AbroadCountryDTO(country.getId(), country.getCountry(), stateDTOs);
                    })
                    .collect(Collectors.toList());
        return new AbroadContinentDTO(continent.getId(), continent.getContinentname(), countryDTOs);
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
            List<String> courseNames,
            List<String> streamNames,       // 
            String scholarship,             // 
            String feesRange,               // 
            List<String> examTypes          // 
    ) {
        // Use Sets for efficient filtering and avoiding duplicates
        Set<AbroadCourse> matchedCourses = new HashSet<>();
        Set<AbroadCollege> matchedColleges = new HashSet<>();
        Set<AbroadUniversity> matchedUniversities = new HashSet<>();
        Set<AbroadCity> matchedCities = new HashSet<>();
        Set<AbroadState> matchedStates = new HashSet<>();
        Set<AbroadCountry> matchedCountries = new HashSet<>();
        Set<AbroadContinent> matchedContinents = new HashSet<>();

        // Flags
        boolean continentNameFilterProvided = continentNames != null && !continentNames.isEmpty();
        boolean continentIdFilterProvided = continentId != null;
        boolean countryNameFilterProvided = countryNames != null && !countryNames.isEmpty();
        boolean stateNameFilterProvided = stateNames != null && !stateNames.isEmpty();
        boolean cityNameFilterProvided = cityNames != null && !cityNames.isEmpty();
        boolean universityNameFilterProvided = universityNames != null && !universityNames.isEmpty();
        boolean collegeNameFilterProvided = collegeNames != null && !collegeNames.isEmpty();
        boolean courseNameFilterProvided = courseNames != null && !courseNames.isEmpty();
        boolean streamNameFilterProvided = streamNames != null && !streamNames.isEmpty();   // 
        boolean scholarshipFilterProvided = scholarship != null && !scholarship.isEmpty();  // 
        boolean feesRangeFilterProvided = feesRange != null && !feesRange.isEmpty();        // 
        boolean examTypeFilterProvided = examTypes != null && !examTypes.isEmpty();         // 

        boolean anyFilterApplied =
                continentIdFilterProvided || continentNameFilterProvided ||
                        countryNameFilterProvided || stateNameFilterProvided ||
                        cityNameFilterProvided || universityNameFilterProvided ||
                        collegeNameFilterProvided || courseNameFilterProvided ||
                        streamNameFilterProvided || scholarshipFilterProvided ||
                        feesRangeFilterProvided || examTypeFilterProvided;

        if (!anyFilterApplied) {
            return getAllHierarchies();
        }

        // ----------------
        // Course filters
        // ----------------
        if (courseNameFilterProvided) {
            courseRepo.findByCourseNameIn(courseNames).forEach(course -> {
                matchedCourses.add(course);
                if (course.getAbroadCollege() != null) matchedColleges.add(course.getAbroadCollege());
            });
        }

        if (streamNameFilterProvided) {
            courseRepo.findByStreamNameIn(streamNames).forEach(course -> {
                matchedCourses.add(course);
                if (course.getAbroadCollege() != null) matchedColleges.add(course.getAbroadCollege());
            });
        }

        if (scholarshipFilterProvided) {
            String normalized = scholarship.trim().equalsIgnoreCase("Yes") ? "Yes" : "No";
            courseRepo.findByScholarshipIgnoreCase(normalized).forEach(course -> {
                matchedCourses.add(course);
                if (course.getAbroadCollege() != null) matchedColleges.add(course.getAbroadCollege());
            });
        }

        if (feesRangeFilterProvided) {
            try {
                String[] parts = feesRange.split("-");
                double min = Double.parseDouble(parts[0].trim());
                double max = Double.parseDouble(parts[1].trim());
                courseRepo.findAll().stream()
                        .filter(c -> c.getTutionFeesINR() != null)
                        .filter(c -> {
                            try {
                                double fee = Double.parseDouble(c.getTutionFeesINR().replaceAll("[^0-9]", ""));
                                return fee >= min && fee <= max;
                            } catch (NumberFormatException e) {
                                return false;
                            }
                        })
                        .forEach(course -> {
                            matchedCourses.add(course);
                            if (course.getAbroadCollege() != null) matchedColleges.add(course.getAbroadCollege());
                        });
            } catch (Exception e) {
                System.out.println("Invalid feesRange format. Use 'min-max'");
            }
        }

        if (examTypeFilterProvided) {
            courseRepo.findByExamTypeIn(examTypes).forEach(course -> {
                matchedCourses.add(course);
                if (course.getAbroadCollege() != null) matchedColleges.add(course.getAbroadCollege());
            });
        }

        // ----------------
        // Higher-level filters (same as your code)
        // ----------------
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

        // ----------------
        // Propagate upwards (same as your code)
        // ----------------
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

        // ----------------
        // Propagate downwards (same as your code)
        // ----------------
        Set<AbroadContinent> baseContinents = new HashSet<>(matchedContinents);
        if (!(countryNameFilterProvided || stateNameFilterProvided || cityNameFilterProvided ||
                universityNameFilterProvided || collegeNameFilterProvided || courseNameFilterProvided ||
                streamNameFilterProvided || scholarshipFilterProvided || feesRangeFilterProvided || examTypeFilterProvided)) {
            matchedCountries.addAll(baseContinents.stream().flatMap(c -> c.getAbroadCountries().stream()).collect(Collectors.toSet()));
            matchedStates.addAll(matchedCountries.stream().flatMap(c -> c.getAbroadStates().stream()).collect(Collectors.toSet()));
            matchedCities.addAll(matchedStates.stream().flatMap(s -> s.getAbroadCities().stream()).collect(Collectors.toSet()));
            matchedUniversities.addAll(matchedCities.stream().flatMap(c -> c.getAbroadUniversities().stream()).collect(Collectors.toSet()));
            matchedColleges.addAll(matchedUniversities.stream().flatMap(u -> u.getAbroadColleges().stream()).collect(Collectors.toSet()));
            matchedCourses.addAll(matchedColleges.stream().flatMap(col -> col.getAbroadCourses().stream()).collect(Collectors.toSet()));
        }

        // Build DTO tree
        List<AbroadContinent> continentsToProcess = matchedContinents.stream().toList();
        return continentsToProcess.stream()
                .map(c -> mapFilteredToDTO(
                        c,
                        continentNameFilterProvided || continentIdFilterProvided,
                        countryNameFilterProvided, matchedCountries,
                        stateNameFilterProvided, matchedStates,
                        cityNameFilterProvided, matchedCities,
                        universityNameFilterProvided, matchedUniversities,
                        collegeNameFilterProvided, matchedColleges,
                        courseNameFilterProvided || streamNameFilterProvided ||
                                scholarshipFilterProvided || feesRangeFilterProvided || examTypeFilterProvided,
                        matchedCourses,
                        streamNames != null ? streamNames : List.of(),
                        scholarship != null ? scholarship : "",
                        examTypes != null ? examTypes : List.of(),
                        feesRangeFilterProvided ? parseFeesRanges(feesRange) : List.of()
                ))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }



    // ------------------------
    // MAPPERS for filtered hierarchy (with strict branch restriction)
    // ------------------------
    private AbroadContinentDTO mapFilteredToDTO(
            AbroadContinent continent,
            boolean continentFilterProvided,
            boolean countryFilterProvided, Set<AbroadCountry> allowedCountries,
            boolean stateFilterProvided, Set<AbroadState> allowedStates,
            boolean cityFilterProvided, Set<AbroadCity> allowedCities,
            boolean universityFilterProvided, Set<AbroadUniversity> allowedUniversities,
            boolean collegeFilterProvided, Set<AbroadCollege> allowedColleges,
            boolean courseFilterProvided, Set<AbroadCourse> allowedCourses,
            List<String> streamNames,
            String scholarship,
            List<String> examTypes,
            List<int[]> feesRanges // each int[] is {min, max}
    ) {
        List<AbroadCountryDTO> countryDTOs = continent.getAbroadCountries().stream()
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
                                                                            // --- New filtering ---
                                                                            .filter(course -> {
                                                                                boolean streamOk = (streamNames == null || streamNames.isEmpty()) ||
                                                                                        streamNames.contains(course.getStreamName());
                                                                                boolean scholarshipOk = (scholarship == null || scholarship.isEmpty()) ||
                                                                                        scholarship.equalsIgnoreCase(course.getScholarship());
                                                                                boolean examOk = (examTypes == null || examTypes.isEmpty()) ||
                                                                                        examTypes.contains(course.getExamType());
                                                                                boolean feesOk = (feesRanges == null || feesRanges.isEmpty()) ||
                                                                                        feesRanges.stream().anyMatch(r -> {
                                                                                            try {
                                                                                                double fee = Double.parseDouble(course.getTutionFeesINR().replaceAll("[^0-9.]", ""));
                                                                                                return fee >= r[0] && fee <= r[1];
                                                                                            } catch (NumberFormatException | NullPointerException e) {
                                                                                                return false;
                                                                                            }
                                                                                        });
                                                                                return streamOk && scholarshipOk && examOk && feesOk;
                                                                            })
                                                                            .filter(course -> !courseFilterProvided || allowedCourses.contains(course))
                                                                            .map(this::mapCourseToDTO)
                                                                            .toList();

                                                                    return (!collegeFilterProvided && courseDTOs.isEmpty()) ? null :
                                                                            (courseDTOs.isEmpty() && !allowedColleges.contains(college)) ? null :
                                                                                    new AbroadCollegeDTO(college.getId(), college.getCollegeName(), courseDTOs);
                                                                })
                                                                .filter(java.util.Objects::nonNull)
                                                                .toList();

                                                        return (!universityFilterProvided && collegeDTOs.isEmpty()) ? null :
                                                                (collegeDTOs.isEmpty() && !allowedUniversities.contains(university)) ? null :
                                                                        new AbroadUniversityDTO(university.getId(), university.getUniversityName(), collegeDTOs);
                                                    })
                                                    .filter(java.util.Objects::nonNull)
                                                    .toList();

                                            return (!cityFilterProvided && universityDTOs.isEmpty()) ? null :
                                                    (universityDTOs.isEmpty() && !allowedCities.contains(city)) ? null :
                                                            new AbroadCityDTO(city.getId(), city.getCity(), universityDTOs);
                                        })
                                        .filter(java.util.Objects::nonNull)
                                        .toList();

                                return (!stateFilterProvided && cityDTOs.isEmpty()) ? null :
                                        (cityDTOs.isEmpty() && !allowedStates.contains(state)) ? null :
                                                new AbroadStateDTO(state.getId(), state.getState(), cityDTOs);
                            })
                            .filter(java.util.Objects::nonNull)
                            .toList();

                    return (!countryFilterProvided && stateDTOs.isEmpty()) ? null :
                            (stateDTOs.isEmpty() && !allowedCountries.contains(country)) ? null :
                                    new AbroadCountryDTO(country.getId(), country.getCountry(), stateDTOs);
                })
                .filter(java.util.Objects::nonNull)
                .toList();

        return (!continentFilterProvided && countryDTOs.isEmpty()) ? null :
                new AbroadContinentDTO(continent.getId(), continent.getContinentname(), countryDTOs);
    }



    // Helper method to parse fee ranges from string format (e.g., "1000-2000,3000-4000")
    private List<int[]> parseFeesRanges(String feesRange) {
        if (feesRange == null || feesRange.trim().isEmpty()) {
            return List.of();
        }
        
        List<int[]> ranges = new ArrayList<>();
        String[] parts = feesRange.split(",");
        
        for (String part : parts) {
            try {
                String[] minMax = part.split("-");
                if (minMax.length == 2) {
                    int min = Integer.parseInt(minMax[0].trim());
                    int max = Integer.parseInt(minMax[1].trim());
                    ranges.add(new int[]{min, max});
                }
            } catch (NumberFormatException e) {
                // Skip invalid range formats
                continue;
            }
        }
        return ranges;
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