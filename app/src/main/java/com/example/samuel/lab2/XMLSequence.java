package com.example.samuel.lab2;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Custom annotation for setting the order of the serialized class attributes
 *
 * Authors: Samuel Mayor, Alexandra Korukova, Max Caduff
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface XMLSequence {
    String[] value();
} 