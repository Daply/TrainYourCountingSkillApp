package com.dariapro.trainyourcountingskills.exception;

public class ExtraIsNullException extends Exception {

    public ExtraIsNullException (String errorMessage) {
        super(errorMessage);
    }

    public ExtraIsNullException (String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
