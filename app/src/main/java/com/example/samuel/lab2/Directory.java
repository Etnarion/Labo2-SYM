package com.example.samuel.lab2;

import java.util.ArrayList;

/**
 * This class is used as a container containing a list of {@link Person}s
 *
 * Authors: Samuel Mayor, Alexandra Korukova, Max Caduff
 */
public class Directory {
    private ArrayList<Person> persons;

    /**
     * Constructor
     */
    public Directory() {
        persons = new ArrayList<>();
    }

    /**
     * Getter
     * @return the list of {@link Directory}'s {@link Person}s
     */
    public ArrayList<Person> getPersons() {
        return persons;
    }

    /**
     * Setter
     * @param persons the list of {@link Person}s to set
     */
    public void setPersons(ArrayList<Person> persons) {
        this.persons = persons;
    }

    /**
     * Adds a new {@link Person} to the {@link Directory}
     * @param person the {@link Person} to add
     */
    public void add(Person person) {
        persons.add(person);
    }

    /**
     * Converts the {@link Directory} to its String representation
     * @return the resulting String representation of the {@link Directory}
     */
    public String toString() {
        StringBuilder result = new StringBuilder("Directory : \n");
        for (Person person : persons) {
            result.append(person.toString());
        }
        return result.toString();
    }
}
