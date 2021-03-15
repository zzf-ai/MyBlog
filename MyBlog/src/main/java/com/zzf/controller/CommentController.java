package com.zzf.controller;

import com.zzf.pojo.Comment;
import com.zzf.pojo.User;
import com.zzf.service.BlogService;
import com.zzf.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

/**
 * @author zzf
 * @date 2021-02-23
 */
@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private BlogService blogService;

    //动态获取评论列表
    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable Long blogId, Model model){
        model.addAttribute("comments",commentService.listCommentByBlogId(blogId));
        return "blog :: commentList";
    }

    //提交评论控制器
    @PostMapping("/comments")
    public String submit(Comment comment, HttpSession session){
        Long blogId=comment.getBlog().getId();
        comment.setBlog(blogService.getBlog(blogId));
        User user = (User) session.getAttribute("user");
        if(user!=null){
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);
        }
        commentService.submitComment(comment);
        return "redirect:/comments/"+comment.getBlog().getId();
    }

    /**
     * 删除评论
     * @param commentId
     * @param blogId
     * @return
     */
    @GetMapping("/deleteComment/{commentId}/{blogId}")
    public String deleteComment(@PathVariable Long commentId,@PathVariable Long blogId){
        commentService.deleteComment(commentId);
        return "redirect:/blog/"+blogId;
    }
}
