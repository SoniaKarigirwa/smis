package rw.ac.rca.springsecurity.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rw.ac.rca.springsecurity.entity.Laptop;
import rw.ac.rca.springsecurity.entity.Student;
import rw.ac.rca.springsecurity.entity.UserData;
import rw.ac.rca.springsecurity.exceptions.ResourceNotFoundException;
import rw.ac.rca.springsecurity.services.JwtService;
import rw.ac.rca.springsecurity.services.StudentService;
import rw.ac.rca.springsecurity.services.UserDataService;

import java.util.List;

@RestController
@RequestMapping("/academics")
public class StudentController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private UserDataService userServices;
    @Autowired
    private JwtService jwtService;
    @PostMapping("/registration")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Student addStudent(@RequestBody Student student, HttpServletRequest request) {
        try{
            String authHeader = request.getHeader("Authorization");
            String token = null;
            String username = null;
            if (authHeader != null && authHeader.startsWith("Bearer")) {
                token = authHeader.substring(7);
                username = jwtService.extractUsername(token);
            }
            UserData info = userServices.loadCurrentUser(username);
            System.out.println(info.getEmail());
            student.setCreated(info);
            return studentService.addStudent(student);
        }catch (Exception e){
            System.out.println(e.getMessage());
            throw e;
        }

    }
    @PutMapping("/updateStudent/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable int id, @RequestBody Student studentDetails) {
        try {
            return ResponseEntity.ok(studentService.updateStudent(id, studentDetails));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/getAll")
    public List<Student> getAllUsers(){
        return  studentService.listAll();
    }

    @DeleteMapping("/deleteAll")
    public void deleteAll() {
        try {
            studentService.deleteAllStudents();
        } catch (Exception e) {
            System.out.println(e.getMessage());
             throw e;
        }
    }

    @GetMapping("/info")
    public String info() {
        return "Amazing day";
    }
}
