package com.olleb.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

@Entity
public class Item extends PanacheEntity {

    public String name;
    public Double price;

    public Item() {
    }

    public Item(String name, Double price) {
        this.name = name;
        this.price = price;
    }

}
