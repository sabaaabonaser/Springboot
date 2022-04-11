package com.example.restapp;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OrderEntityFactory implements RepresentationModelAssembler<Order, EntityModel<Order>> {

    @Override
    public EntityModel<Order> toModel(Order order) {
        // TODO: להוציא מהערה כשיש את המתדות - singleOrder ו getAllOrders בקונטרולר
        return EntityModel.of(order,
                linkTo(methodOn(OrderController.class).singleOrder(order.getId())).withSelfRel(),
                linkTo(methodOn(OrderController.class).getAllOrders()).withRel("All orders"));
    }


    public EntityModel<?> toModel() {
        // TODO: להוציא מהערה כשיש את המתדות - singleOrder ו getAllOrders בקונטרולר
        return EntityModel.of(
                linkTo(methodOn(OrderController.class).getAllOrders()).withSelfRel(),
                linkTo(methodOn(OrderController.class).getAllOrders())
                        .withRel("The order has been deleted and this is a link to all orders"));
    }

    @Override
    public CollectionModel<EntityModel<Order>> toCollectionModel(Iterable<? extends Order> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities);
    }

    public EntityModel<Product> ForProductModelWithOrderLink(Order order, Product product){
        return EntityModel.of(product,
                linkTo(methodOn(ProductController.class).singleProduct(product.getId())).withSelfRel(),
                linkTo(methodOn(OrderController.class).singleOrder(order.getId())).withRel("Back to order"));
    }

}

