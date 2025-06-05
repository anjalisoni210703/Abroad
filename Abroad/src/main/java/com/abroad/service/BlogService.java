package com.abroad.service;

import com.abroad.entity.Blog;

import java.util.List;

public interface BlogService {
    Blog createBlog(Blog blog);
    Blog updateBlog(Long id, Blog blog);
    void deleteBlog(Long id);
    Blog getBlogById(Long id);
    List<Blog> getAllBlogs();
}
