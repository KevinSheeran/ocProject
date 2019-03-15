package com.online.college.core.business;

import com.online.college.core.vo.CourseSectionVO;

import java.util.List;

public interface ICourseBusiness {

	/**
	 * 获取课程章节
	 */
	List<CourseSectionVO> queryCourseSection(Long courseId);
	
}
