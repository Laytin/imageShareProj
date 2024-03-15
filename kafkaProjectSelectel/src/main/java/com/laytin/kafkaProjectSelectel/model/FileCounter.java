package com.laytin.kafkaProjectSelectel.model;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "filecounter")
public class FileCounter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "count")
    private int count;
    @Column(name="tm")
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
