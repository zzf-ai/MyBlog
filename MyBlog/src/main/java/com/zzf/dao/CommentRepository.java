package com.zzf.dao;

import com.zzf.pojo.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author zzf
 * @date 2021-02-23
 */
public interface CommentRepository extends JpaRepository<Comment,Long> {

    //根据blogId查询评论，按创建时间排序
    List<Comment> findByBlogIdAndParentCommentNull(Long blogId, Sort sort);

    void deleteByIdIn(List<Long> commentIds);
}
