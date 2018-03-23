package com.alissonrubim.fifaexpress.Model.DAO;

import com.alissonrubim.fifaexpress.Model.Database;

import java.util.ArrayList;

/**
 * Created by alissonrubim on 23/03/2018.
 */

public interface BaseDAO<T> {
    public ArrayList<T> GetAll();
    public T GetById(int id);
    public long Insert(T obj);
    public void Update(T obj);
    public void Delete(T obj);

}
