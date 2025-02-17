package com.example.student_management.expections;

public class StudentEmptyNameException extends RuntimeException{
    public StudentEmptyNameException(String message) {
        super(message);
    }
}