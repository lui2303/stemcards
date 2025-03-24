package com.luiswiederhold.backend.exception;

public class LowConfidenceException extends Exception{
    public LowConfidenceException(String exception) {
        super(exception);
    }
}
