package com.dizplai.voting_api.exceptions;

public class PollTooLargeException extends Exception {

    public PollTooLargeException(String message) {
        super(message);
    }
}
