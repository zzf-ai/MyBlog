package com.zzf.service;

import com.zzf.pojo.Comment;

import java.util.List;

/**
 * @author zzf
 * @date 2021-02-23
 */
public interface CommentService {

    List<Comment> listCommentByBlogId(Long blogId);//根据blogId获取评论列表
    Comment submitComment(Comment comment);//发布评论

    void deleteComment(Long commentId);
}
