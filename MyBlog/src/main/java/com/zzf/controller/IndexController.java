package com.zzf.controller;

import com.zzf.service.BlogService;
import com.zzf.service.TagService;
import com.zzf.service.TypeService;
import com.zzf.utils.CountUtil;
import com.zzf.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;

    /**
     * 前台展示首页
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping("/")
    public String toIndex(@PageableDefault(size = 4,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable, Model model){
        model.addAttribute("page",blogService.listPublishedBlog(pageable));
        model.addAttribute("types", CountUtil.CountType(typeService.listTop(6)));
        model.addAttribute("tags",CountUtil.CountTag(tagService.listTagTop(10)));
        model.addAttribute("lastestBlogs",blogService.lastestBlogs(6));

        return "index";
    }

    /**
     * 跳转博客内容页面
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id, Model model){
        model.addAttribute("blog",blogService.getAndConvertBlog(id));
        return "blog";
    }

    /**
     * 全局搜索博客
     * @param pageable
     * @param query
     * @param model
     * @return
     */
    @GetMapping("/search")
    public String searchBlog(@PageableDefault(size = 4,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,@RequestParam String query, Model model){
        model.addAttribute("page",blogService.listBlogByQurey(pageable, "%"+query+"%"));
        model.addAttribute("query",query);
        return "search";
    }
}
