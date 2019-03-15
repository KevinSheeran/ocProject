package com.online.college.springboot.controller;

import com.online.college.core.auth.domain.AuthUser;
import com.online.college.core.auth.service.IAuthUserService;
import com.online.college.core.business.IPortalBusiness;
import com.online.college.core.consts.CourseEnum;
import com.online.college.core.consts.domain.ConstsSiteCarousel;
import com.online.college.core.consts.service.IConstsSiteCarouselService;
import com.online.college.core.course.domain.Course;
import com.online.college.core.course.domain.CourseQueryDto;
import com.online.college.core.course.service.ICourseService;
import com.online.college.core.vo.ConstsClassifyVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**
 * 网站主页
 */
@Controller
public class PortalController {

    @Autowired
    private IPortalBusiness portalBusiness;

    @Autowired
    private IConstsSiteCarouselService siteCarouselService;

    @Autowired
    private ICourseService courseService;

    @Autowired
    private IAuthUserService authUserService;

    @RequestMapping("/")
	public String index(Map<String,Object> model){
        System.out.println("asdfasdfasdfa..............");
        //加载轮播图片
       List<ConstsSiteCarousel> carouselList = siteCarouselService.queryCarousels(4);
        model.put("carouselList", carouselList);

        //课程分类(一级分类）
        List<ConstsClassifyVO> classifys = portalBusiness.queryAllClassify();

        //课程推荐
        portalBusiness.prepareRecomdCourses(classifys);
        model.put("classifys", classifys);

        //获取5门实战课推荐，根据权重（weight）进行排序
        CourseQueryDto queryEntity = new CourseQueryDto();
        queryEntity.setCount(5);//5门
        queryEntity.setFree(CourseEnum.FREE_NOT.value());//非免费的：实战课
        queryEntity.descSortField("weight");//按照weight降序排列
        List<Course> actionCourseList = this.courseService.queryList(queryEntity);
        model.put("actionCourseList", actionCourseList);

		//获取5门免费课程推荐，根据权重（weight）进行排序
		queryEntity.setFree(1);
		List<Course> list = courseService.getFiveCourses(queryEntity);
		model.put("freeCourseList", list);

        //获取7门java课程，根据权重（学习数量studyCount）进行排序
        queryEntity.setCount(7);
        queryEntity.setFree(null);//不分实战和免费类别
        queryEntity.setSubClassify("java");//java分类
        queryEntity.descSortField("studyCount");//按照studyCount降序排列
        List<Course> javaCourseList = this.courseService.queryList(queryEntity);
        model.put("javaCourseList", javaCourseList);

		//加载推荐的讲师
        List<AuthUser> recomdTeacherList = authUserService.queryRecomd();
        model.put("recomdTeacherList", recomdTeacherList );
		return "index";
	}

}
