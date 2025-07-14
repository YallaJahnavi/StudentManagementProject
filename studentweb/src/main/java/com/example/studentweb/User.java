package com.example.studentweb;

import jakarta.persistence.*;

@Entity                 // ➜ table USER
@Table(name = "users")  // (optional) pick your own table name
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)   // auto‑increment PK
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    private String category;   // TEACHER / STUDENT

    public User() {}
    public User(String username, String password, String category) {
        this.username = username;
        this.password = password;
        this.category = category;
    }
    /* getters / setters omitted for brevity */
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
    
    
}
