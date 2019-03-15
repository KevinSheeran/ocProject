package com.online.college.springboot.controller;

import com.online.college.core.course.domain.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.Query;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.result.ScoredPage;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("solr")
public class SolrController {

    @Autowired
    SolrTemplate template;

    /**
     * template综合查询: 在综合查询中, 有按条件查询,条件过滤, 排序, 分页
     * @param keywords
     * @return
     */
    @RequestMapping("/search/{keywords}")
    public List<Course> search(@PathVariable String keywords) {
        if(keywords!=null){
            //查找条件
            Query query=new SimpleQuery("*:*");
            Criteria criteria=new Criteria("keywords").contains(keywords);
            query.addCriteria(criteria);
            query.setOffset(0);
            query.setRows(3);
            ScoredPage<Course> page = template.queryForPage(query,Course.class);
            return page.getContent();
        }else{
            Query query=new SimpleQuery("*:*");
            ScoredPage<Course> page = template.queryForPage(query,Course.class);
            return page.getContent();
        }
    }

    @RequestMapping("/toSearch/{keywords}")
    public  ModelAndView toSearch(@PathVariable String keywords){
        Query query=new SimpleQuery("*:*");
        Criteria cri=new Criteria("keywords").contains(keywords);
        query.addCriteria(cri);
        ScoredPage<Course> page = template.queryForPage(query,Course.class);
        for (Course c : page.getContent()){
            System.out.println(c.getName()+"\t"+c.getStudyCount()+"\t"+c.getFree()+"\t"+c.getBrief()+"\t"+c.getPrice()+"\t"+c.getLevel());
        }
        System.out.println("总记录："+page.getTotalElements());
        System.out.println("总页数："+page.getTotalPages());
        ModelAndView mv = new ModelAndView("search");
        mv.addObject("pages",page.getContent() );
        return mv;
    }
    @RequestMapping("/toSearchAll")
    public  ModelAndView toSearchAll(){
        Query query=new SimpleQuery("*:*");
        ScoredPage<Course> page = template.queryForPage(query,Course.class);
        ModelAndView mv = new ModelAndView("search");
        mv.addObject("pages",page.getContent() );
        return mv;
    }
}
