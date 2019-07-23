package com.dariapro.traincounting.exception;

public class ExtraIsNullException extends Exception {

    public ExtraIsNullException (String errorMessage) {
        super(errorMessage);
    }

    public ExtraIsNullException (String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
