package com.example.restapp;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Component
public class ProductEntityFactory implements RepresentationModelAssembler<Product, EntityModel<Product>> {
    @Override
    public EntityModel<Product> toModel(Product product) {
        // TODO: להוציא מהערה כשיש את המתדות - singleOrder ו getAllOrders בקונטרולר
        return EntityModel.of(product,
                linkTo(methodOn(ProductController.class).singleProduct(product.getId())).withSelfRel(),
                linkTo(methodOn(ProductController.class).getAllProducts()).withRel("Back to products"));
    }

    public EntityModel<?> toModel() {
        // TODO: להוציא מהערה כשיש את המתדות - singleOrder ו getAllOrders בקונטרולר
        return EntityModel.of(
                linkTo(methodOn(ProductController.class).getAllProducts()).withSelfRel(),
                linkTo(methodOn(ProductController.class).getAllProducts())
                        .withRel("The product has been deleted and this is a link to all products"));
    }


}
