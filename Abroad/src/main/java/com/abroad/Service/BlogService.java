package com.abroad.Service;

import com.abroad.Entity.AbroadBlog;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface BlogService {
    AbroadBlog createBlog(AbroadBlog abroadBlog, MultipartFile image, String role, String email);

    Map<String,Object> getAllBlogsByBranchCode(int size, int page);

    AbroadBlog getBlogById(Long id);
    AbroadBlog updateBlog(Long id, AbroadBlog abroadBlog, MultipartFile image, String role, String email);
    void deleteBlog(Long id, String role, String email);

    AbroadBlog getBlogByTitle(String title);

}
