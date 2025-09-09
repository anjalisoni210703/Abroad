package com.abroad.Serviceimpl;

import com.abroad.DTO.*;
import com.abroad.Entity.*;
import com.abroad.Repository.*;
import com.abroad.Service.AbroadHierarchyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

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
        AbroadContinent continent = continentRepo.findById(continentId)
                .orElseThrow(() -> new RuntimeException("Continent not found"));
        return mapToDTO(continent);
    }

    @Override
    public List<AbroadContinentDTO> getAllHierarchies() {
        List<AbroadContinent> continents = continentRepo.findAll();
        return continents.stream().map(this::mapToDTO).toList();
    }

    private AbroadContinentDTO mapToDTO(AbroadContinent continent) {
        List<AbroadCountryDTO> countryDTOs = continent.getAbroadCountries().stream().map(country -> {
            List<AbroadStateDTO> stateDTOs = country.getAbroadStates().stream().map(state -> {
                List<AbroadCityDTO> cityDTOs = state.getAbroadCities().stream().map(city -> {
                    List<AbroadUniversityDTO> universityDTOs = city.getAbroadUniversities().stream().map(university -> {
                        List<AbroadCollegeDTO> collegeDTOs = university.getAbroadColleges().stream().map(college -> {
                            List<AbroadCourseDTO> courseDTOs = college.getAbroadCourses().stream().map(course ->
                                    new AbroadCourseDTO(
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
                                    )
                            ).toList();
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

    @Override
    public Page<AbroadContinent> getAllContinents(Pageable pageable) {
        return continentRepo.findAll(pageable);
    }

    @Override
    public Page<AbroadCountry> getCountriesByContinentName(String continentName, Pageable pageable) {
        var continent = continentRepo.findByContinentname(continentName)
                .orElseThrow(() -> new RuntimeException("Continent not found: " + continentName));
        return countryRepo.findByAbroadContinentId(continent.getId(), pageable);
    }

    @Override
    public Page<AbroadState> getStatesByCountryName(String countryName, Pageable pageable) {
        var country = countryRepo.findByCountry(countryName)
                .orElseThrow(() -> new RuntimeException("Country not found: " + countryName));
        return stateRepo.findByAbroadCountryId(country.getId(), pageable);
    }

    @Override
    public Page<AbroadCity> getCitiesByStateName(String stateName, Pageable pageable) {
        var state = stateRepo.findByState(stateName)
                .orElseThrow(() -> new RuntimeException("State not found: " + stateName));
        return cityRepo.findByAbroadStateId(state.getId(), pageable);
    }

    @Override
    public Page<AbroadUniversity> getUniversitiesByCityName(String cityName, Pageable pageable) {
        var city = cityRepo.findByCity(cityName)
                .orElseThrow(() -> new RuntimeException("City not found: " + cityName));
        return universityRepo.findByAbroadCityId(city.getId(), pageable);
    }

    @Override
    public Page<AbroadCollege> getCollegesByUniversityName(String universityName, Pageable pageable) {
        var university = universityRepo.findByUniversityName(universityName)
                .orElseThrow(() -> new RuntimeException("University not found: " + universityName));
        return collegeRepo.findByAbroadUniversityId(university.getId(), pageable);
    }

    @Override
    public Page<AbroadCourse> getCoursesByCollegeName(String collegeName, Pageable pageable) {
        var college = collegeRepo.findByCollegeName(collegeName)
                .orElseThrow(() -> new RuntimeException("College not found: " + collegeName));
        return courseRepo.findByAbroadCollegeId(college.getId(), pageable);
    }
}
