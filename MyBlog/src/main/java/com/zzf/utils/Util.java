package com.zzf.utils;


import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zzf
 * @date 2021-02-17
 */
public class Util {

    public static void main(String[] args) {
        String test = "@[TOC]\n" +
                "## 定义\n" +
                "将一个接口转换成客户希望的另一个接口，使接口不兼容的那些类可以一起工作，其别名为包装器(Wrapper)。适配器模式既可以作为类结构型模式，也可以作为对象结构型模式。\n" +
                "注意：定义中所提及的接口是指广义的接口，它可以表示一个方法或者方法的集合\n" +
                "适配器模式可分为对象适配器和类适配器两种，在对象适配器模式中，适配器与适配者之间是关联关系；在类适配器模式中，适配器与适配者之间是继承（或实现）关系。实际开发中，对象适配器的使用频率更高\n" +
                "\n" +
                "## 对象适配器";
        //System.out.println(test.indexOf("@[TOC]"));
        String html = Markdown2Html(test);
        System.out.println(Html2PlainText(html));
    }

    //字符串转Long列表
    public static List<Long> convertToList(String ids){
        List<Long> list = new ArrayList<>();
        if(!"".equals(ids) && ids!=null){
            String[] idarray = ids.split(",");
            for (int i = 0; i < idarray.length; i++) {
                list.add(new Long(idarray[i]));
            }
        }
        return list;
    }

    /**
     * 获取所有属性值为空的属性名数组
     */
    public static String[] getNullPropertyNames(Object source){
        Object object;
        BeanWrapper beanWrapper = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = beanWrapper.getPropertyDescriptors();
        List<String> nullPropertyNames = new ArrayList<>();
        for (PropertyDescriptor pd:pds){
            String propertyName = pd.getName();
            if(beanWrapper.getPropertyValue(propertyName)==null){
                nullPropertyNames.add(propertyName);
            }
        }
        return nullPropertyNames.toArray(new String[nullPropertyNames.size()]);
    }

    public static String Markdown2Html(String md) {
        MutableDataSet options = new MutableDataSet();

        Parser parser = Parser.builder(options).build();
        HtmlRenderer renderer = HtmlRenderer.builder(options).build();

        Node document = parser.parse(md);
        String html = renderer.render(document);
        return html;
    }

    public static String Html2PlainText (String html)
    {
        if (StringUtils.isEmpty(html))
        {
            return "";
        }

        Document document = Jsoup.parse(html);
        Document.OutputSettings outputSettings = new Document.OutputSettings().prettyPrint(false);
        document.outputSettings(outputSettings);
        /*document.select("br").append("\\n");
        document.select("p").prepend("\\n");
        document.select("p").append("\\n");*/
        String newHtml = document.html().replaceAll("\\\\n", "\n");
        String plainText = Jsoup.clean(newHtml, "", Whitelist.none(), outputSettings);
        String result = StringEscapeUtils.unescapeHtml(plainText.trim());
        if(result.indexOf("[TOC]")!=-1){
            return result.substring(6,100).trim();
        }
        else {
            System.out.println(result);
            return result.substring(0, 100).trim();
        }
    }

}
