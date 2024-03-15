package com.laytin.kafkaProjectMain.model;

import java.sql.Timestamp;

public class FileCounter {
    private int id;
    private String name;
    private int count;
    private Timestamp tm;

    public FileCounter() {
    }

    public FileCounter(String name, int count) {
        this.name = name;
        this.count = count;
    }

    public FileCounter(int id, String name, int count, Timestamp tm) {
        this.id = id;
        this.name = name;
        this.count = count;
        this.tm = tm;
    }

    public Timestamp getTm() {
        return tm;
    }

    public void setTm(Timestamp tm) {
        this.tm = tm;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
