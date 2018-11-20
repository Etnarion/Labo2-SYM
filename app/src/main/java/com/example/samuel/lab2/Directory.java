package com.example.samuel.lab2;

import java.util.ArrayList;

public class Directory {
    private ArrayList<Person> persons;

    public Directory() {
        persons = new ArrayList<>();
    }

    public ArrayList<Person> getPersons() {
        return persons;
    }

    public void setPersons(ArrayList<Person> persons) {
        this.persons = persons;
    }

    public void add(Person person) {
        persons.add(person);
    }

    public String toString() {
        StringBuilder result = new StringBuilder("Directory : \n");
        for (Person person : persons) {
            result.append(person.toString());
        }
        return result.toString();
    }
}
