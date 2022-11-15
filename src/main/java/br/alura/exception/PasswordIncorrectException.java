package br.alura.exception;

import lombok.Getter;

@Getter
public class PasswordIncorrectException extends RuntimeException {

    private static String username;

    public PasswordIncorrectException(final String username) {
        super();
        this.username = username;
    }

    @Override
    public String getMessage() {
        return String.format("Password is incorrect for user #{}", username);
    }
}
