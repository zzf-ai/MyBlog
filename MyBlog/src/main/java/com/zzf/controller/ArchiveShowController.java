package com.zzf.controller;

import com.zzf.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author zzf
 * @date 2021-02-21
 */
@Controller
public class ArchiveShowController {

    @Autowired
    private BlogService blogService;

    /**
     * 博客归档
     * @param model
     * @return
     */
    @GetMapping("/archives")
    public String archives(Model model){
        model.addAttribute("archives",blogService.blogArchive());
        model.addAttribute("blogCount",blogService.blogCount());
        return "archives";
    }
}
