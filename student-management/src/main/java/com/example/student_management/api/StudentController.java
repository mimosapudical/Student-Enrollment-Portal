package com.example.student_management.api;

import com.example.student_management.expections.InvalidUniversityClassException;
import com.example.student_management.expections.StudentEmptyNameException;
import com.example.student_management.expections.StudentNonExistException;
import com.example.student_management.model.Student;
import com.example.student_management.service.StudentService;
import org.apache.coyote.Response;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/student")
public class StudentController {

    private StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    @RequiresPermissions("student:read")
    public List<Student> getAllStudents(){
       return studentService.getAllStudents();
    }

    @GetMapping("/name")
    public List<Student> getStudents(@RequestParam String name) {
        return studentService.getStudentByName(name);
    }

    @GetMapping("/contain_name")
    public List<Student> getStudentsContainName(@RequestParam String name) {
        return studentService.getStudentsContainName(name);
    }

    @GetMapping("/class")
    public List<Student> getStudentsContainName(@RequestParam int year,@RequestParam int number) {
        return studentService.getStudentsInClass(year, number);
    }

    @RequestMapping("/register")
    @PostMapping
    public ResponseEntity<String> registerStudent(@RequestBody Student student) {
        try {
            Student savedStudent = studentService.addStudent(student);
        return ResponseEntity.ok("Registered student." + student.toString());
    } catch (StudentEmptyNameException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
    }

    @PostMapping(path = "assignClass/{sid}/{cid}")
    public ResponseEntity<String> assignClass(@PathVariable("sid") Long studentId,
                                              @PathVariable("cid") Long classId) {
        try {
            Student updatedStudent = studentService.assignClass(studentId, classId);
            return ResponseEntity.ok("Assigned class. " + updatedStudent.toString());
        } catch (StudentNonExistException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (InvalidUniversityClassException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}