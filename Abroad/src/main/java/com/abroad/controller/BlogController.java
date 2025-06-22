package com.abroad.controller;

import com.abroad.entity.Blog;
import com.abroad.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.abroad.entity.Blog;
import com.abroad.service.BlogService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin(origins = "https://pjsofttech.in")
public class BlogController {
    @Autowired
    private BlogService service;

    @PostMapping("/createBlog")
    public ResponseEntity<Blog> createBlog(@RequestPart("blog") String blogJson,
                                           @RequestParam("image") MultipartFile image,
                                           @RequestParam String role,
                                           @RequestParam String email) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        Blog blog = mapper.readValue(blogJson, Blog.class);
        return ResponseEntity.ok(service.createBlog(blog, image, role, email));
    }

    @PutMapping("/updateBlog/{id}")
    public ResponseEntity<Blog> updateBlog(@PathVariable Long id,
                                           @RequestPart("blog") String blogJson,
                                           @RequestParam(value = "image", required = false) MultipartFile image,
                                           @RequestParam String role,
                                           @RequestParam String email) throws JsonProcessingException {
        Blog blog = new ObjectMapper().readValue(blogJson, Blog.class);
        return ResponseEntity.ok(service.updateBlog(id, blog, image, role, email));
    }

    @GetMapping("/getAllBlogs")
    public ResponseEntity<List<Blog>> getAllBlogs(@RequestParam String role,
                                                  @RequestParam String email) {
        return ResponseEntity.ok(service.getAllBlogs(role, email));
    }

    @GetMapping("/getBlogById/{id}")
    public ResponseEntity<Blog> getBlogById(@PathVariable Long id,
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

