package com.vironit.kazimirov.entity;

public enum Status {
    NEW ("NEW"),
    IN_PROCESS("IN PROCESS"),
    REGISTRATE ("REGISTRATE"),
    CANCELED ("CANCELED");
    private String text;

    Status() {
    }

    Status(String text) {
        this.text=text;
    }

    public String getText() {
        return text;
    }
}
