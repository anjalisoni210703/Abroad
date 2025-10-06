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
    import java.sql.Date;
    import java.time.LocalDate;
    import java.time.YearMonth;
    import java.util.*;
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

            if (abroadEnquiry.getEnquiry_date() == null) {
                abroadEnquiry.setEnquiry_date(LocalDate.now());
            }
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
            List<AbroadEnquiry> enquiries = repository.findAll();
            if (enquiries.isEmpty()) {
                throw new NoSuchElementException("No enquiries found in the system");
            }
            return enquiries;
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
            existing.setAddress(abroadEnquiry.getAddress() != null ? abroadEnquiry.getAddress() : existing.getAddress());
            existing.setPercentage(abroadEnquiry.getPercentage()!=0 ? abroadEnquiry.getPercentage():existing.getPercentage());
            existing.setState(abroadEnquiry.getState() != null ? abroadEnquiry.getState() : existing.getState());
            existing.setPassoutYear(abroadEnquiry.getPassoutYear() !=null ? abroadEnquiry.getPassoutYear() : existing.getPassoutYear());
            existing.setFathersIncome(abroadEnquiry.getFathersIncome() !=0 ? abroadEnquiry.getFathersIncome():existing.getFathersIncome());
            existing.setFathersOccupation(abroadEnquiry.getFathersOccupation() != null? abroadEnquiry.getFathersOccupation():existing.getFathersOccupation());
            existing.setGender(abroadEnquiry.getGender()!=null ? abroadEnquiry.getGender():existing.getGender());
            if (abroadEnquiry.getDob() != null) {
                existing.setDob(abroadEnquiry.getDob());
            } else if (existing.getDob() == null) {
                // Optionally, set default DOB (e.g., today or leave null)
                existing.setDob(Date.valueOf(LocalDate.now()));
            }

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
            List<Object[]> totalResults = repository.countInquiriesByCourse(branchCode);

            LocalDate today = LocalDate.now();
            LocalDate last7 = today.minusDays(7);
            LocalDate last30 = today.minusDays(30);
            LocalDate last365 = today.minusDays(365);

            Map<String, Long> todayMap = repository.countInquiriesByCourseFromDate(today, branchCode).stream()
                    .collect(Collectors.toMap(r -> (String) r[0], r -> (Long) r[1]));

            Map<String, Long> last7Map = repository.countInquiriesByCourseFromDate(last7, branchCode).stream()
                    .collect(Collectors.toMap(r -> (String) r[0], r -> (Long) r[1]));

            Map<String, Long> last30Map = repository.countInquiriesByCourseFromDate(last30, branchCode).stream()
                    .collect(Collectors.toMap(r -> (String) r[0], r -> (Long) r[1]));

            Map<String, Long> last365Map = repository.countInquiriesByCourseFromDate(last365, branchCode).stream()
                    .collect(Collectors.toMap(r -> (String) r[0], r -> (Long) r[1]));

            return totalResults.stream()
                    .map(result -> {
                        String courseName = (String) result[0];
                        Long totalInquiries = (Long) result[1];

                        Map<String, Object> map = new HashMap<>();
                        map.put("courseName", courseName);
                        map.put("totalInquiries", totalInquiries);
                        map.put("today", todayMap.getOrDefault(courseName, 0L));
                        map.put("last7Days", last7Map.getOrDefault(courseName, 0L));
                        map.put("last30Days", last30Map.getOrDefault(courseName, 0L));
                        map.put("last365Days", last365Map.getOrDefault(courseName, 0L));
                        return map;
                    })
                    .collect(Collectors.toList());
        }


        @Override
        public List<Map<String, Object>> getInquiryCountByStreamAsMap(String branchCode) {
            List<Object[]> totalResults = repository.countInquiriesByStream(branchCode);

            LocalDate today = LocalDate.now();
            LocalDate last7 = today.minusDays(7);
            LocalDate last30 = today.minusDays(30);
            LocalDate last365 = today.minusDays(365);

            Map<String, Long> todayMap = repository.countInquiriesByStreamFromDate(today, branchCode).stream()
                    .collect(Collectors.toMap(r -> (String) r[0], r -> (Long) r[1]));

            Map<String, Long> last7Map = repository.countInquiriesByStreamFromDate(last7, branchCode).stream()
                    .collect(Collectors.toMap(r -> (String) r[0], r -> (Long) r[1]));

            Map<String, Long> last30Map = repository.countInquiriesByStreamFromDate(last30, branchCode).stream()
                    .collect(Collectors.toMap(r -> (String) r[0], r -> (Long) r[1]));

            Map<String, Long> last365Map = repository.countInquiriesByStreamFromDate(last365, branchCode).stream()
                    .collect(Collectors.toMap(r -> (String) r[0], r -> (Long) r[1]));

            return totalResults.stream()
                    .map(result -> {
                        String streamName = (String) result[0];
                        Long totalInquiries = (Long) result[1];

                        Map<String, Object> map = new HashMap<>();
                        map.put("streamName", streamName);
                        map.put("totalInquiries", totalInquiries);
                        map.put("today", todayMap.getOrDefault(streamName, 0L));
                        map.put("last7Days", last7Map.getOrDefault(streamName, 0L));
                        map.put("last30Days", last30Map.getOrDefault(streamName, 0L));
                        map.put("last365Days", last365Map.getOrDefault(streamName, 0L));
                        return map;
                    })
                    .collect(Collectors.toList());
        }

        @Override
        public List<Map<String, Object>> getInquiryCountByConductByAsMap(String branchCode) {
            List<Object[]> totalResults = repository.countInquiriesByConductBy(branchCode);

            LocalDate today = LocalDate.now();
            LocalDate last7 = today.minusDays(7);
            LocalDate last30 = today.minusDays(30);
            LocalDate last365 = today.minusDays(365);

            Map<String, Long> todayMap = repository.countInquiriesByConductByFromDate(today, branchCode).stream()
                    .collect(Collectors.toMap(r -> (String) r[0], r -> (Long) r[1]));

            Map<String, Long> last7Map = repository.countInquiriesByConductByFromDate(last7, branchCode).stream()
                    .collect(Collectors.toMap(r -> (String) r[0], r -> (Long) r[1]));

            Map<String, Long> last30Map = repository.countInquiriesByConductByFromDate(last30, branchCode).stream()
                    .collect(Collectors.toMap(r -> (String) r[0], r -> (Long) r[1]));

            Map<String, Long> last365Map = repository.countInquiriesByConductByFromDate(last365, branchCode).stream()
                    .collect(Collectors.toMap(r -> (String) r[0], r -> (Long) r[1]));

            return totalResults.stream()
                    .map(result -> {
                        String conductBy = (String) result[0];
                        Long totalInquiries = (Long) result[1];

                        Map<String, Object> map = new HashMap<>();
                        map.put("conductBy", conductBy);
                        map.put("totalInquiries", totalInquiries);
                        map.put("today", todayMap.getOrDefault(conductBy, 0L));
                        map.put("last7Days", last7Map.getOrDefault(conductBy, 0L));
                        map.put("last30Days", last30Map.getOrDefault(conductBy, 0L));
                        map.put("last365Days", last365Map.getOrDefault(conductBy, 0L));
                        return map;
                    })
                    .collect(Collectors.toList());
        }

        @Override
        public List<Map<String, Object>> getInquiryCountByStreamForMonth(int month, String branchCode) {
            List<Object[]> results = repository.countInquiriesByStreamForMonth(month, branchCode);

            return results.stream()
                    .map(result -> {
                        Map<String, Object> map = new HashMap<>();
                        map.put("streamName", result[0]);
                        map.put("inquiryCount", result[1]);
                        return map;
                    })
                    .collect(Collectors.toList());
        }
        @Override
        public List<Map<String, Object>> getInquiryCountByStreamForYearRange(int startYear, int endYear, String branchCode) {
            List<Object[]> results = repository.countInquiriesByStreamForYearRange(startYear, endYear, branchCode);

            return results.stream()
                    .map(result -> {
                        Map<String, Object> map = new HashMap<>();
                        map.put("streamName", result[0]);
                        map.put("inquiryCount", result[1]);
                        map.put("year", result[2]); // so frontend knows year-wise counts
                        return map;
                    })
                    .collect(Collectors.toList());
        }



        @Override
        public List<Map<String, Object>> getInquiryCountByCourseForMonth(int month, String branchCode) {
            List<Object[]> results = repository.countInquiriesByCourseForMonth(month, branchCode);

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
        public List<Map<String, Object>> getInquiryCountByCourseForYearRange(int startYear, int endYear, String branchCode) {
            List<Object[]> results = repository.countInquiriesByCourseForYearRange(startYear, endYear, branchCode);

            return results.stream()
                    .map(result -> {
                        Map<String, Object> map = new HashMap<>();
                        map.put("courseName", result[0]);
                        map.put("inquiryCount", result[1]);
                        map.put("year", result[2]); // include year so frontend can distinguish
                        return map;
                    })
                    .collect(Collectors.toList());
        }


        @Override
        public List<Map<String, Object>> getInquiryCountByConductByForMonth(int month, String branchCode) {
            List<Object[]> results = repository.countInquiriesByConductByForMonth(month, branchCode);

            return results.stream()
                    .map(result -> {
                        Map<String, Object> map = new HashMap<>();
                        map.put("conductBy", result[0]);
                        map.put("inquiryCount", result[1]);
                        return map;
                    })
                    .collect(Collectors.toList());
        }


        @Override
        public Map<String, Long> getAllEnquiryCounts(String branchCode) {
            Map<String, Long> counts = new HashMap<>();

            LocalDate today = LocalDate.now();
            counts.put("total", repository.getAllTotalCountEnquiry(branchCode));
            counts.put("today", repository.getCountFromDate(today, branchCode));
            counts.put("last7Days", repository.getCountFromDate(today.minusDays(7), branchCode));
            counts.put("last30Days", repository.getCountFromDate(today.minusDays(30), branchCode));
            counts.put("last365Days", repository.getCountFromDate(today.minusDays(365), branchCode));

            return counts;
        }

        @Override
        public Map<String, Long> getAllStatusWiseCount(String branchCode) {
            List<Object[]> results = repository.getAllStatusWiseCount(branchCode);

            Map<String, Long> statusCounts = new HashMap<>();
            for (Object[] row : results) {
                String status = (String) row[0];
                Long count = (Long) row[1];
                statusCounts.put(status, count);
            }

            return statusCounts;
        }

        @Override
        public List<AbroadEnquiry> getAllEnquiryDataByIdOrNameOrEmailOrPhone(Long id, String name, String email, Long phoneNo) {
            return repository.searchEnquiries(id, name, email, phoneNo);
        }

        @Override
        public Map<String, Object> getDailyInquiryCountsWithTotal(int year, int month) {
            List<Object[]> results = repository.getDailyInquiryCounts(year, month);

            // Put query results into a map
            Map<LocalDate, Long> dbCounts = results.stream()
                    .collect(Collectors.toMap(
                            r -> (LocalDate) r[0],
                            r -> (Long) r[1]
                    ));

            Map<String, Long> dailyCounts = new LinkedHashMap<>();
            LocalDate start = LocalDate.of(year, month, 1);
            LocalDate end = start.withDayOfMonth(start.lengthOfMonth());

            long total = 0;
            for (LocalDate date = start; !date.isAfter(end); date = date.plusDays(1)) {
                long count = dbCounts.getOrDefault(date, 0L);
                dailyCounts.put(date.toString(), count);
                total += count;
            }

            Map<String, Object> response = new LinkedHashMap<>();
            response.put("year", year);
            response.put("month", month);
            response.put("dailyCounts", dailyCounts);
            response.put("totalCount", total);

            return response;
        }
        @Override
        public Map<String, Object> getMonthlyInquiryCountsWithTotal(int startYear, int endYear) {
            List<Object[]> results = repository.getMonthlyInquiryCountsBetweenYears(startYear, endYear);

            // { year -> { month -> count } }
            Map<Integer, Map<Integer, Long>> yearlyData = new LinkedHashMap<>();

            for (Object[] row : results) {
                int year = (Integer) row[0];
                int month = (Integer) row[1];
                long count = (Long) row[2];

                yearlyData
                        .computeIfAbsent(year, y -> new LinkedHashMap<>())
                        .put(month, count);
            }

            long total = yearlyData.values().stream()
                    .flatMap(m -> m.values().stream())
                    .mapToLong(Long::longValue)
                    .sum();

            Map<String, Object> response = new LinkedHashMap<>();
            response.put("startYear", startYear);
            response.put("endYear", endYear);
            response.put("yearlyMonthlyCounts", yearlyData);
            response.put("totalCount", total);

            return response;
        }
        @Override
        public Map<String, Object> getYearlyInquiryCounts() {
            List<Object[]> results = repository.getYearlyInquiryCounts();

            Map<Integer, Long> yearlyCounts = results.stream()
                    .collect(Collectors.toMap(
                            r -> (Integer) r[0],   // year
                            r -> (Long) r[1],
                            (a, b) -> a,
                            LinkedHashMap::new
                    ));

            Map<String, Object> response = new LinkedHashMap<>();
            response.put("yearlyCounts", yearlyCounts);

            return response;
        }

        // get all enquiry by for branch
        @Override
        public List<AbroadEnquiry> getAllEnquiryByBranchCode(String role, String email, String branchCode) {
            if (!permissionService.hasPermission(role, email, "GET")) {
                throw new AccessDeniedException("No permission to view enquiries");
            }
            List<AbroadEnquiry> enquiries = repository.findByBranchCode(branchCode);
            if (enquiries == null || enquiries.isEmpty()) {
                throw new NoSuchElementException("No enquiries found for branch code: " + branchCode);
            }
            return enquiries;
        }

        // get all enquiry for staff
        @Override
        public List<AbroadEnquiry> getAllEnquiryByCreatedByEmail(String role, String email, String createdByEmail) {
            if (!permissionService.hasPermission(role, email, "GET")) {
                throw new AccessDeniedException("No permission to view enquiries");
            }
            List<AbroadEnquiry> enquiries = repository.findByCreatedByEmail(createdByEmail);
            if (enquiries == null || enquiries.isEmpty()) {
                throw new NoSuchElementException("No enquiries found for email: " + createdByEmail);
            }
            return enquiries;
        }

        @Override
        public List<AbroadEnquiry> getEnquiries(String role, String email,String branchCode) {
            if (!permissionService.hasPermission(role, email, "GET")) {
                throw new AccessDeniedException("No permission to view enquiries");
            }

            List<AbroadEnquiry> enquiries;

            switch (role.toUpperCase()) {
                case "SUPERADMIN":
                    enquiries = repository.findAll();
                    break;

                case "BRANCH":
                    if (branchCode == null || branchCode.isEmpty()) {
                        throw new IllegalArgumentException("Branch code is required for BRANCH role");
                    }
                    enquiries = repository.findByBranchCode(branchCode);
                    break;

                case "STAFF":
                    if ( email == null || email.isEmpty()) {
                        throw new IllegalArgumentException("Email is required for STAFF role");
                    }
                    enquiries = repository.findByCreatedByEmail(email);
                    break;

                default:
                    throw new AccessDeniedException("Invalid role: " + role);
            }

            if (enquiries == null || enquiries.isEmpty()) {
                throw new NoSuchElementException("No enquiries found");
            }

            return enquiries;
        }

    }
