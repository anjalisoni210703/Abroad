    package com.abroad.Serviceimpl;

    import com.abroad.Entity.*;
    import com.abroad.Repository.*;
    import com.abroad.Service.PermissionService;
    import com.abroad.Service.S3Service;
    import jakarta.persistence.criteria.Predicate;
    import org.joda.time.DateTime;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.data.domain.Page;
    import org.springframework.data.domain.PageRequest;
    import org.springframework.data.domain.Pageable;
    import org.springframework.data.jpa.domain.Specification;
    import org.springframework.security.access.AccessDeniedException;
    import org.springframework.stereotype.Service;
    import org.springframework.web.multipart.MultipartFile;

    import java.io.IOException;
    import java.sql.Date;
    import java.time.LocalDate;
    import java.util.ArrayList;
    import java.util.HashMap;
    import java.util.List;
    import java.util.Map;

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
        public AbroadEnquiry createEnquiry(AbroadEnquiry abroadEnquiry, MultipartFile image,List<MultipartFile> documents, String role, String email,
                                           Long continentId, Long countryId, Long universityId, Long courseId,
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
            } catch (IOException e) {
                throw new RuntimeException("Failed to upload enquiry image", e);
            }

            try{
                List<String> uploadedUrls = new ArrayList<>();
                if(documents!=null && !documents.isEmpty()){
                for (MultipartFile document : documents) {
                    String url = s3Service.uploadImage(document); // Upload and get file URL
                    uploadedUrls.add(url);
                }
                abroadEnquiry.setUploadDocuments(uploadedUrls);
            }
            }catch (IOException e) {
                throw new RuntimeException(e);
            }


            abroadEnquiry.setEnquiry_date(LocalDate.now());
            abroadEnquiry.setCreatedByEmail(email);
            abroadEnquiry.setRole(role);
            abroadEnquiry.setBranchCode(branchCode);

            // Set names from IDs
           AbroadContinent continent= continentRepository.findById(continentId).orElseThrow(()-> new RuntimeException("continent not found"));
           abroadEnquiry.setAbroadContinent(continent);
           abroadEnquiry.setContinent(continent.getContinentname());

          AbroadCountry country= countryRepository.findById(countryId).orElseThrow(()->new RuntimeException("countery not found"));
          abroadEnquiry.setCountry(country.getCountry());
          abroadEnquiry.setAbroadCountry(country);

           AbroadUniversity university= universityRepository.findById(universityId).orElseThrow(()-> new RuntimeException("university not found"));
           abroadEnquiry.setUniversity(university.getUniversityName());
           abroadEnquiry.setAbroadUniversity(university);

//            streamRepository.findById(streamId).ifPresent(s -> abroadEnquiry.setStream(s.getName()));
           AbroadCourse course= courseRepository.findById(courseId).orElseThrow(()->new RuntimeException("course not found"));
           abroadEnquiry.setCourse(course.getCourseName());
           abroadEnquiry.setAbroadCourse(course);

           AbroadCollege college= collegeRepository.findById(collegeId).orElseThrow(()->new RuntimeException("college not found"));
           abroadEnquiry.setCollage(college.getCollegeName());
           abroadEnquiry.setAbroadCollege(college);

           AbroadState state= stateRepository.findById(stateId).orElseThrow(()->new RuntimeException("state not found"));
           abroadEnquiry.setState(state.getState());
           abroadEnquiry.setAbroadState(state);

           AbroadCity city= cityRepository.findById(cityId).orElseThrow(()->new RuntimeException("city not found"));
           abroadEnquiry.setCity(city.getCity());
           abroadEnquiry.setAbroadCity(city);

            return repository.save(abroadEnquiry);
        }

//        private String extractExamName(String filename) {
//            if (filename == null || filename.isEmpty()) return "UNKNOWN";
//
//            // Split by dot, take name before extension
//            String name = filename.split("\\.")[0];
//
//            // Optionally clean underscores/spaces etc.
//            return name.toUpperCase().replaceAll("[^A-Z]", "");
//        }



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
        public AbroadEnquiry updateEnquiry(Long id, AbroadEnquiry abroadEnquiry, MultipartFile image,Map<String, MultipartFile> updateDoc, String role, String email) {
            if (!permissionService.hasPermission(role, email, "PUT")) {
                throw new AccessDeniedException("No permission to update Enquiry");
            }

            AbroadEnquiry existing = repository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Enquiry not found"));

            existing.setName(abroadEnquiry.getName() != null ? abroadEnquiry.getName() : existing.getName());
            existing.setPhone_no(abroadEnquiry.getPhone_no() != null ? abroadEnquiry.getPhone_no() : existing.getPhone_no());
            existing.setEmail(abroadEnquiry.getEmail() != null ? abroadEnquiry.getEmail() : existing.getEmail());
//            existing.setEnquiry_date(abroadEnquiry.getEnquiry_date() != null ? abroadEnquiry.getEnquiry_date() : existing.getEnquiry_date());
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
            try {
                if (image != null && !image.isEmpty()) {
                    // Delete old image if exists
                    if (existing.getPhotoUrl() != null) {
                        s3Service.deleteImage(existing.getPhotoUrl());
                    }
                    String newImageUrl = s3Service.uploadImage(image);
                    existing.setPhotoUrl(newImageUrl);
                }
            } catch (IOException e) {
                throw new RuntimeException("Failed to update enquiry image", e);
            }
            try{
                List<String> existingDocs = existing.getUploadDocuments();

                for (Map.Entry<String, MultipartFile> entry : updateDoc.entrySet()) {
                    String key = entry.getKey();
                    if (!key.matches("\\d+")) continue;
                    Integer index =  Integer.parseInt(entry.getKey());
                    MultipartFile newFile = entry.getValue();

                    if (index < 0 || index >= existingDocs.size()) {
                        throw new IllegalArgumentException("Invalid index: " + index);
                    }

                    if (!newFile.isEmpty()) {
                        String newUrl = s3Service.uploadImage(newFile);  // Your existing S3 upload
                        existingDocs.set(index, newUrl);  // Replace URL at that index
                    }
                }

                existing.setUploadDocuments(existingDocs);  // Update entity
                repository.save(existing);
            } catch (Exception e) {
                throw new RuntimeException(e);
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
                String applyFor, // NEW PARAM
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

    }
