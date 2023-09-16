package com.sushil.todo.dao;

import com.sushil.todo.helper.Helper;
import com.sushil.todo.models.Todo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository

public class TodoDao {

    Logger logger= LoggerFactory.getLogger(TodoDao.class);


    private JdbcTemplate template;

    public TodoDao( @Autowired JdbcTemplate template) {
        this.template = template;

        // create table if does not exist

        String createTable="CREATE TABLE IF NOT EXISTS todos( id INT PRIMARY KEY, title VARCHAR(100) NOT NULL, content VARCHAR(500), status VARCHAR(10) NOT NULL, addedDate DATETIME, todoDate DATETIME); ";

        int update = template.update(createTable);
        logger.info("TODO TABLE CREATED {} ",update);
    }

    public JdbcTemplate getTemplate() {
        return template;
    }

    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }

    //	save todo in database

    public Todo saveTodo(Todo todo){
        String insertQuery=" insert into todos(id,title,content,status,addedDate,todoDate) value(?,?,?,?,?,?) ";
        int rows=template.update(insertQuery,todo.getId(),todo.getTitle(),todo.getContent(),todo.getStatus(),todo.getAddedDate(),todo.getToDoDate());
        logger.info("JDBC OPERATION: {} inserted ",rows);
        return todo;
    }
//    Get single todo from database
    public Todo getTodo(int id) throws ParseException {
        String query="select * from todos where id= ?";
        Todo todo = template.queryForObject(query, new TodoRowMapper(), id);
//        Todo todo=new Todo();
//
//        todo.setId((int)(todoData.get("id")));
//        todo.setTitle((String)(todoData.get("title")));
//        todo.setContent((String)(todoData.get("content")));
//        todo.setStatus((String)(todoData.get("status")));
//        todo.setAddedDate(Helper.parseDate((LocalDateTime) (todoData.get("addedDate"))));
//        todo.setToDoDate(Helper.parseDate((LocalDateTime) (todoData.get("todoDate"))));

       return todo;
    }

//    get all todos from database
    public List<Todo> getAllTodos(){
        String query="select * from todos";
        List<Todo> todo=template.query(query,new TodoRowMapper());
        return todo;
//        List<Map<String, Object>> maps = template.queryForList(query);
//        List<Todo> collect = maps.stream().map((map) -> {
//            Todo todo = new Todo();
//            todo.setId((int) (map.get("id")));
//            todo.setTitle((String) (map.get("title")));
//            todo.setContent((String) (map.get("content")));
//            todo.setStatus((String) (map.get("status")));
//            try {
//                todo.setAddedDate(Helper.parseDate((LocalDateTime) (map.get("addedDate"))));
//            } catch (ParseException e) {
//                throw new RuntimeException(e);
//            }
//            try {
//                todo.setToDoDate(Helper.parseDate((LocalDateTime) (map.get("todoDate"))));
//            } catch (ParseException e) {
//                throw new RuntimeException(e);
//            }
//            return todo;
//        }).collect(Collectors.toList());
//       return collect;
    }

    // update todo
    public Todo updateTodo(int id,Todo newTodo){
        String query="update todos set title=?,content=?,status=?,addedDate=?,todoDate=? WHERE id=?";
        int update = template.update(query, newTodo.getTitle(), newTodo.getContent(), newTodo.getStatus(), newTodo.getAddedDate(), newTodo.getToDoDate(),id);
        logger.info("UPDATED {}",update);
        newTodo.setId(id);
        return newTodo;
    }

    //delete todo from database
    public void deleteTodo(int id){
        String query="delete from todos WHERE id=?";
        int update = template.update(query, id);
        logger.info("DELETED {}",update);
    }

    public void deleteMultiple(int[] ids){
        String query="delete from todos WHERE id=?";
        int[] ints = template.batchUpdate(query, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                int id = ids[i];
                ps.setInt(1, id);
            }

            @Override
            public int getBatchSize() {
                return ids.length;
            }
        });
        for(int i:ints){
            logger.info(" DELETED {} : ",i);
        }
    }
}
