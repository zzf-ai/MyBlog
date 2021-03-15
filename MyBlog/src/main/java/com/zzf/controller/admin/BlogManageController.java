package com.zzf.controller.admin;

import com.zzf.pojo.Blog;
import com.zzf.pojo.User;
import com.zzf.service.BlogService;
import com.zzf.service.TagService;
import com.zzf.service.TypeService;
import com.zzf.utils.Util;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zzf
 * @date 2021-02-14
 */
@Controller
@RequestMapping("/admin")
public class BlogManageController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;

    /**
     * 博客管理
     * @param pageable
     * @param blog
     * @param model
     * @return
     */
    @GetMapping("/blogs")
    public String toManagePage(@PageableDefault(size = 5,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blog, Model model){
        model.addAttribute("types",typeService.listType());
        model.addAttribute("page",blogService.listBlog(pageable, blog));
        return "admin/blogs-manage";
    }


    /**
     * 多条件查询，动态刷新
     * @param pageable
     * @param blog
     * @param model
     * @return
     */
    @PostMapping("/blogs/search")
    public String toManagePageBySearch(@PageableDefault(size = 5,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blog, Model model){
        model.addAttribute("page",blogService.listBlog(pageable, blog));
        return "admin/blogs-manage :: blogList";
    }

    /**
     * 跳转新增博客页面
     * @param model
     * @return
     */
    @GetMapping("/blogs/input")
    public String input(Model model){
        model.addAttribute("types",typeService.listType());
        model.addAttribute("tags",tagService.listTag());
        model.addAttribute("blog",new Blog());
        return "admin/blogs-input";
    }

    /**
     * 跳转编辑博客页面
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/blogs/{id}/input")
    public String edit(@PathVariable Long id, Model model){
        model.addAttribute("types",typeService.listType());
        model.addAttribute("tags",tagService.listTag());
        Blog blog=blogService.getBlog(id);
        blog.init();
        model.addAttribute("blog",blog);
        return "admin/blogs-input";
    }

    /**
     * 删除博客
     * @param id
     * @param redirectAttributes
     * @return
     */
    @GetMapping("/blogs/{id}/delete")
    public String deleteType(@PathVariable Long id,RedirectAttributes redirectAttributes){
        blogService.deleteBlog(id);
        redirectAttributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/blogs";
    }

    /**
     * 批量删除
     * @param ids
     * @param redirectAttributes
     * @return
     */
    @GetMapping("/blogs/deleteBatch/{ids}")
    public String deleteBatch(@PathVariable String ids,RedirectAttributes redirectAttributes){
        List<Long> Ids = Arrays.asList(ids.split("-")).stream().map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
        blogService.deleteBatch(Ids);
        redirectAttributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/blogs";
    }


    /**
     * 新增/修改博客
     * @param blog
     * @param redirectAttributes
     * @param session
     * @return
     */
    @PostMapping("/blogs")
    public String save(Blog blog, RedirectAttributes redirectAttributes, HttpSession session){
        blog.setUser((User) session.getAttribute("user"));
        int number = (int)(Math.random()*1000)+2;
        blog.setTopPicture("https://picsum.photos/id/"+number+"/800/450");
        blog.setDescription(Util.Html2PlainText(Util.Markdown2Html(blog.getContent())));

        Blog b;
        if(blog.getId()==null){
            b = blogService.saveBlog(blog);
        }else {
            b = blogService.updateBlog(blog.getId(),blog);
        }

        if(b==null){
            redirectAttributes.addFlashAttribute("message","操作失败");
        }else{
            redirectAttributes.addFlashAttribute("message","操作成功");
        }
        return "redirect:/admin/blogs";
    }
}
