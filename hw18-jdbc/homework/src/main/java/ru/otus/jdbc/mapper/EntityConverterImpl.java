package ru.otus.jdbc.mapper;

import ru.otus.jdbc.exceptions.MetaDataException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EntityConverterImpl<T> implements EntityConverter<T> {

    private final EntityClassMetaData<T> entityClassMetaData;

    public EntityConverterImpl(EntityClassMetaData<T> entityClassMetaData) {
        this.entityClassMetaData = entityClassMetaData;
    }


    public T convert(ResultSet resultSet) {
        try {
            if (resultSet.next()) {
                return extracted(resultSet);
            }
            return null;
        } catch (Exception e) {
            throw new MetaDataException(e);
        }
    }

    @Override
    public List<T> convertAll(ResultSet resultSet) {
        try {
            ArrayList<T> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(extracted(resultSet));
            }
            return list;
        } catch (Exception e) {
            throw new MetaDataException(e);
        }

    }

    private T extracted(ResultSet resultSet) {
        try {
            Constructor<T> constructor = entityClassMetaData.getConstructor();
            T t = constructor.newInstance();
            for (Field field : entityClassMetaData.getAllFields()) {
                field.setAccessible(true);
                field.set(t, resultSet.getObject(field.getName()));
            }
            return t;
        } catch (Exception e) {
            throw new MetaDataException(e);
        }
    }
}
