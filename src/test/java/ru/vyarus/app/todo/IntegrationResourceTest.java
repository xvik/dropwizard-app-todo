package ru.vyarus.app.todo;

import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import org.junit.jupiter.api.Test;
import ru.vyarus.app.todo.model.Todo;
import ru.vyarus.app.todo.util.CustomClientFactory;
import ru.vyarus.dropwizard.guice.test.ClientSupport;
import ru.vyarus.dropwizard.guice.test.jupiter.TestDropwizardApp;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

/**
 * @author Vyacheslav Rusakov
 * @since 11.09.2025
 */
// custom configuration is required because otherwise asset registered on root will collide with default rest path
// alternatively, restMapping = "/rest/"  could be used (if other config fields are not important)
@TestDropwizardApp(value = TodoApp.class, clientFactory = CustomClientFactory.class,
        config = "src/test/resources/test-config.yml")
public class IntegrationResourceTest {

    @Test
    void testRest(ClientSupport client) {
        WHEN("Loading empty list");
        List<Todo> res = client.targetRest("todo/").request().buildGet().invoke(new GenericType<>() {});

        // THEN list is empty
        assertThat(res).isEmpty();

        WHEN("Add element");
        Todo added = client.post("rest/todo/", Todo.builder().title("one").completed(false).build(), Todo.class);

        // THEN record added
        assertThat(added)
                .isNotNull()
                .extracting("id", "title", "completed")
                .containsExactly(1, "one", false);

        WHEN("Add second element");
        added = client.post("rest/todo/", Todo.builder().title("two").completed(false).build(), Todo.class);

        // THEN ok
        assertThat(added)
                .isNotNull()
                .extracting("id", "title", "completed")
                .containsExactly(2, "two", false);

        WHEN("Loading not empty list");
        res = client.targetRest("todo/").request().buildGet().invoke(new GenericType<>() {});

        // THEN list is correct
        assertThat(res).hasSize(2)
                .extracting(Todo::getTitle)
                .containsExactly("one", "two");

        WHEN("Loading by ID");
        Todo item = client.get("rest/todo/1", Todo.class);

        // THEN loaded
        assertThat(item).isNotNull()
                .extracting("id").isEqualTo(1);

        WHEN("Updating entity");
        Todo patch = Todo.builder()
                .id(1)
                .title("one_custom")
                .completed(true)
                .build();
        Todo updated = client.targetRest("todo/1").request().build("PATCH", Entity.json(patch)).invoke(Todo.class);

        // THEN update ok
        assertThat(updated).isNotNull()
                .extracting("id", "title", "completed")
                .containsExactly(1, "one_custom", true);

        WHEN("Remove record");
        client.delete("rest/todo/1", Void.class);
        res = client.targetRest("todo/").request().buildGet().invoke(new GenericType<>() {});

        // THEN removed
        assertThat(res).hasSize(1)
                .extracting(Todo::getId).containsExactly(2);

    }

    private void WHEN(String message) {
        System.out.println("\n!!! [WHEN] " + message + "\n");
    }
}
