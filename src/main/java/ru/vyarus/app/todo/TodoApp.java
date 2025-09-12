package ru.vyarus.app.todo;

import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.core.Application;
import io.dropwizard.core.Configuration;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;
import ru.vyarus.dropwizard.guice.GuiceBundle;

/**
 * @author Vyacheslav Rusakov
 * @since 11.09.2025
 */
public class TodoApp extends Application<Configuration> {

    public static void main(String[] args) throws Exception {
        new TodoApp().run(args);
    }

    @Override
    public void initialize(Bootstrap<Configuration> bootstrap) {
        bootstrap.addBundle(GuiceBundle.builder()
                .enableAutoConfig()
                .build());

        bootstrap.addBundle(new AssetsBundle("/assets/", "/", "index.html"));
    }

    @Override
    public void run(Configuration configuration, Environment environment) throws Exception {
    }
}
