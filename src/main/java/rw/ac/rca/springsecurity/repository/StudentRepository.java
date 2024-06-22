package rw.ac.rca.springsecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.ac.rca.springsecurity.entity.Student;
@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
}

