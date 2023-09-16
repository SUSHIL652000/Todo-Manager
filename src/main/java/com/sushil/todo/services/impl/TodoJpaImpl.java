package com.sushil.todo.services.impl;

import com.sushil.todo.dao.TodoRepository;
import com.sushil.todo.models.Todo;
import com.sushil.todo.services.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;

@Service
@Primary
public class TodoJpaImpl implements TodoService {
    @Autowired
    private TodoRepository todoRepository;
    @Override
    public Todo createTodo(Todo todo) {
        return todoRepository.save(todo);
    }

    @Override
    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    @Override
    public Todo getTodo(int todoId) throws ParseException {
        return todoRepository.findById(todoId).orElseThrow(()-> new RuntimeException("todo not found"));
    }

    @Override
    public Todo updateTodo(int todoId, Todo todoWithNewDetails) {
       Todo todo= todoRepository.findById(todoId).orElseThrow(()-> new RuntimeException("todo not found"));
       todo.setTitle(todoWithNewDetails.getTitle());
        todo.setContent(todoWithNewDetails.getContent());
        todo.setStatus(todoWithNewDetails.getStatus());
        todo.setToDoDate(todoWithNewDetails.getToDoDate());
        return todoRepository.save(todo);
    }

    @Override
    public void deleteTodo(int todoId) {
        Todo todo= todoRepository.findById(todoId).orElseThrow(()-> new RuntimeException("todo not found"));
        todoRepository.delete(todo);
    }
}
