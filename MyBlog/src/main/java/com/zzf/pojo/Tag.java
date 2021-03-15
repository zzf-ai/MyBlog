package com.zzf.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zzf
 * @date 2021-02-14
 */
@Entity
@Table(name = "t_tag")
@Data
@NoArgsConstructor
public class Tag {
    @Id
    @GeneratedValue
    private Long id;
    @NotBlank(message = "标签名称不能为空")
    private String name;

    @ManyToMany(mappedBy = "tags")
    private List<Blog> blogs = new ArrayList<>();

    public Tag(String name) {
        this.name=name;
    }

    @Transient
    private Long tagCounts;//该标签含发布的博客数量
}
