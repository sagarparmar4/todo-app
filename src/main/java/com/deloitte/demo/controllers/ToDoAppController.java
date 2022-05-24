package com.deloitte.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.deloitte.demo.entities.ToDoItem;
import com.deloitte.demo.services.ToDoAppService;

/**
 * Master application REST controller
 * 
 * @author Sagar Parmar
 *
 */
@RestController
public class ToDoAppController {

	@Autowired
	private ToDoAppService toDoAppService;

	/**
	 * Save ToDo items against logged-in user
	 * 
	 * @param toDoItems
	 * @return {@literal List<ToDoItem>} - List of saved ToDo items against
	 *         logged-in user
	 */
	@PostMapping("/saveToDoList")
	public List<ToDoItem> updateToDoList(@RequestBody List<ToDoItem> toDoItems) {
		return toDoAppService.saveToDoList(toDoItems);
	}

	/**
	 * Get list of ToDo items against logged-in user
	 * 
	 * @return {@literal List<ToDoItem>}
	 */
	@GetMapping("/getToDoList")
	public List<ToDoItem> getToDoList() {
		return toDoAppService.getToDoList();
	}

}
