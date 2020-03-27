package com.people.app.repository;

import com.people.app.model.Course;
import com.people.app.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends CrudRepository<Student, Integer>, PagingAndSortingRepository<Student, Integer> {

    Page<Student> findAll(Pageable pageable);

    List<Student> findAll();

    Student findStudentByRut(int id);

    <S extends Student> S save(S s);

    void deleteById(Integer integer);
}
