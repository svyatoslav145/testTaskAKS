package com.example.TestTaskOvsiyAurosKS.exception;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(long entityId) {
        super(String.valueOf(entityId).concat(" not found."));
    }
}
