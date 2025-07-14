package com.example.studentweb;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class StudentController {

    List<Student> studentList = new ArrayList<>();

//    @GetMapping("/")
//    public String home(Model model) {
//        model.addAttribute("student", new Student());
//        return "index"; // loads index.html
//    }
    
    @GetMapping("/")
    public String landingPage() {
        return "landing"; // loads landing.html
    }
    
    @GetMapping("/form")
    public String studentForm(Model model) {
        model.addAttribute("student", new Student());
        return "index"; // loads index.html
    }


    @PostMapping("/addStudent")
    public String addStudent(@ModelAttribute Student student, Model model) {
    	for (Student s : studentList) {
            if (s.getId() == student.getId()) {
                model.addAttribute("error", "User ID already exists!");
                model.addAttribute("student", new Student()); // Reset form
                return "index";
            }
        }
    	
    	studentList.add(student);
        model.addAttribute("message", "Student added successfully!");
        model.addAttribute("student", new Student()); // Reset form
        return "index";
    }

    @GetMapping("/viewStudents")
    public String viewStudents(Model model) {
        model.addAttribute("students", studentList);
        return "students"; // loads students.html
    }
}
