package com.abroad.Serviceimpl;

import com.abroad.DTO.*;
import com.abroad.Entity.AbroadContinent;
import com.abroad.Repository.ContinentRepository;
import com.abroad.Service.AbroadHierarchyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AbroadHierarchyServiceImpl implements AbroadHierarchyService {

    private final ContinentRepository continentRepository;

    @Override
    public AbroadContinentDTO getHierarchyByContinentId(Long continentId) {
        AbroadContinent continent = continentRepository.findById(continentId)
                .orElseThrow(() -> new RuntimeException("Continent not found"));

        List<AbroadCountryDTO> countryDTOs = continent.getAbroadCountries().stream().map(country -> {
            List<AbroadStateDTO> stateDTOs = country.getAbroadStates().stream().map(state -> {
                List<AbroadCityDTO> cityDTOs = state.getAbroadCities().stream().map(city -> {
                    List<AbroadUniversityDTO> universityDTOs = city.getAbroadUniversities().stream().map(university -> {
                        List<AbroadCollegeDTO> collegeDTOs = university.getAbroadColleges().stream().map(college -> {
                            List<AbroadCourseDTO> courseDTOs = college.getAbroadCourses().stream().map(course -> {
                                return new AbroadCourseDTO(course.getId(), course.getCourseName(), course.getTutionFees(), course.getApplicationFees());
                            }).toList();
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
}
