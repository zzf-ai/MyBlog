package com.zzf.dao;

import com.zzf.pojo.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author zzf
 * @date 2021-02-15
 */
public interface TypeRepository extends JpaRepository<Type,Long> {
    Type findByName(String name);
    void deleteByIdIn(List<Long> ids);

    @Query("select t from Type t")//hql
    List<Type> findTop(Pageable pageable);
}
