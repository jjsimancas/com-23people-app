package com.people.app.service.api;

import com.people.app.model.Course;
import com.people.app.repository.CourseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CourseInfService {

    private final CourseRepository courseRepository;

    public CourseInfService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Page<Course> getAllCourseByPage(){
        Pageable pageRequest =  PageRequest.of(0, 5, Sort.by(
                Sort.Order.asc("code")
        ));
        return courseRepository.findAll(pageRequest);
    }

    public List<Course> getAllCourses(){
        return courseRepository.findAll();
    }
}
