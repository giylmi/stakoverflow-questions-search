package com.example.piano.exception;

/**
 * Created by adel on 9/15/17.
 */
public class PianoIllegalArgumentException extends PianoTestRuntimeException {

    private String argument;
    private String reason;

    public PianoIllegalArgumentException(String argument, String reason) {
        this.argument = argument;
        this.reason = reason;
    }

    public String getArgument() {
        return argument;
    }

    public String getReason() {
        return reason;
    }
}
