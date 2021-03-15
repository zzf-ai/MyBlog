package com.zzf.service.impl;

import com.zzf.NotFoundException;
import com.zzf.dao.BlogRepository;
import com.zzf.dao.TagRepository;
import com.zzf.dao.TypeRepository;
import com.zzf.pojo.Blog;
import com.zzf.pojo.Tag;
import com.zzf.pojo.Type;
import com.zzf.service.BlogService;
import com.zzf.utils.Util;
import com.zzf.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.*;

/**
 * @author zzf
 * @date 2021-02-16
 */
@Service
@Transactional
public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private TypeRepository typeRepository;

    @Autowired
    private TagRepository tagRepository;


    @Override
    public Blog getBlog(Long id) {
        return blogRepository.findById(id).get();
    }

    //获取的blog用于前端展示
    @Override
    public Blog getAndConvertBlog(Long id) {
        Blog blog = blogRepository.findById(id).get();
        if(blog==null){
            throw new NotFoundException("该博客不存在");
        }
        /*Blog b = new Blog();
        //拷贝一份，尽量不改变数据库原有内容
        BeanUtils.copyProperties(blog,b);
        String content = b.getContent();
        b.setContent(MarkDownUtil.markdownToHtmlExtensions(content));
        return b;*/
        blogRepository.updateViews(id);
        return blog;
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                //拼装条件
                List<Predicate> predicates = new ArrayList<>();
                if(!"".equals(blog.getTitle()) && blog.getTitle()!=null){
                    predicates.add(criteriaBuilder.like(root.<String>get("title"),"%"+blog.getTitle()+"%"));
                }
                if(blog.getTypeId()!=null){
                    predicates.add(criteriaBuilder.equal(root.<String>get("type").get("id"),blog.getTypeId()));
                }
                if(!"".equals(blog.getArticleType()) && blog.getArticleType()!=null){
                    System.out.println();
                    predicates.add(criteriaBuilder.equal(root.<String>get("articleType"),blog.getArticleType()));
                }
                criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        },pageable);
    }

    @Override
    public Page<Blog> listBlogByTagId(Pageable pageable, Long tagId) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                //拼装条件
                List<Predicate> predicates = new ArrayList<>();
                predicates.add(criteriaBuilder.equal(root.<String>get("published"),true));
                Join join = root.join("tags");
                predicates.add(criteriaBuilder.equal(join.get("id"),tagId));
                criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        },pageable);
    }

    @Override
    public Page<Blog> listBlogByTypeId(Pageable pageable, Long typeId) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                //拼装条件
                List<Predicate> predicates = new ArrayList<>();
                predicates.add(criteriaBuilder.equal(root.<String>get("published"),true));
                Join join = root.join("type");
                predicates.add(criteriaBuilder.equal(join.get("id"),typeId));
                criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        },pageable);
    }

    @Override
    public Page<Blog> listBlogByQurey(Pageable pageable, String query) {
        return blogRepository.findAllBlogByQurey(query, pageable);
    }

    public static boolean isNumeric(String str){

        for (int i = str.length();--i>=0;){

            if (!Character.isDigit(str.charAt(i))){

                return false;

            }

        }

        return true;

    }
    public void updateType(Blog blog){
        System.out.println(blog.getTypesId());
        String typeName = blog.getTypesId();
        if(typeName==null || typeName.equals("")){
            return;
        }
        if(!isNumeric(typeName)){
            Type type = new Type(typeName);
            blog.setType(type);
        }else {
            blog.setType(typeRepository.getOne(Long.parseLong(blog.getTypesId())));
        }

    }
    public void updateTag(Blog blog){
        String[] split = blog.getTagIds().split(",");
        List<Tag> tags = new ArrayList<>();
        List<Tag> tags2 = new ArrayList<>();
        System.out.println(blog.getTagIds());
        for (int i = 0; i < split.length; i++) {
            if(isNumeric(split[i])){
                System.out.println("aaaaa");
                tags2.add(tagRepository.getOne(Long.parseLong(split[i])));
            }else{
                System.out.println("bbbbb");
                Tag tag = new Tag(split[i]);
                tags.add(tag);
            }
        }
        if(tags!=null && tags.size()!=0){
            List<Tag> tags1 = tagRepository.saveAll(tags);
            //System.out.println(tags1.toString());
            System.out.println(tags1.size());
            for (Tag t:tags1) {
                tags2.add(t);
            }
        }
        //tags2.addAll(tags1);
        //System.out.println(tags2.toString());
        if(tags2!=null){
            System.out.println(tags2.size());
            //System.out.println(tags2.toString());
            blog.setTags(tags2);
        }
    }
    @Override
    public Blog saveBlog(Blog blog) {
        System.out.println(blog.getTagIds());
        if(blog.getId()==null){
            //System.out.println(blog.getType().getName());
            updateType(blog);
            updateTag(blog);
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setViews(0);
        }else{
            //updateTag(blog);
            blog.setTags(tagRepository.findAllById(Util.convertToList(blog.getTagIds())));
            blog.setUpdateTime(new Date());
        }
        return blogRepository.save(blog);
    }

    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog b = blogRepository.findById(id).get();
        if(b==null){
            throw new NotFoundException("该博客不存在！");
        }
        updateType(blog);
        updateTag(blog);
        BeanUtils.copyProperties(blog,b,Util.getNullPropertyNames(blog));
        //b.setTags(tagRepository.findAllById(Util.convertToList(blog.getTagIds())));
        b.setUpdateTime(new Date());
        return blogRepository.save(b);
    }

    @Override
    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }

    @Override
    public void deleteBatch(List<Long> ids) {
        blogRepository.deleteByIdIn(ids);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    @Override
    public Page<Blog> listPublishedBlog(Pageable pageable) {
        return blogRepository.findAllBlog(pageable);
    }

    @Override
    public List<Blog> lastestBlogs(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC,"updateTime");
        Pageable pageable = PageRequest.of(0, size, sort);
        return blogRepository.findBlogTop(pageable);
    }

    @Override
    public Map<String, List<Blog>> blogArchive() {
        List<String> years = blogRepository.findYearGroup();//查出年份集合
        Map<String, List<Blog>> map = new HashMap<>();
        //根据年份查出blog集合，封装成map
        for (String year:years) {
            map.put(year,blogRepository.findByYear(year));
        }
        return map;
    }

    @Override
    public int blogCount() {
        return blogRepository.countBlogsByPublished(true);
    }
}
