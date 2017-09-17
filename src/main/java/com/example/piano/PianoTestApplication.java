package com.example.piano;

import com.example.piano.client.StackOverflowClient;
import com.example.piano.dao.StackOverflowDao;
import com.example.piano.health.PropsAvailableHealthCheck;
import com.example.piano.providers.PianoIllegalArgumentExceptionMapper;
import com.example.piano.resources.QuestionResource;
import com.example.piano.service.QuestionServiceImpl;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

/**
 * Created by adel on 9/15/17.
 */
public class PianoTestApplication extends Application<PianoTestConfiguration> {

    public static void main(String[] args) throws Exception {
        new PianoTestApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<PianoTestConfiguration> bootstrap) {
        bootstrap.addBundle(new AssetsBundle("/assets", "/", "index.html"));
    }

    @Override
    public String getName() {
        return "piano-test";
    }

    public void run(PianoTestConfiguration pianoTestConfiguration, Environment environment) throws Exception {
        final QuestionResource resource = new QuestionResource(
            new QuestionServiceImpl(new StackOverflowDao(
                    new StackOverflowClient(pianoTestConfiguration.getStackOverflowConfiguration().getConnectTimeout(),
                            pianoTestConfiguration.getStackOverflowConfiguration().getReadTimeout())))
        );

        environment.healthChecks().register("propertiesCheck",
                new PropsAvailableHealthCheck(pianoTestConfiguration.getCheck()));

        environment.jersey().register(new PianoIllegalArgumentExceptionMapper());
        environment.jersey().register(resource);
    }
}
