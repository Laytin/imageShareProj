package com.laytin.kafkaProjectSelectel.model;

import jakarta.persistence.*;

@Entity
@Table(name = "filecounter")
public class ImageCounter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "count")
    private int count;

    public ImageCounter() {
    }

    public ImageCounter(String name, int count) {
        this.name = name;
        this.count = count;
    }

    public ImageCounter(int id, String name, int count) {
        this.id = id;
        this.name = name;
        this.count = count;
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
