package com.midterm2021.quocanle.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

// Class này dùng để lưu các hành động đã thực hiện
@Entity
public class ActionExcute {
    @PrimaryKey (autoGenerate = true)
    private int id;
    @ColumnInfo
    private String input;
    @ColumnInfo
    private String actionDo;
    @ColumnInfo
    private String output;

    public ActionExcute(String input, String actionDo, String output) {
        this.input = input;
        this.actionDo = actionDo;
        this.output = output;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getActionDo() {
        return actionDo;
    }

    public void setActionDo(String actionDo) {
        this.actionDo = actionDo;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }
}
