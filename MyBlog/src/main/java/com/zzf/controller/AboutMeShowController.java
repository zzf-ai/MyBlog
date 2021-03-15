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
public class AboutMeShowController {

    @Autowired
    private BlogService blogService;

    /**
     * 关于博主页面
     * @return
     */
    @GetMapping("/about")
    public String aboutMe(){
        return "about";
    }

    /**
     * 底部展示
     * @param model
     * @return
     */
    @GetMapping("/footer/lastest")
    public String lastestBlogs(Model model){
        model.addAttribute("lastestblogs",blogService.lastestBlogs(3));
        return "fragments :: lastestblogList";
    }
}
