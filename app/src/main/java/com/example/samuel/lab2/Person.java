package com.example.samuel.lab2;

@XMLSequence({
        "name",
        "firstname",
        "middlename",
        "gender",
        "phone",
})
public class Person {
    private String name;
    private String firstname;
    private String middlename;
    private String gender;
    private Phone phone;

    public Person() {}

    public Person(String name, String firstname, String gender, Phone phone) {
        this.name = name;
        this.firstname = firstname;
        this.middlename = "";
        this.gender = gender;
        this.phone = phone;
    }

    public Person(String name, String firstname, String middlename, String gender, Phone phone) {
        this.name = name;
        this.firstname = firstname;
        this.middlename = middlename;
        this.gender = gender;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Phone getPhone() {
        return phone;
    }

    public void setPhone(Phone phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Received object : \n" +
                "name : " + name + "\n" +
                "first name : " + firstname + "\n" +
                "middle name : " + middlename + "\n" +
                "gender : " + gender + "\n" +
                phone.getType() + " phone : " + phone.getNumber() + "\n";
    }
}
