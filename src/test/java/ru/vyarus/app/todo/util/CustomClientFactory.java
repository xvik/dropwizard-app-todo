package ru.vyarus.app.todo.util;

import io.dropwizard.jersey.jackson.JacksonFeature;
import io.dropwizard.testing.DropwizardTestSupport;
import org.glassfish.jersey.apache5.connector.Apache5ConnectorProvider;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.client.HttpUrlConnectorProvider;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.glassfish.jersey.logging.LoggingFeature;
import ru.vyarus.dropwizard.guice.test.client.DefaultTestClientFactory;
import ru.vyarus.dropwizard.guice.test.client.TestClientFactory;

import java.util.logging.Level;

/**
 * Default client, based on HttpURLConnection will not allow PATCH method calls on JDK > 16 (without
 * custom --add-opens: <a href="http://blog.supol.cz/?p=320">related article</a>)
 *
 * @author Vyacheslav Rusakov
 * @since 11.09.2025
 */
public class CustomClientFactory implements TestClientFactory {

    @Override
    public JerseyClient create(DropwizardTestSupport<?> support) {
        final JerseyClientBuilder builder = new JerseyClientBuilder()
                .register(new JacksonFeature(support.getEnvironment().getObjectMapper()))
                // log everything to simplify debug
                .register(LoggingFeature.builder()
                        .withLogger(new DefaultTestClientFactory.ConsoleLogger())
                        .verbosity(LoggingFeature.Verbosity.PAYLOAD_TEXT)
                        .level(Level.INFO)
                        .build())
                .property(ClientProperties.CONNECT_TIMEOUT, 1000)
                .property(ClientProperties.READ_TIMEOUT, 5000)
                .property(HttpUrlConnectorProvider.SET_METHOD_WORKAROUND, true);
        // override default HttpUrlConnectorProvider to support PATCH on jdk > 16
        builder.getConfiguration().connectorProvider(new Apache5ConnectorProvider());
        return builder.build();
    }
}
