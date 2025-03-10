package com.olleb.rest;

import java.util.List;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.jboss.resteasy.reactive.RestStreamElementType;

import com.olleb.model.Product;

import io.smallrye.mutiny.Multi;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;

@Path("/inventory")
public class InventoryResource {

    @Inject
    @Channel("inventory")
    Multi<List<Product>> inventory;

    @Inject
    @Channel("checkout")
    Multi<List<Product>> updates;

    @GET
    @RestStreamElementType(MediaType.APPLICATION_JSON)
    public Multi<List<Product>> getInventory() {
        return inventory;
    }

    @GET
    @Path("/updates")
    @RestStreamElementType(MediaType.APPLICATION_JSON)
    public Multi<List<Product>> getUpdates() {
        return updates;
    }
}
