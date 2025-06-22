package com.abroad.service;

import com.abroad.entity.Blog;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BlogService {
    Blog createBlog(Blog blog, MultipartFile image, String role, String email);
    List<Blog> getAllBlogs(String role, String email);
    Blog getBlogById(Long id, String role, String email);
    Blog updateBlog(Long id, Blog blog, MultipartFile image, String role, String email);
    void deleteBlog(Long id, String role, String email);
}
