package ru.vyarus.app.todo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Vyacheslav Rusakov
 * @since 11.09.2025
 */
@Data
@EqualsAndHashCode
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Todo {

    private String title;
    private int id;
    private Boolean completed;

    public Todo update(Todo patch) {

        if (patch.completed != null) {
            completed = patch.completed;
        }

        if (patch.title != null) {
            title = patch.title;
        }

        return this;
    }
}
