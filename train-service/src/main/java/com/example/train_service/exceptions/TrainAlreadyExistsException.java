package com.example.train_service.exceptions;

public class TrainAlreadyExistsException extends RuntimeException {
    public TrainAlreadyExistsException(String message) {
        super(message);
    }
}