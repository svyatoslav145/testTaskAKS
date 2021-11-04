package com.example.TestTaskOvsiyAurosKS.exception;

public class NameExistsException extends RuntimeException{

    public NameExistsException(String name) {
        super(name.concat(" already exists, try other name"));
    }
}
