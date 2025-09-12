package ru.vyarus.app.todo.resource;

import com.google.common.collect.Maps;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import ru.vyarus.app.todo.model.Todo;

import java.util.Collection;
import java.util.Map;

/**
 * @author Vyacheslav Rusakov
 * @since 11.09.2025
 */
@Path("/todo")
@Produces(MediaType.APPLICATION_JSON)
public class TodoResource {

    private Map<Integer, Todo> todos = Maps.newHashMap();
    private int counter = 0;

    @GET
    public Collection<Todo> get() {
        return todos.values();
    }

    @GET
    @Path("{id}")
    public Todo getById(@PathParam("id") int id) {
        return todos.get(id);
    }

    @POST
    public Todo addTodos(Todo todo) {
        todo.setId(++counter);
        todo.setCompleted(false);
        todos.put(counter, todo);
        return todo;
    }

    @DELETE
    public void delete() {
        todos.clear();
    }

    @DELETE
    @Path("{id}")
    public void deleteById(@PathParam("id") int id) {
        todos.remove(id);
    }

    @PATCH
    @Path("{id}")
    public Todo edit(@PathParam("id") int id, Todo patch) {
        Todo todo = todos.get(id);
        Todo patchedTodo = todo.update(patch);
        todos.put(id, patchedTodo);
        return patchedTodo;
    }
}
