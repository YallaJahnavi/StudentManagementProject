package com.example.studentweb;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/* Custom exception for bad credentials */
class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException(String msg) { super(msg); }
}

@Controller
public class StudentController {

    /* -------- In‑memory storage (swap for DB later) -------- */
    private final List<User>    userList    = new ArrayList<>();
    private final List<Student> studentList = new ArrayList<>();

    /* ---------------- Landing ---------------- */
    @GetMapping("/")
    public String landing() { return "landing"; }          // templates/landing.html

    /* ---------------- Registration ---------------- */
    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new User());
        return "register";                                  // templates/register.html
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user,
                               RedirectAttributes ra,
                               Model model) {
        /* duplicate username check */
        for (User u : userList)
            if (u.getUsername().equalsIgnoreCase(user.getUsername())) {
                model.addAttribute("error", "Username already exists!");
                return "register";
            }
        userList.add(user);                                 // save user
        ra.addFlashAttribute("message", "User registered successfully!");
        return "redirect:/login";
    }

    /* ---------- LOGIN (GET) ---------- */
    @GetMapping("/login")
    public String loginPage(Model model,
                            @ModelAttribute("error") String error) {
        model.addAttribute("error", error);
        return "login";
    }

    /* ---------- LOGIN (POST) ---------- */
    @PostMapping("/login")
    public String doLogin(@RequestParam String username,
                          @RequestParam String password) {

        for (User u : userList) {
            if (u.getUsername().equalsIgnoreCase(username)
                    && u.getPassword().equals(password)) {
                return "redirect:/index"; // successful login
            }
        }

        // If not found: throw exception
        throw new InvalidCredentialsException("Invalid username or password");
    }

    /* ---------- Handle Login Exception ---------- */
    @ExceptionHandler(InvalidCredentialsException.class)
    public String handleLoginError(InvalidCredentialsException ex,
                                   RedirectAttributes redirect) {
        redirect.addFlashAttribute("error", ex.getMessage());
        return "redirect:/login";
    }
    
    /* ---------------- Student page (index.html) ---------------- */
    @GetMapping("/index")
    public String studentPage(Model model) {
        model.addAttribute("student", new Student());
        return "index";                                   // templates/index.html
    }

    @PostMapping("/addStudent")
    public String addStudent(@ModelAttribute Student student, Model model) {
        for (Student s : studentList)
            if (s.getId() == student.getId()) {
                model.addAttribute("error", "User ID already exists!");
                model.addAttribute("student", new Student());
                return "index";
            }

        studentList.add(student);
        model.addAttribute("message", "Student added successfully!");
        model.addAttribute("student", new Student());
        return "index";
    }

    @GetMapping("/viewStudents")
    public String viewStudents(Model model) {
        model.addAttribute("students", studentList);
        return "students";                                // templates/students.html
    }
}

