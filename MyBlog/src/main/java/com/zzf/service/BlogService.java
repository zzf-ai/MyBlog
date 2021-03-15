package com.zzf.service;

import com.zzf.pojo.Blog;
import com.zzf.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * @author zzf
 * @date 2021-02-16
 */
public interface BlogService {

    Blog getBlog(Long id);

    Blog getAndConvertBlog(Long id);
    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);
    Page<Blog> listBlogByTagId(Pageable pageable, Long tagId);
    Page<Blog> listBlogByTypeId(Pageable pageable, Long typeId);

    Page<Blog> listBlogByQurey(Pageable pageable, String query);
    Blog saveBlog(Blog blog);
    Blog updateBlog(Long id,Blog blog);
    void deleteBlog(Long id);
    void deleteBatch(List<Long> ids);
    Page<Blog> listBlog(Pageable pageable);
    Page<Blog> listPublishedBlog(Pageable pageable);
    List<Blog> lastestBlogs(Integer size);

    Map<String,List<Blog>> blogArchive();

    int blogCount();
}
