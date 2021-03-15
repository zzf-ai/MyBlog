package com.zzf.controller.admin;

import com.zzf.pojo.Tag;
import com.zzf.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zzf
 * @date 2021-02-15
 */
@Controller
@RequestMapping("/admin")
public class TagManageController {

    @Autowired
    private TagService tagService;

    /**
     * 标签管理页面
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping("/tags")
    public String Tags(@PageableDefault(size = 8,sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable, Model model){
        model.addAttribute("page", tagService.listTag(pageable));
        return "admin/tag-manage";
    }

    /**
     * 新增标签页面
     * @param model
     * @return
     */
    @GetMapping("/tags/input")
    public String input(Model model){
        model.addAttribute("tag",new Tag());
        return "admin/tag-input";
    }

    /**
     * 新增标签
     * @param tag
     * @param bindingResult
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/tags")
    public String addTag(@Valid Tag tag, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        Tag tag1= tagService.getTagByName(tag.getName());
        if(tag1!=null){
            bindingResult.rejectValue("name","nameError","标签名称不能重复");
        }
        if(bindingResult.hasErrors()){
            return "admin/tag-input";
        }
        Tag t = tagService.saveTag(tag);
        //消息提示
        if(t==null){
            redirectAttributes.addFlashAttribute("message","操作失败");
        }else {
            redirectAttributes.addFlashAttribute("message","操作成功");
        }
        return "redirect:/admin/tags";
    }

    /**
     * 编辑标签页面
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/tags/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("tag", tagService.getTag(id));
        return "admin/tag-input";
    }

    /**
     * 修改标签
     * @param tag
     * @param bindingResult
     * @param id
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/tags/{id}")
    public String editTag(@Valid Tag tag, BindingResult bindingResult,@PathVariable Long id, RedirectAttributes redirectAttributes){
        Tag Tag1= tagService.getTagByName(tag.getName());
        if(Tag1!=null){
            bindingResult.rejectValue("name","nameError","标签名称不能重复");
        }
        if(bindingResult.hasErrors()){
            return "admin/tag-input";
        }
        Tag t = tagService.updateTag(id,tag);
        //消息提示
        if(t==null){
            redirectAttributes.addFlashAttribute("message","更新失败");
        }else {
            redirectAttributes.addFlashAttribute("message","更新成功");
        }
        return "redirect:/admin/tags";
    }

    /**
     * 删除标签
     * @param id
     * @param redirectAttributes
     * @return
     */
    @GetMapping("/tags/{id}/delete")
    public String deleteTag(@PathVariable Long id,RedirectAttributes redirectAttributes){
        tagService.deleteTag(id);
        redirectAttributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/tags";
    }

    /**
     * 批量删除
     * @param ids
     * @param redirectAttributes
     * @return
     */
    @GetMapping("/tags/deleteBatch/{ids}")
    public String deleteBatch(@PathVariable String ids,RedirectAttributes redirectAttributes){
        List<Long> Ids = Arrays.asList(ids.split("-")).stream().map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
        tagService.deleteBatch(Ids);
        redirectAttributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/tags";
    }
}
