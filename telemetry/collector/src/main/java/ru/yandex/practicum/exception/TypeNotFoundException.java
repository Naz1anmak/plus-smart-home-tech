package ru.yandex.practicum.exception;

public class TypeNotFoundException extends RuntimeException {
    public TypeNotFoundException(String message) {
        super(message);
    }
}
