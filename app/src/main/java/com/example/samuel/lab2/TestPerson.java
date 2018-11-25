package com.example.samuel.lab2;

public class TestPerson {
    private Person person;

    public TestPerson() {
    }

    public Person generate(int fieldSize) {
        String sample = "";
        for (int i = 0; i < fieldSize; i++) {
            sample += "a";
        }
        person = new Person(sample, sample, sample, sample, new Phone(sample, sample));
        return person;
    }
}
