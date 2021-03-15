package com.zzf.controller.admin;

import com.zzf.pojo.Type;
import com.zzf.service.TypeService;
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
public class TypeManageController {

    @Autowired
    private TypeService typeService;

    /**
     * 分类管理页面
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping("/types")
    public String types(@PageableDefault(size = 7,sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable, Model model){
        model.addAttribute("page",typeService.listType(pageable));
        return "admin/type-manage";
    }

    /**
     * 新增分类页面
     * @param model
     * @return
     */
    @GetMapping("/types/input")
    public String input(Model model){
        model.addAttribute("type",new Type());
        return "admin/type-input";
    }

    /**
     * 新增分类
     * @param type
     * @param bindingResult
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/types")
    public String addType(@Valid Type type, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        Type type1=typeService.getTypeByName(type.getName());
        if(type1!=null){
            bindingResult.rejectValue("name","nameError","分类名称不能重复");
        }
        if(bindingResult.hasErrors()){
            return "admin/type-input";
        }
        Type t = typeService.saveType(type);
        //消息提示
        if(t==null){
            redirectAttributes.addFlashAttribute("message","操作失败");
        }else {
            redirectAttributes.addFlashAttribute("message","操作成功");
        }
        return "redirect:/admin/types";
    }

    /**
     * 编辑分类页面
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("type",typeService.getType(id));
        return "admin/type-input";
    }

    /**
     * 编辑分类
     * @param type
     * @param bindingResult
     * @param id
     * @param redirectAttributes
     * @return
     */
    @PostMapping("/types/{id}")
    public String editType(@Valid Type type, BindingResult bindingResult,@PathVariable Long id, RedirectAttributes redirectAttributes){
        Type type1=typeService.getTypeByName(type.getName());
        if(type1!=null){
            bindingResult.rejectValue("name","nameError","分类名称不能重复");
        }
        if(bindingResult.hasErrors()){
            return "admin/type-input";
        }
        Type t = typeService.updateType(id,type);
        //消息提示
        if(t==null){
            redirectAttributes.addFlashAttribute("message","更新失败");
        }else {
            redirectAttributes.addFlashAttribute("message","更新成功");
        }
        return "redirect:/admin/types";
    }

    /**
     * 删除分类
     * @param id
     * @param redirectAttributes
     * @return
     */
    @GetMapping("/types/{id}/delete")
    public String deleteType(@PathVariable Long id,RedirectAttributes redirectAttributes){
        typeService.deleteType(id);
        redirectAttributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/types";
    }

    /**
     * 批量删除分类
     * @param ids
     * @param redirectAttributes
     * @return
     */
    @GetMapping("/types/deleteBatch/{ids}")
    public String deleteBatch(@PathVariable String ids,RedirectAttributes redirectAttributes){
        List<Long> Ids = Arrays.asList(ids.split("-")).stream().map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
        typeService.deleteBatch(Ids);
        redirectAttributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/types";
    }
}
