package com.demo.throwable;

import java.io.FileNotFoundException;

public class ErrorAndException {
    private void throwError() {
        throw new StackOverflowError();
    }

    private void throwRuntimeException() {
        throw new NullPointerException();
    }

    private void throwCheckdException() throws FileNotFoundException {
        throw new FileNotFoundException();
    }

    public static void main(String[] args) {
        ErrorAndException err = new ErrorAndException();
        err.throwError();
        err.throwRuntimeException();
        try {
            err.throwCheckdException();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
