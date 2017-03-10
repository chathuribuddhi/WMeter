package com.chathuribuddhi.business;

import java.util.ArrayList;

/**
 * Created by CHATHURI on 2017-02-26.
 */
public interface IBO<T> {
    public boolean add(T t) throws Exception;
    public boolean update(T t) throws Exception;
    public boolean delete(T t) throws Exception;
    public T get(String id) throws Exception;
    public ArrayList<T> getAll() throws Exception;
}
