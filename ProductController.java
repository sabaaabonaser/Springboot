package com.example.restapp;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


import java.util.List;
import java.util.stream.Collectors;

// Indicates that the data from our methods will be injected to the response payload (body)

@RestController
public class ProductController {
    private ProductRepo productDatabase;
    private ProductEntityFactory productEntityFactory;

    public ProductController(ProductRepo productDatabase,ProductEntityFactory productEntityFactory){
        this.productDatabase = productDatabase;
        this.productEntityFactory = productEntityFactory;
    }


    // מטודה מספר 1
    @GetMapping("/products")
    public ResponseEntity<CollectionModel<EntityModel<Product>>> getAllProducts(){
        List<EntityModel<Product>> products = productDatabase.findAll().stream()
                .map(product -> productEntityFactory.toModel(product))
                .collect(Collectors.toList());
        return ResponseEntity
                .ok(CollectionModel.of(products));
    }


    // מטודה מספר 2
    @GetMapping("products/{id}")
    public ResponseEntity<EntityModel<Product>> singleProduct(@PathVariable Long id){
        Product product = productDatabase.findById(id)
                .orElseThrow(()->new ProductNotFoundException(id));
        return ResponseEntity.ok(productEntityFactory.toModel(product));
    }

    /*
    ResponseEntity ->
    הוא מיכל מיוחד עבור Spring MVC המאפשר הוספה של קודי תגובה + נתונים
    */
    @PostMapping("/products")
    ResponseEntity<?> createProduct(@RequestBody Product newProduct){
        EntityModel<Product> productRepresentation
                = productEntityFactory.toModel(productDatabase.save(newProduct));
        return ResponseEntity
                .created(productRepresentation.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(productRepresentation);
    }


    @PutMapping("/products/{id}")
    ResponseEntity<?> replaceProduct(@RequestBody Product aProduct, @PathVariable Long id){
        Product updatedProduct =  productDatabase.findById(id).map(productToUpdate->{
                    productToUpdate.setProductName(aProduct.getProductName());
                    productToUpdate.setCategory((aProduct.getCategory()));
                    productToUpdate.setPrice(aProduct.getPrice());
                    return productDatabase.save(productToUpdate);

                })
                .orElseGet(()->{
                    aProduct.setId(id);
                    return productDatabase.save(aProduct);
                });
        EntityModel<Product> productRepresentation =
                productEntityFactory.toModel(productDatabase.save(updatedProduct));
        return ResponseEntity
                .created(productRepresentation.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(productRepresentation);
    }


    @DeleteMapping("/products/{id}")
    ResponseEntity<?> deleteProduct( @PathVariable(value = "id") Long productID) {
        // מוחק המוצר ה id שהוא בתוך products מתוך -> productDatabase
        productDatabase.deleteById(productID);
        // מחזיר בסוף דרך ResponseEntity משפט וגם מוסיף סטאטוס 200
        return ResponseEntity.ok(productEntityFactory.toModel());
    }

//    @DeleteMapping("/products/{id}")
//    void deleteProduct(@PathVariable Long id){
//        productDatabase.deleteById(id);
//    }


    // home route
//    @GetMapping("/products")
//    List<Product> getAllProducts(){
//        return productDatabase.findAll();
//    }

    /*
    אנחנו יודעים ש-findAll() מחזירה לנו רשימה של products
    אנחנו רוצים שכל product יהיה עטוף בקונטיינר
    ניקח כל איבר ברשימה של products ונהפוך אותו לקונטיינר + לינקים (לפרודקט עצמו ולכל הפרודקטים)
    אם זה המצב - נקבל רשימה של קונטיינרים
    אבל רשימה זה טיפוס קונקרטי - אנו רוצים להחזיר ייצוג של משאב
    ייצוג של collection נקרא CollectionModel
    וגם לייצוג נוסיף קישור - לאותו קולשקשן עצמו
     */
//    @GetMapping("/products")
//    CollectionModel<EntityModel<Product>> getAllProducts(){
//        List<EntityModel<Product>> products = productDatabase.findAll()
//                .stream().map(product -> EntityModel.of(product,
//                        linkTo(methodOn(ProductController.class).singleProduct(product.getId()))
//                                .withSelfRel(),
//                        linkTo(methodOn(ProductController.class).getAllProducts())
//                                .withRel("All products"))).collect(Collectors.toList());
//        //container of container of product
//        return CollectionModel.of(products,
//                linkTo(methodOn(ProductController.class).getAllProducts()).withSelfRel());
//    }


//    @GetMapping("/products")
//    CollectionModel<EntityModel<Product>> getAllProducts(){
//        List<EntityModel<Product>> products = productDatabase.findAll()
//                .stream().map(productEntityFactory::toModel).collect(Collectors.toList());
////                .stream().map(product -> productFactory.toModel(product)).collect(Collectors.toList());
//        return CollectionModel.of(products,
//                linkTo(methodOn(ProductController.class).getAllProducts()).withSelfRel());
//    }


//    @PostMapping("/products")
//    Product createProduct(@RequestBody Product aProduct){
//        return productDatabase.save(aProduct);
//    }


//    @GetMapping("/products/{id}")
//    Product singleProduct(@PathVariable Long id){
//        return productDatabase.findById(id)
//                .orElseThrow(()->new ProductNotFoundException(id));
//    }


//    @GetMapping("/products/{id}")
//    EntityModel<Product> singleProduct(@PathVariable Long id){
//         Product product = productDatabase.findById(id)
//                 .orElseThrow(()->new ProductNotFoundException(id));
//         return EntityModel.of(product,
//                 linkTo(methodOn(ProductController.class).singleProduct(id))
//                         .withSelfRel(),
//                 linkTo(methodOn(ProductController.class).getAllProducts())
//                         .withRel("Back to all products"));
//    }
//
//    @GetMapping("/products/{id}")
//    EntityModel<Product> singleProduct(@PathVariable Long id) {
//        Product product = productDatabase.findById(id)
//                .orElseThrow(() -> new ProductNotFoundException(id));
//        return productEntityFactory.toModel(product);
//    }


//    @PutMapping("/products/{id}")
//    Product updateProduct(@PathVariable Long id,@RequestBody Product aProduct){
//        return productDatabase.findById(id).map(productToUpdate->{
//            productToUpdate.setPrice(aProduct.getPrice());
//            productToUpdate.setCategory(aProduct.getCategory());
//            productToUpdate.setProductName(aProduct.getProductName());
//            return productDatabase.save(productToUpdate);
//        })
//                .orElseGet(()->{
//                    createProduct(aProduct).setId(id);
//                    return productDatabase.save(aProduct);
//                });
//    }

}


