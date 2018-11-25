package com.example.samuel.lab2;

/**
 * This class represents an author model
 *
 * Authors: Samuel Mayor, Alexandra Korukova, Max Caduff
 */
public class Author {
    private int id;
    private String first_name;
    private String last_name;

    /**
     * Default constructor
     */
    public Author() {}

    /**
     * Constructor
     * @param id {@link Author}'s id
     * @param first_name {@link Author}'s first name
     * @param last_name {@link Author}'s last name
     */
    public Author(int id, String first_name, String last_name) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
    }

    /**
     * Getter
     * @return id of the {@link Author}
     */
    public int getId() {
        return id;
    }

    /**
     * Setter
     * @param id id of the {@link Author} to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter
     * @return the {@link Author}'s first name
     */
    public String getFirst_name() {
        return first_name;
    }

    /**
     * Setter
     * @param first_name {@link Author}'s first name to set
     */
    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    /**
     * Getter
     * @return {@link Author}'s last name
     */
    public String getLast_name() {
        return last_name;
    }

    /**
     * Setter
     * @param last_name {@link Author}'s last name to set
     */
    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    /**
     * Converts an {@link Author} to its String representation
     * @return the resulting String representation of the {@link Author}
     */
    @Override
    public String toString() {
        return id + " " + first_name + " " + last_name;
    }
}
