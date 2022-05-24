package com.deloitte.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deloitte.demo.entities.ToDoItem;
import com.deloitte.demo.entities.User;
import com.deloitte.demo.repositories.ToDoItemRepository;

/**
 * Primary business logic for TODO application
 * 
 * @author Sagar Parmar
 *
 */
@Service
public class ToDoAppService {

	@Autowired
	ToDoItemRepository toDoItemRepository;

	@Autowired
	UserService userService;

	/**
	 * Save ToDo items against logged-in user
	 * 
	 * @param toDoItems
	 * @return {@literal List<ToDoItem>} - List of saved ToDo items against
	 *         logged-in user
	 */
	public List<ToDoItem> saveToDoList(List<ToDoItem> toDoItems) {
//		toDoItems.stream().forEach(item -> item.setId(null));
		List<ToDoItem> savedItems = toDoItemRepository.saveAll(toDoItems);

		User existingUser = userService.getLoggedInUser();
		existingUser.setToDoItems(savedItems);
		return userService.saveUser(existingUser).getToDoItems();
	}

	/**
	 * Get list of ToDo items against logged-in user
	 * 
	 * @return {@literal List<ToDoItem>}
	 */
	public List<ToDoItem> getToDoList() {
		return userService.getLoggedInUser().getToDoItems();
	}

}
