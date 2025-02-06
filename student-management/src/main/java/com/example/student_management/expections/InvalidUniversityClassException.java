package com.example.student_management.expections;

public class InvalidUniversityClassException extends RuntimeException{

    public InvalidUniversityClassException(String message) {
        super(message);
    }
}
