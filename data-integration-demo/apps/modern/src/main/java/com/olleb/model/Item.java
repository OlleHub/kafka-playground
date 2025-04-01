package com.olleb.model;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;


@MongoEntity(collection = "Items")
public class Item extends PanacheMongoEntity {

    public String name;
    public Double price;
    public Integer stock;

    public Item() {
    }

    public Item(String name, Double price, Integer stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

}
