package com.example.samuel.lab2;

import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.converters.extended.ToAttributedValueConverter;

@XStreamConverter(value = ToAttributedValueConverter.class, strings = {"number"})

/**
 * This class represents a phone number model
 *
 * Authors: Samuel Mayor, Alexandra Korukova, Max Caduff
 */
public class Phone {
    String number;
    @XStreamAsAttribute
    String type;

    /**
     * Default constructor
     */
    public Phone() {}

    /**
     * Construtcor
     * @param number phone number
     * @param type phone number's type (for example, "home" or "work")
     */
    public Phone(String number, String type) {
        this.number = number;
        this.type = type;
    }

    /**
     * Getter
     * @return the phone number
     */
    public String getNumber() {
        return number;
    }

    /**
     * Setter
     * @param number the phone number to set
     */
    public void setNumber(String number) {
        this.number = number;
    }

    /**
     * Getter
     * @return the phone number's type
     */
    public String getType() {
        return type;
    }

    /**
     * Setter
     * @param type the phone number's type to set
     */
    public void setType(String type) {
        this.type = type;
    }
}
