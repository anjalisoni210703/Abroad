package com.abroad.Controller;

import com.abroad.Entity.AbroadBlog;
import com.abroad.Service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin(origins = "https://wayabroad.in")
public class BlogController {
    @Autowired
    private BlogService service;

    @PostMapping("/createBlog")
    public ResponseEntity<AbroadBlog> createBlog(@RequestPart("blog") String blogJson,
                                                 @RequestParam("image") MultipartFile image,
                                                 @RequestParam String role,
                                                 @RequestParam String email) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        AbroadBlog abroadBlog = mapper.readValue(blogJson, AbroadBlog.class);
        return ResponseEntity.ok(service.createBlog(abroadBlog, image, role, email));
    }

    @PutMapping("/updateBlog/{id}")
    public ResponseEntity<AbroadBlog> updateBlog(@PathVariable Long id,
                                                 @RequestPart("blog") String blogJson,
                                                 @RequestParam(value = "image", required = false) MultipartFile image,
                                                 @RequestParam String role,
                                                 @RequestParam String email) throws JsonProcessingException {
        AbroadBlog abroadBlog = new ObjectMapper().readValue(blogJson, AbroadBlog.class);
        return ResponseEntity.ok(service.updateBlog(id, abroadBlog, image, role, email));
    }

    @GetMapping("/getAllBlogs")
    public ResponseEntity<List<AbroadBlog>> getAllBlogsByBranchCode(@RequestParam String branchCode,
                                                                    @RequestParam String role,
                                                                    @RequestParam String email) {
        return ResponseEntity.ok(service.getAllBlogsByBranchCode(branchCode, role, email));
    }

    @GetMapping("/getBlogById/{id}")
    public ResponseEntity<AbroadBlog> getBlogById(@PathVariable Long id,
                                                  @RequestParam String role,
                                                  @RequestParam String email) {
        return ResponseEntity.ok(service.getBlogById(id, role, email));
    }

    @DeleteMapping("/deleteBlog/{id}")
    public ResponseEntity<String> deleteBlog(@PathVariable Long id,
                                             @RequestParam String role,
                                             @RequestParam String email) {
        service.deleteBlog(id, role, email);
        return ResponseEntity.ok("Blog deleted successfully");
    }

    }

