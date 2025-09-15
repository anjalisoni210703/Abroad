    package com.abroad.Serviceimpl;

    import com.abroad.Entity.*;
    import com.abroad.Repository.*;
    import com.abroad.Service.PermissionService;
    import com.abroad.Service.S3Service;
    import jakarta.persistence.criteria.Predicate;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.data.domain.Page;
    import org.springframework.data.domain.PageRequest;
    import org.springframework.data.domain.Pageable;
    import org.springframework.data.jpa.domain.Specification;
    import org.springframework.security.access.AccessDeniedException;
    import org.springframework.stereotype.Service;
    import org.springframework.web.multipart.MultipartFile;

    import java.io.IOException;
    import java.time.LocalDate;
    import java.util.ArrayList;
    import java.util.HashMap;
    import java.util.List;
    import java.util.Map;
    import java.util.stream.Collectors;

    @Service
    public class EnquiryServiceImpl implements com.abroad.Service.EnquiryService {
        @Autowired
        private EnquiryRepository repository;

        @Autowired
        private PermissionService permissionService;

        @Autowired
        private ContinentRepository continentRepository;

        @Autowired
        private CountryRepository countryRepository;

        @Autowired
        private UniversityRepository universityRepository;

        @Autowired
        private CollegeRepository collegeRepository;

        @Autowired
        private StreamRepository streamRepository;

        @Autowired
        private CourseRepository courseRepository;

        @Autowired
        private StateRepository stateRepository;

        @Autowired
        private CityRepository cityRepository;

        @Autowired
        private S3Service s3Service;


        @Override
        public AbroadEnquiry createEnquiry(AbroadEnquiry abroadEnquiry, MultipartFile image, MultipartFile document1, MultipartFile document2,
                                           String role, String email, Long continentId, Long countryId, Long universityId,
                                           Long stateId, Long cityId, Long collegeId) {

            if (!permissionService.hasPermission(role, email, "POST")) {
                throw new AccessDeniedException("No permission to create Enquiry");
            }

            String branchCode = permissionService.fetchBranchCode(role, email);

            try {
                if (image != null && !image.isEmpty()) {
                    String imageUrl = s3Service.uploadImage(image);
                    abroadEnquiry.setPhotoUrl(imageUrl);
                }

                if (document1 != null && !document1.isEmpty()) {
                    String doc1Url = s3Service.uploadImage(document1);
                    abroadEnquiry.setDocument1(doc1Url);
                }

                if (document2 != null && !document2.isEmpty()) {
                    String doc2Url = s3Service.uploadImage(document2);
                    abroadEnquiry.setDocument2(doc2Url);
                }

            } catch (IOException e) {
                throw new RuntimeException("Failed to upload file(s)", e);
            }

            abroadEnquiry.setEnquiry_date(LocalDate.now());
            abroadEnquiry.setCreatedByEmail(email);
            abroadEnquiry.setRole(role);
            abroadEnquiry.setBranchCode(branchCode);

            AbroadContinent continent = continentRepository.findById(continentId).orElseThrow(() -> new RuntimeException("Continent not found"));
            abroadEnquiry.setAbroadContinent(continent);
            abroadEnquiry.setContinent(continent.getContinentname());

            AbroadCountry country = countryRepository.findById(countryId).orElseThrow(() -> new RuntimeException("Country not found"));
            abroadEnquiry.setCountry(country.getCountry());
            abroadEnquiry.setAbroadCountry(country);

            AbroadUniversity university = universityRepository.findById(universityId).orElseThrow(() -> new RuntimeException("University not found"));
            abroadEnquiry.setUniversity(university.getUniversityName());
            abroadEnquiry.setAbroadUniversity(university);

//            AbroadCourse course = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found"));
//            abroadEnquiry.setCourse(course.getCourseName());
//            abroadEnquiry.setAbroadCourse(course);

            AbroadCollege college = collegeRepository.findById(collegeId).orElseThrow(() -> new RuntimeException("College not found"));
            abroadEnquiry.setCollage(college.getCollegeName());
            abroadEnquiry.setAbroadCollege(college);

            AbroadState state = stateRepository.findById(stateId).orElseThrow(() -> new RuntimeException("State not found"));
            abroadEnquiry.setState(state.getState());
            abroadEnquiry.setAbroadState(state);

            AbroadCity city = cityRepository.findById(cityId).orElseThrow(() -> new RuntimeException("City not found"));
            abroadEnquiry.setCity(city.getCity());
            abroadEnquiry.setAbroadCity(city);

            return repository.save(abroadEnquiry);
        }


        @Override
        public List<AbroadEnquiry> getAllEnquiries(String role, String email) {
            if (!permissionService.hasPermission(role, email, "GET")) {
                throw new AccessDeniedException("No permission to view Enquiries");
            }

            String branchCode = permissionService.fetchBranchCode(role, email);
            return repository.findAllByBranchCode(branchCode);
        }

        @Override
        public AbroadEnquiry getEnquiryById(Long id, String role, String email) {
            if (!permissionService.hasPermission(role, email, "GET")) {
                throw new AccessDeniedException("No permission to view Enquiry");
            }

            return repository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Enquiry not found"));
        }

        @Override
        public AbroadEnquiry updateEnquiry(Long id, AbroadEnquiry abroadEnquiry, MultipartFile image, MultipartFile document1, MultipartFile document2,
                                           String role, String email) {

            if (!permissionService.hasPermission(role, email, "PUT")) {
                throw new AccessDeniedException("No permission to update Enquiry");
            }

            AbroadEnquiry existing = repository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Enquiry not found"));
            existing.setName(abroadEnquiry.getName() != null ? abroadEnquiry.getName() : existing.getName());
            existing.setPhone_no(abroadEnquiry.getPhone_no() != null ? abroadEnquiry.getPhone_no() : existing.getPhone_no());
            existing.setEmail(abroadEnquiry.getEmail() != null ? abroadEnquiry.getEmail() : existing.getEmail());
//            existing.setEnquiry_date(abroadEnquiry.getEnquiry_date() != null ? abroadEnquiry.getEnquiry_date() : existing.getEnquiry_date());
            existing.setEnquiry_date(LocalDate.now());
            existing.setAddress(abroadEnquiry.getAddress() != null ? abroadEnquiry.getAddress() : existing.getAddress());
            existing.setPercentage(abroadEnquiry.getPercentage()!=0 ? abroadEnquiry.getPercentage():existing.getPercentage());
            existing.setState(abroadEnquiry.getState() != null ? abroadEnquiry.getState() : existing.getState());
            existing.setPassoutYear(abroadEnquiry.getPassoutYear() !=null ? abroadEnquiry.getPassoutYear() : existing.getPassoutYear());
            existing.setFathersIncome(abroadEnquiry.getFathersIncome() !=0 ? abroadEnquiry.getFathersIncome():existing.getFathersIncome());
            existing.setFathersOccupation(abroadEnquiry.getFathersOccupation() != null? abroadEnquiry.getFathersOccupation():existing.getFathersOccupation());
            existing.setGender(abroadEnquiry.getGender()!=null ? abroadEnquiry.getGender():existing.getGender());
            existing.setDob(abroadEnquiry.getDob()!=null ? abroadEnquiry.getDob():existing.getDob());
            existing.setPassoutCourse(abroadEnquiry.getPassoutCourse() !=null? abroadEnquiry.getPassoutCourse():existing.getPassoutCourse());
            existing.setCity(abroadEnquiry.getCity() !=null ? abroadEnquiry.getCity():existing.getCity());
            existing.setPincode(abroadEnquiry.getPincode() !=null ? abroadEnquiry.getPincode():existing.getPincode());
            existing.setPassportNo(abroadEnquiry.getPassportNo() !=null ? abroadEnquiry.getPassportNo():existing.getPassportNo());
            existing.setApplyFor(abroadEnquiry.getApplyFor() !=null ? abroadEnquiry.getApplyFor(): existing.getApplyFor());
            existing.setSource(abroadEnquiry.getSource() !=null ? abroadEnquiry.getSource():existing.getSource());
            existing.setFatherNumber(abroadEnquiry.getFatherNumber()!=null?abroadEnquiry.getFatherNumber():existing.getFatherNumber());
            existing.setLoanRequirement(abroadEnquiry.getLoanRequirement()!=null?abroadEnquiry.getLoanRequirement():existing.getLoanRequirement());
            existing.setAmount(abroadEnquiry.getAmount()!=null?abroadEnquiry.getAmount():existing.getAmount());
            existing.setRemark(abroadEnquiry.getRemark()!=null?abroadEnquiry.getRemark():existing.getRemark());
            existing.setYear(abroadEnquiry.getYear()!=null?abroadEnquiry.getYear():existing.getYear());
            existing.setConductBy(abroadEnquiry.getConductBy()!=null?abroadEnquiry.getConductBy():existing.getConductBy());
            existing.setGap(abroadEnquiry.getGap()!=null?abroadEnquiry.getGap():existing.getGap());
            existing.setGapYear(abroadEnquiry.getGapYear()!=null?abroadEnquiry.getGapYear():existing.getGapYear());
            existing.setEntranceExam(abroadEnquiry.getEntranceExam()!=null?abroadEnquiry.getEntranceExam():existing.getEntranceExam());
            existing.setScore(abroadEnquiry.getScore()!=null?abroadEnquiry.getScore():existing.getScore());
            existing.setFatherITR(abroadEnquiry.getFatherITR()!=null?abroadEnquiry.getFatherITR():existing.getFatherITR());
            existing.setAmountITR(abroadEnquiry.getAmountITR()!=null?abroadEnquiry.getAmountITR():existing.getAmountITR());
            existing.setYearITR(abroadEnquiry.getYearITR()!=null?abroadEnquiry.getYearITR():existing.getYearITR());
            existing.setHasPassport(abroadEnquiry.getHasPassport()!=null?abroadEnquiry.getHasPassport():existing.getHasPassport());
            existing.setStatus(abroadEnquiry.getStatus()!=null?abroadEnquiry.getStatus():existing.getStatus());
            try {
                if (image != null && !image.isEmpty()) {
                    if (existing.getPhotoUrl() != null) {
                        s3Service.deleteImage(existing.getPhotoUrl());
                    }
                    existing.setPhotoUrl(s3Service.uploadImage(image));
                }

                if (document1 != null && !document1.isEmpty()) {
                    if (existing.getDocument1() != null) {
                        s3Service.deleteImage(existing.getDocument1());
                    }
                    existing.setDocument1(s3Service.uploadImage(document1));
                }

                if (document2 != null && !document2.isEmpty()) {
                    if (existing.getDocument2() != null) {
                        s3Service.deleteImage(existing.getDocument2());
                    }
                    existing.setDocument2(s3Service.uploadImage(document2));
                }

            } catch (IOException e) {
                throw new RuntimeException("Failed to upload/update documents", e);
            }

                return repository.save(existing);
        }

        @Override
        public void deleteEnquiry(Long id, String role, String email) {
            if (!permissionService.hasPermission(role, email, "DELETE")) {
                throw new AccessDeniedException("No permission to delete Enquiry");
            }

            AbroadEnquiry existing = repository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Enquiry not found"));

            if (existing.getPhotoUrl() != null) {
                s3Service.deleteImage(existing.getPhotoUrl());
            }

            repository.deleteById(id);
        }

        @Override
        public Page<AbroadEnquiry> filterEnquiries(
                String continent, String country, String stream, String course, String status,
                String branchCode, String role, String email, String fullName,
                String enquiryDateFilter, LocalDate startDate, LocalDate endDate,
                String applyFor,
                String conductBy,// NEW PARAM
                int page, int size) {

            if (!permissionService.hasPermission(role, email, "POST"))
                throw new AccessDeniedException("No permission");

            Specification<AbroadEnquiry> spec = (root, query, cb) -> {
                List<Predicate> predicates = new ArrayList<>();

                if (branchCode != null && !branchCode.equalsIgnoreCase("All")) {
                    predicates.add(cb.equal(root.get("branchCode"), branchCode));
                }

                if (continent != null) predicates.add(cb.equal(root.get("continent"), continent));
                if (country != null) predicates.add(cb.equal(root.get("country"), country));
                if (stream != null) predicates.add(cb.equal(root.get("stream"), stream));
                if (course != null) predicates.add(cb.equal(root.get("course"), course));
                if (status != null) predicates.add(cb.equal(root.get("status"), status));
                if (applyFor != null) predicates.add(cb.equal(root.get("applyFor"), applyFor)); // <-- NEW
                if (conductBy != null) predicates.add(cb.equal(root.get("conductBy"), conductBy)); // <-- NEW

                if (fullName != null && !fullName.trim().isEmpty()) {
                    predicates.add(cb.like(cb.lower(root.get("name")), "%" + fullName.toLowerCase() + "%"));
                }

                if (enquiryDateFilter != null) {
                    LocalDate today = LocalDate.now();
                    switch (enquiryDateFilter.toLowerCase()) {
                        case "today" -> predicates.add(cb.equal(root.get("enquiry_date"), today));
                        case "last7days" -> predicates.add(cb.between(root.get("enquiry_date"), today.minusDays(6), today));
                        case "last30days" -> predicates.add(cb.between(root.get("enquiry_date"), today.minusDays(29), today));
                        case "last365days" -> predicates.add(cb.between(root.get("enquiry_date"), today.minusDays(364), today));
                        case "custom" -> {
                            if (startDate != null && endDate != null) {
                                predicates.add(cb.between(root.get("enquiry_date"), startDate, endDate));
                            }
                        }
                    }
                }

                query.orderBy(cb.desc(root.get("enquiry_date")));
                return cb.and(predicates.toArray(new Predicate[0]));
            };

            Pageable pageable = PageRequest.of(page, size);
            return repository.findAll(spec, pageable);
        }


        @Override
        public List<Map<String, Object>> getInquiryCountByCourseAsMap(String branchCode) {
            List<Object[]> results = repository.countInquiriesByCourseNameAndBranch(branchCode);

            return results.stream()
                    .map(result -> {
                        Map<String, Object> map = new HashMap<>();
                        map.put("courseName", result[0]);
                        map.put("inquiryCount", result[1]);
                        return map;
                    })
                    .collect(Collectors.toList());
        }

        @Override
        public List<Map<String, Object>> getInquiryCountByStreamAsMap(String branchCode) {
            List<Object[]> results = repository.countInquiriesByStreamAndBranch(branchCode);

            return results.stream()
                    .map(result -> {
                        Map<String, Object> map = new HashMap<>();
                        map.put("streamName", result[0]);
                        map.put("inquiryCount", result[1]);
                        return map;
                    })
                    .collect(Collectors.toList());
        }

    }
