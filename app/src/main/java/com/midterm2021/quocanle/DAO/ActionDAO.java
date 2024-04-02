package com.midterm2021.quocanle.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.midterm2021.quocanle.model.ActionExcute;

import java.util.List;

@Dao
public interface ActionDAO {
    @Query("SELECT * FROM actionexcute")
    List<ActionExcute> getAll();

    @Query("SELECT * FROM actionexcute WHERE id IN (:actionIds)")
    List<ActionExcute> loadAllByIds(int[] actionIds);

    @Insert
    void insertAll(ActionExcute... actionexcutes);


    @Delete
    void delete(ActionExcute actionexcutes);

    @Delete
    void deleteAll(List<ActionExcute> actionexcutes);
}