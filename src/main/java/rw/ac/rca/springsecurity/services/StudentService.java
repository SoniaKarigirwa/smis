package rw.ac.rca.springsecurity.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.ac.rca.springsecurity.entity.Student;
import rw.ac.rca.springsecurity.entity.UserData;
import rw.ac.rca.springsecurity.exceptions.ResourceNotFoundException;
import rw.ac.rca.springsecurity.repository.StudentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    private StudentRepository repo;
    public Student addStudent(Student st) {
        try{
            return repo.save(st);
        }catch (Exception e){
            e.printStackTrace();
            throw  e;
        }
    }

//    public void getAllStudents() {
//        try {
//            repo.findAll();
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw e;
//        }
//    }

    public List<Student> listAll() {
        return repo.findAll();
    }

    public void deleteStudentById(Integer id) {
        try{
            repo.deleteById(id);
        } catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    public Student updateStudent(Integer id, Student studentDetails) {
        Optional<Student> studentOption = repo.findById(id);

        if(studentOption.isPresent()) {
            Student student = studentOption.get();
            student.setFirstName(student.getFirstName());
            student.setLastName(studentDetails.getLastName());
            student.setEmail(studentDetails.getEmail());
            return repo.save(student);
        } else {
            throw new ResourceNotFoundException("Student with id " + id + " not found");
        }
    }

    public void deleteAllStudents() {
        repo.deleteAll();
    }
}
