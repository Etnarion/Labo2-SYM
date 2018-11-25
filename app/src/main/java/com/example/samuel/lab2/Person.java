package com.example.samuel.lab2;

/**
 * Order of the attributes in the serialized object
 */
@XMLSequence({
        "name",
        "firstname",
        "middlename",
        "gender",
        "phone",
})

/**
 * This class represents a Person model
 *
 * Authors: Samuel Mayor, Alexandra Korukova, Max Caduff
 */
public class Person {
    private String name;
    private String firstname;
    private String middlename;
    private String gender;
    private Phone phone;

    /**
     * Default constructor
     */
    public Person() {}

    /**
     * Constructor
     * @param name {@link Person}'s last name
     * @param firstname {@link Person}'s first name
     * @param gender {@link Person}'s gender
     * @param phone {@link Person}'s phone number
     */
    public Person(String name, String firstname, String gender, Phone phone) {
        this.name = name;
        this.firstname = firstname;
        this.middlename = "";
        this.gender = gender;
        this.phone = phone;
    }

    /**
     * Constructor
     * @param name {@link Person}'s last name
     * @param firstname {@link Person}'s first name
     * @param middlename {@link Person}'s middle name
     * @param gender {@link Person}'s gender
     * @param phone {@link Person}'s phone number
     */
    public Person(String name, String firstname, String middlename, String gender, Phone phone) {
        this.name = name;
        this.firstname = firstname;
        this.middlename = middlename;
        this.gender = gender;
        this.phone = phone;
    }

    /**
     * Getter
     * @return {@link Person}'s last name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter
     * @param name {@link Person}'s last name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter
     * @return {@link Person}'s first name
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * Setter
     * @param firstname {@link Person}'s first name to set
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * Getter
     * @return {@link Person}'s middle name to set
     */
    public String getMiddlename() {
        return middlename;
    }

    /**
     * Setter
     * @param middlename {@link Person}'s middle name to set
     */
    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    /**
     * Getter
     * @return {@link Person}'s gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * Setter
     * @param gender {@link Person}'s gender to set
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Getter
     * @return {@link Person}'s phone number
     */
    public Phone getPhone() {
        return phone;
    }

    /**
     * Setter
     * @param phone {@link Person}'s phone number to set
     */
    public void setPhone(Phone phone) {
        this.phone = phone;
    }

    /**
     * Converts the {@link Person} object to its String representation
     * @return the resulting {@link Person} String representation
     */
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
