package ru.otus.jdbc.mapper.impl;

import ru.otus.anatations.Id;
import ru.otus.jdbc.exceptions.MetaDataException;
import ru.otus.jdbc.mapper.EntityClassMetaData;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {

    private final String name;
    private final Constructor<T> constructor;
    private final Field idField;
    private final List<Field> allField;
    private final List<Field> fieldsWithoutId;

    public EntityClassMetaDataImpl(Class<T> clazz) {
        try {
            this.name = clazz.getSimpleName().toLowerCase();
            this.constructor = clazz.getDeclaredConstructor();
            this.allField = List.of(clazz.getDeclaredFields());
            this.fieldsWithoutId = new ArrayList<>(allField.size() - 1);
            Field tempIdField = null;
            for (Field field : allField) {
                if (field.getAnnotation(Id.class) != null) {
                    if (tempIdField == null) {
                        tempIdField = field;
                    } else {
                        throw new MetaDataException("Должно быть только одно поле с аннотацией Id");
                    }
                } else {
                    fieldsWithoutId.add(field);
                }
            }
            if (tempIdField == null) {
                throw new MetaDataException("Хотя бы одно поле должно быть помечено аннотацией Id");
            }
            this.idField = tempIdField;

        } catch (Exception e) {
            throw new MetaDataException(e);
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Constructor<T> getConstructor() {
        return constructor;
    }

    @Override
    public Field getIdField() {
        return idField;
    }

    @Override
    public List<Field> getAllFields() {
        return allField;
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return fieldsWithoutId;
    }
}
