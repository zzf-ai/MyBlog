package com.zzf.utils;

import com.zzf.pojo.Blog;
import com.zzf.pojo.Tag;
import com.zzf.pojo.Type;

import java.util.List;

/**
 * @author zzf
 * @date 2021-02-20
 */
public class CountUtil {
    public static List<Tag> CountTag(List<Tag> tags){
        for (Tag tag:tags) {
            Long count=0L;
            for (Blog b:tag.getBlogs()) {
                if(b.isPublished()){
                    count++;
                }
            }
            tag.setTagCounts(count);
        }
        return tags;
    }
    public static List<Type> CountType(List<Type> types){
        for (Type type:types) {
            Long count=0L;
            for (Blog b:type.getBlogs()) {
                if(b.isPublished()){
                    count++;
                }
            }
           type.setCountType(count);
        }
        return types;
    }
}
