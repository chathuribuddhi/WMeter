package com.chathuribuddhi.dao;

import java.sql.Connection;
import java.util.ArrayList;

/**
 * Created by CHATHURI on 2017-02-26.
 */
public interface IDAO<T> {
    public void setConnection(Connection connection)throws Exception;
    public boolean add(T t)throws Exception;
    public boolean update(T t)throws Exception;
    public boolean delete(String id) throws Exception;
    public T get(String id) throws Exception;
    public ArrayList<T> getAll() throws Exception;
}
