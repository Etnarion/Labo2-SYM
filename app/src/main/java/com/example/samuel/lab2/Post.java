package com.example.samuel.lab2;

/**
 * This class represent a post model
 *
 * Authors: Samuel Mayor, Alexandra Korukova, Max Caduff
 */
public class Post {
    private int id;
    private String title;
    private String description;
    private String content;
    private String date;

    /**
     * Default constructor
     */
    public Post() {}

    /**
     * Constructor
     * @param id {@link Post}'s id
     * @param title the title of the {@link Post}
     * @param description {@link Post} description
     * @param content {@link Post} content
     * @param date the date of the {@link Post}
     */
    public Post(int id, String title, String description, String content, String date) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.content = content;
        this.date = date;
    }

    /**
     * Setter
     * @param id the id of the {@link Post} to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter
     * @return the id of the {@link Post}
     */
    public int getId() {
        return id;
    }

    /**
     * Getter
     * @return the title fo the {@link Post}
     */
    public String getTitle() {
        return title;
    }

    /**
     * Setter
     * @param title the title of the {@link Post} to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Getter
     * @return the description of the {@link Post}
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter
     * @param description the description of the {@link Post} to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter
     * @return the content of the {@link Post}
     */
    public String getContent() {
        return content;
    }

    /**
     * Setter
     * @param content the content of the {@link Post} to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Getter
     * @return the date of the {@link Post}
     */
    public String getDate() {
        return date;
    }

    /**
     * Setter
     * @param date the date of the {@link Post} to set
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Converts the {@link Post} to its String representation
     * @return the resulting String representation of the {@link Post}
     */
    @Override
    public String toString() {
        return id + " " + title + "\n" + description + "\n" + content + "\n" + date;
    }
}
