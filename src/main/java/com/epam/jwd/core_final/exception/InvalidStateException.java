package com.epam.jwd.core_final.exception;

public class InvalidStateException extends Exception {
    // todo

    private final String fileName;

    public InvalidStateException(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String getMessage() {
        return "Input file " + this.fileName + " not found. Please load correct input files and restart the program";
    }


}
