package com.learning.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.Id;
import javax.persistence.Table;

//import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "user")
public class User {
	
    @Id
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;

    @Column(name = "enabled")
    private boolean enabled;
    
    @Column(name = "fullname")
    private String fullname;
    
    @Column(name = "id")
    private int id;

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(String username, String password, boolean enabled, String fullname, int id) {
		super();
		this.username = username;
		this.password = password;
		this.enabled = enabled;
		this.fullname = fullname;
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

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", enabled=" + enabled + ", fullname="
				+ fullname + ", id=" + id + "]";
	}

	
}
