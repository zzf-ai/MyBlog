package com.zzf.pojo;

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
@Table(name = "t_type")
@Data
@NoArgsConstructor
public class Type {
    @Id
    @GeneratedValue
    private Long id;
    @NotBlank(message = "分类名称不能为空")
    private String name;

    @OneToMany(mappedBy = "type")
    private List<Blog> blogs=new ArrayList<>();

    @Transient
    private Long countType;//该分类含发布的博客数量


    public Type(@NotBlank(message = "分类名称不能为空") String name) {
        this.name = name;
    }
}
