package com.zzf.controller;

import com.zzf.pojo.Tag;
import com.zzf.service.BlogService;
import com.zzf.service.TagService;
import com.zzf.utils.CountUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author zzf
 * @date 2021-02-20
 */
@Controller
public class TagShowController {
    @Autowired
    private TagService tagService;

    @Autowired
    private BlogService blogService;

    /**
     * 按标签展示博客
     * @param pageable
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/tags/{id}")
    public String tags(@PageableDefault(size = 4,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable, @PathVariable Long id, Model model){
        List<Tag> tags=tagService.listTagTop(10000);
        if(id==-1){
            id=tags.get(0).getId();
            System.out.println(id);
        }
        model.addAttribute("tags", CountUtil.CountTag(tags));
        System.out.println(tags.size());
        model.addAttribute("page",blogService.listBlogByTagId(pageable,id));
        model.addAttribute("activeTagId",id);
        return "tags";
    }
}
