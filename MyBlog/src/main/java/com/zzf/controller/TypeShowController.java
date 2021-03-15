package com.zzf.controller;

import com.zzf.pojo.Type;
import com.zzf.service.BlogService;
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

import java.util.List;

/**
 * @author zzf
 * @date 2021-02-20
 */
@Controller
public class TypeShowController {

    @Autowired
    private TypeService typeService;
    @Autowired
    private BlogService blogService;

    /**
     * 博客按分类展示
     * @param pageable
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/types/{id}")
    public String types(@PageableDefault(size = 4,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable, @PathVariable Long id, Model model){
        List<Type> types = typeService.listTop(10000);
        if(id==-1){
            id=types.get(0).getId();
        }

        model.addAttribute("types", CountUtil.CountType(types));
        model.addAttribute("page",blogService.listBlogByTypeId(pageable,id));
        model.addAttribute("activeTypeId",id);
        return "types";
    }
}
