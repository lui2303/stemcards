package com.luiswiederhold.backend.flashcards.exception;

public class LowConfidenceException extends Exception{
    public LowConfidenceException(String exception) {
        super(exception);
    }
}
