package com.zzf.dao;

import com.zzf.pojo.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author zzf
 * @date 2021-02-16
 */
public interface BlogRepository extends JpaRepository<Blog,Long>, JpaSpecificationExecutor<Blog> {
    void deleteByIdIn(List<Long> ids);

    @Query("select b from Blog b where b.published = true")//hql
    List<Blog> findBlogTop(Pageable pageable);

    @Query("select b from Blog b where b.published = true")//hql
    Page<Blog> findAllBlog(Pageable pageable);

    @Query("select b from Blog b where b.published = true and (b.title like ?1 or b.content like ?1)")//hql
    Page<Blog> findAllBlogByQurey(String query,Pageable pageable);

    @Query("select function('date_format',b.updateTime,'%Y') as year from Blog b group by function('date_format',b.updateTime,'%Y') order by function('date_format',b.updateTime,'%Y') desc")
    List<String> findYearGroup();

    @Query("select b from Blog b where function('date_format',b.updateTime,'%Y') = ?1 and b.published=true")
    List<Blog> findByYear(String year);

    int countBlogsByPublished(boolean published);

    @Transactional
    @Modifying
    @Query("update Blog b set b.views=b.views+1 where b.id=?1")
    int updateViews(Long id);//设置浏览次数加1
}
