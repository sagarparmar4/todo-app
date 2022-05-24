package com.deloitte.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.deloitte.demo.entities.ToDoItem;

@Repository
public interface ToDoItemRepository extends JpaRepository<ToDoItem, Long> {

}
