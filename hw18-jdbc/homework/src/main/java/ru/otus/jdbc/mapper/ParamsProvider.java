package ru.otus.jdbc.mapper;

import java.util.List;

public interface ParamsProvider<T> {

    List<Object> getInsertParams(T t);

    List<Object> getUpdateParams(T t);
}
