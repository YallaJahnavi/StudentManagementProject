package com.example.studentweb;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class StudentController {

    List<Student> studentList = new ArrayList<>();

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("student", new Student());
        return "index"; // loads index.html
    }

    @PostMapping("/addStudent")
    public String addStudent(@ModelAttribute Student student, Model model) {
        studentList.add(student);
        model.addAttribute("message", "Student added successfully!");
        model.addAttribute("student", new Student()); // reset form
        return "index";
    }

    @GetMapping("/viewStudents")
    public String viewStudents(Model model) {
        model.addAttribute("students", studentList);
        return "students"; // loads students.html
    }
}
