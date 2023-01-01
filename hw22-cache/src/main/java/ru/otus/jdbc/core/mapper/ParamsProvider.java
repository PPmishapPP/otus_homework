package ru.otus.jdbc.core.mapper;

import java.util.List;

public interface ParamsProvider<T> {

    List<Object> getInsertParams(T t);

    List<Object> getUpdateParams(T t);
}
