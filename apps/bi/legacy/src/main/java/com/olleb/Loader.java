package com.olleb;

import com.olleb.model.Item;

import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class Loader {

    void onStart(@Observes StartupEvent ev) {
        loadInitialData();
    }

    @Transactional
    void loadInitialData() {
        if (Item.count() == 0) {
            Item item1 = new Item();
            item1.name = "Large Smart TV";
            item1.price = 799.99;
            item1.persist();

            Item item2 = new Item();
            item2.name = "Laptop Pro";
            item2.price = 2399.99;
            item2.persist();

            Item item3 = new Item();
            item3.name = "Running Shoes";
            item3.price = 150.00;
            item3.persist();

            Item item4 = new Item();
            item4.name = "Smartphone Pro";
            item4.price = 1199.99;
            item4.persist();

            Item item5 = new Item();
            item5.name = "Stand Mixer";
            item5.price = 399.99;
            item5.persist();

            Item item6 = new Item();
            item6.name = "Noise-Canceling Headphones";
            item6.price = 299.99;
            item6.persist();

        }
    }
}
