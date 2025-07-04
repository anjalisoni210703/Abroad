package com.abroad.Service;

import com.abroad.Entity.AbroadBlog;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BlogService {
    AbroadBlog createBlog(AbroadBlog abroadBlog, MultipartFile image, String role, String email);
    List<AbroadBlog> getAllBlogsByBranchCode(String branchCode);
    AbroadBlog getBlogById(Long id, String role, String email);
    AbroadBlog updateBlog(Long id, AbroadBlog abroadBlog, MultipartFile image, String role, String email);
    void deleteBlog(Long id, String role, String email);
}
