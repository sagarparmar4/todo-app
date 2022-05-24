package com.deloitte.demo.entities;

import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;

/**
 * Entity class for users and security principal context
 * 
 * @author Sagar Parmar
 *
 */
@Entity
@Table(name = "users")
public class User extends AbstractEntity implements UserDetails {

	private static final long serialVersionUID = 1L;

	@Column(unique = true)
	private String username;

	@NotNull
	private String firstName;

	@NotNull
	private String lastName;

	@JsonIgnore
	private String password;

	@OneToMany(fetch = FetchType.EAGER)
	private List<ToDoItem> toDoItems;

	public User() {
		super();
	}

	public User(String username, String firstName, String lastName, String password, List<ToDoItem> toDoItems) {
		super();
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.toDoItems = toDoItems;
	}

	@Override
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<ToDoItem> getToDoItems() {
		return toDoItems;
	}

	public void setToDoItems(List<ToDoItem> toDoItems) {
		this.toDoItems = toDoItems;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO: Add roles and authorities against users
		return null;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
