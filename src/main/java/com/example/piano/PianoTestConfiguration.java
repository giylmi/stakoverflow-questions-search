package com.example.piano;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;

/**
 * Created by adel on 9/15/17.
 */
public class PianoTestConfiguration extends Configuration {

    private String check;
    @JsonProperty("stackoverflow")
    private StackOverflowConfiguration stackOverflowConfiguration;

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }

    public StackOverflowConfiguration getStackOverflowConfiguration() {
        return stackOverflowConfiguration;
    }

    public void setStackOverflowConfiguration(StackOverflowConfiguration stackOverflowConfiguration) {
        this.stackOverflowConfiguration = stackOverflowConfiguration;
    }

    public static class StackOverflowConfiguration {
        private long connectTimeout = 2;
        private long readTimeout = 2;

        public long getConnectTimeout() {
            return connectTimeout;
        }

        public void setConnectTimeout(long connectTimeout) {
            this.connectTimeout = connectTimeout;
        }

        public long getReadTimeout() {
            return readTimeout;
        }

        public void setReadTimeout(long readTimeout) {
            this.readTimeout = readTimeout;
        }
    }
}
