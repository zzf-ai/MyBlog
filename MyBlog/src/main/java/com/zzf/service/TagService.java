package com.zzf.service;


import com.zzf.pojo.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author zzf
 * @date 2021-02-15
 */
public interface TagService {
    Tag saveTag(Tag Tag);
    Tag getTag(Long id);
    Tag getTagByName(String name);
    Page<Tag> listTag(Pageable pageable);
    Tag updateTag(Long id, Tag Tag);
    void deleteTag(Long id);
    void deleteBatch(List<Long> ids);
    List<Tag> listTag();
    List<Tag> listTag(String ids);
    List<Tag> saveAllTag(List<Tag> tags);
    //取前几个标签
    List<Tag> listTagTop(Integer size);

    List<Tag> listTagTopByPublished();

}
