package com.zzf.dao;

import com.zzf.pojo.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author zzf
 * @date 2021-02-15
 */
public interface TagRepository extends JpaRepository<Tag,Long> {
    Tag findByName(String name);
    void deleteByIdIn(List<Long> ids);

    @Query("select t from Tag t")//hql
    List<Tag> findTagTop(Pageable pageable);
}
