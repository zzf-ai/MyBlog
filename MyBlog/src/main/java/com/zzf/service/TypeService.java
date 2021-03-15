package com.zzf.service;


import com.zzf.pojo.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author zzf
 * @date 2021-02-15
 */
public interface TypeService {
    Type saveType(Type type);
    Type getType(Long id);
    Type getTypeByName(String name);
    Page<Type> listType(Pageable pageable);
    Type updateType(Long id,Type type);
    void deleteType(Long id);
    void deleteBatch(List<Long> ids);

    List<Type> listType();
    //取前几个分类
    List<Type> listTop(Integer size);
}
