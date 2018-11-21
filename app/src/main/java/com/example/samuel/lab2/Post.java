package com.example.samuel.lab2;

public class Post {
    private int id;
    private String title;
    private String description;
    private String content;
    private String date;

    public Post() {}

    public Post(int id, String title, String description, String content, String date) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.content = content;
        this.date = date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return id + " " + title + "\n" + description + "\n" + content + "\n" + date;
    }
}
