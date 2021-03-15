package com.zzf.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zzf
 * @date 2021-02-14
 */
@Entity
@Table(name = "t_blog")
@Data
@NoArgsConstructor
public class Blog {
    @Id
    @GeneratedValue
    private Long id;//主键id
    private String title;//博客标题
    @Column(columnDefinition = "longtext")
    private String content;//内容
    private String articleType;//文章类型
    private String topPicture;//内容顶部图片
    private String description;//描述
    //private String tag;//标签
    private Integer views;//浏览次数
    //private boolean commentabled;//能否评论
    private boolean published;//是否发布
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;//创建时间
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;//更新时间

    //实体类之间的关系

    @ManyToOne(cascade = {CascadeType.PERSIST})
    private Type type;//多对一，多个博客在一个分类下

    //对多多，级联新增，新增一个tag，同时会将其保存在数据库
    @ManyToMany(cascade = {CascadeType.PERSIST})
    private List<Tag> tags=new ArrayList<>();

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "blog")
    private List<Comment> comments=new ArrayList<>();

    @Transient
    private String typesId;

    @Transient
    private String tagIds;

    public void init(){
        this.tagIds=tagsToIds(this.getTags());
    }
    //tags转化成,分割的形式，得到tagsIds
    private String tagsToIds(List<Tag> tags){
        if(!tags.isEmpty()){
            StringBuffer ids=new StringBuffer();
            boolean flag=false;
            for (Tag tag:tags){
                if(flag){
                    ids.append(",");
                }else {
                    flag=true;
                }
                ids.append(tag.getId());
            }
            return ids.toString();
        }else {
            return tagIds;
        }
    }

}
