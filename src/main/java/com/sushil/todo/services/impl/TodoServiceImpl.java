package com.sushil.todo.services.impl;

import com.sushil.todo.models.Todo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TodoServiceImpl implements com.sushil.todo.services.TodoService {
    Logger logger= LoggerFactory.getLogger(TodoServiceImpl.class);
    List<Todo> todos=new ArrayList<>();
//    create todo method
    public Todo createTodo(Todo todo){
//        create....
//        change the logic
        todos.add(todo);
        logger.info("Todos {} ",this.todos);
        return todo;
    }

    public List<Todo> getAllTodos() {
        return todos;
    }

    public Todo getTodo(int todoId){
//        for(int i=0;i<todos.size();i++){
//            if(todos.get(i).getId()==todoId) {
//                logger.info("TODO : {}",todos.get(i));
//                return todos.get(i);
//            }
//        }
//
//        return null;    // we can also do by above logic
        Todo  todo=todos.stream().filter(t-> todoId==t.getId()).findAny().get();
        logger.info("TODO : {}",todo);
        return todo;
    }

    public Todo updateTodo(int todoId, Todo todoWithNewDetails) {
       List<Todo> newUpdateList= todos.stream().map(t->{
            if(t.getId()==todoId){
                t.setContent(todoWithNewDetails.getContent());
                t.setStatus(todoWithNewDetails.getStatus());
                t.setTitle(todoWithNewDetails.getTitle());
                return t;
            }
            else{
                return t;
            }
        }).collect(Collectors.toList());
       todos=newUpdateList;
       todoWithNewDetails.setId(todoId);
       return todoWithNewDetails;
    }

    public void deleteTodo(int todoId) {
        logger.info("Deleting info");
          List<Todo> newList =todos.stream().filter(t->t.getId()!=todoId).collect(Collectors.toList());
          todos=newList;
    }
}
