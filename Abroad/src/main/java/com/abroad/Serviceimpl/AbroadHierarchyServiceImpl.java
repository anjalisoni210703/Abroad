package com.abroad.Serviceimpl;

import com.abroad.DTO.*;
import com.abroad.Entity.*;
import com.abroad.Repository.*;
import com.abroad.Service.AbroadHierarchyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AbroadHierarchyServiceImpl implements AbroadHierarchyService {

    private final ContinentRepository continentRepository;
    @Autowired
    private  ContinentRepository continentRepo;
    @Autowired
    private  CountryRepository countryRepo;
    @Autowired
    private  StateRepository stateRepo;
    @Autowired
    private  CityRepository cityRepo;
    @Autowired
    private  UniversityRepository universityRepo;
    @Autowired
    private  CollegeRepository collegeRepo;
    @Autowired
    private  CourseRepository courseRepo;

    @Override
    public AbroadContinentDTO getHierarchyByContinentId(Long continentId) {
        AbroadContinent continent = continentRepository.findById(continentId)
                .orElseThrow(() -> new RuntimeException("Continent not found"));
        return mapToDTO(continent);
    }

    @Override
    public List<AbroadContinentDTO> getAllHierarchies() {
        List<AbroadContinent> continents = continentRepository.findAll();
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
//                                            course.getAbroadCollege() != null ? course.getAbroadCollege().getId() : null
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
    public Page<AbroadCountry> getCountriesByContinent(Long continentId, Pageable pageable) {
        return countryRepo.findByAbroadContinentId(continentId, pageable);
    }

    @Override
    public Page<AbroadState> getStatesByCountry(Long countryId, Pageable pageable) {
        return stateRepo.findByAbroadCountryId(countryId, pageable);
    }

    @Override
    public Page<AbroadCity> getCitiesByState(Long stateId, Pageable pageable) {
        return cityRepo.findByAbroadStateId(stateId, pageable);
    }

    @Override
    public Page<AbroadUniversity> getUniversitiesByCity(Long cityId, Pageable pageable) {
        return universityRepo.findByAbroadCityId(cityId, pageable);
    }

    @Override
    public Page<AbroadCollege> getCollegesByUniversity(Long universityId, Pageable pageable) {
        return collegeRepo.findByAbroadUniversityId(universityId, pageable);
    }

    @Override
    public Page<AbroadCourse> getCoursesByCollege(Long collegeId, Pageable pageable) {
        return courseRepo.findByAbroadCollegeId(collegeId, pageable);
    }

}