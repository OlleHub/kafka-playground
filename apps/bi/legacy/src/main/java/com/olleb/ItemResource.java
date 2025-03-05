package com.olleb;

import java.net.URI;
import java.util.List;

import com.olleb.model.Item;

import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("item")
public class ItemResource {

    @CheckedTemplate(requireTypeSafeExpressions = false)
    public static class Templates {
        public static native TemplateInstance item(Item item);

        public static native TemplateInstance itemList(List<Item> items);
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance getAllItems() {
        List<Item> items = Item.listAll();
        return Templates.itemList(items);
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance get(@PathParam("id") Long id) {
        Item item = Item.findById(id);
        if (item == null) {
            throw new WebApplicationException("Item not found", 404);
        }
        return Templates.item(item);
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Transactional
    public Response createItem(@FormParam("name") String name, @FormParam("price") Double price) {
        if (name == null || price == null) {
            throw new WebApplicationException("Invalid input", 400);
        }

        Item item = new Item(name, price);
        item.persist();
        return Response.seeOther(URI.create("/item")).build();
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.TEXT_HTML)
    @Transactional
    public Response deleteItem(@PathParam("id") Long id) {
        Item item = Item.findById(id);
        if (item == null) {
            throw new WebApplicationException("Item not found", 404);
        }
        item.delete();
        return Response.seeOther(URI.create("/item")).build();
    }
}
