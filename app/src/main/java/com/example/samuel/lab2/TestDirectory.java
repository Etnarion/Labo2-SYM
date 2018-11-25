package com.example.samuel.lab2;

public class TestDirectory {
    private Directory directory;
    private int size;
    public TestDirectory() {
        directory = new Directory();
    }

    public Directory generate(int size) {
        for (int i = 0; i < size; i++) {
            String name = "name" + i,
                    firstName = "first_name" + i,
                    midName = "mid_name" + i,
                    gender = "none";
            Phone phone = new Phone(String.valueOf(i), "type");
            directory.add(new Person(name, firstName, midName, gender, phone));
        }

        return directory;
    }

}
