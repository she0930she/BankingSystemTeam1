package com.learning.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "authority")
public class Authority implements Serializable {

    private static final long serialVersionUID = 8734140534986494039L;

    @Id
    private String username;
    private String authority;
    
	public Authority() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Authority(String username, String authority) {
		super();
		this.username = username;
		this.authority = authority;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getAuthority() {
		return authority;
	}
	public void setAuthority(String authority) {
		this.authority = authority;
	}
	@Override
	public String toString() {
		return "Authority [username=" + username + ", authority=" + authority + "]";
	}
    
}
