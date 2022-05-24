package com.deloitte.demo.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Entity class for ToDo items
 * 
 * @author Sagar Parmar
 *
 */
@Entity
@Table(name = "todo_items")
public class ToDoItem extends AbstractEntity {

	private String name;

	private String description;

	private Boolean completed;

	public ToDoItem() {
		super();
	}

	public ToDoItem(String name, String description, Boolean completed) {
		super();
		this.name = name;
		this.description = description;
		this.completed = completed;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getCompleted() {
		return completed;
	}

	public void setCompleted(Boolean completed) {
		this.completed = completed;
	}

}
