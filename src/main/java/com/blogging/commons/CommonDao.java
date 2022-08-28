package com.blogging.commons;

import java.util.*;

public interface CommonDao {

    String saveOrUpdate(Object obj);

    boolean isExists(final String beanName,final String whereCondition);

    <T> T get(Class<T> t, Integer id);

    <T> List<T> getAllData(Class<T> T);

    <T> List<T> getDataByWhereCondition(Class<T> T, String whereCond);

    <T> List<T> getDataPagination(Class<T> T, int startIndex, int maxResult);

    <T> void updateColumns(Class<T> T, List<String[]> columns, String whereCondition);

    <T> void deleteData(Class<T> T, String whereCondition);

    <T> List<Object> getSingleColumnList(Class<T> T, String columnName, String whereCondition);

    List<HashMap<String, String>> getSpecificColumn(String beanName, List<String> columns, String whereCondition);
}
