package com.example.ilyastp5;

public class Note {
    private String description;
    private long id;

    public Note(String description, long id) {
        this.description = description;
        this.id = id;
    }

    public Note() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
