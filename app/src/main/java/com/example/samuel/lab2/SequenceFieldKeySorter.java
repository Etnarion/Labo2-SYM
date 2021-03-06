package com.example.samuel.lab2;

import com.thoughtworks.xstream.converters.reflection.FieldKey;
import com.thoughtworks.xstream.converters.reflection.FieldKeySorter;
import com.thoughtworks.xstream.core.util.OrderRetainingMap;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

/**
 * This class is used to sort the class attributes in the custom order to serialize
 *
 * Authors: Samuel Mayor, Alexandra Korukova, Max Caduff
 */
public class SequenceFieldKeySorter implements FieldKeySorter {
    @Override
    public Map sort(final Class type, final Map keyedByFieldKey) {
        Annotation sequence = type.getAnnotation(XMLSequence.class);
        if (sequence != null) {
            final String[] fieldsOrder = ((XMLSequence) sequence).value();
            Map result = new OrderRetainingMap();
            Set<Map.Entry<FieldKey, Field>> fields = keyedByFieldKey.entrySet();
            for (String fieldName : fieldsOrder) {
                if (fieldName != null) {
                    for (Map.Entry<FieldKey, Field> fieldEntry : fields) {
                        if
                                (fieldName.equals(fieldEntry.getKey().getFieldName())) {
                            result.put(fieldEntry.getKey(),
                                    fieldEntry.getValue());
                        }
                    }
                }
            }
            return result;
        } else {
            return keyedByFieldKey;
        }

    }
}