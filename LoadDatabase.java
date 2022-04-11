package com.example.restapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Configuration

public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);
    @Bean
    CommandLineRunner initDatabase(ProductRepo myProducts){
        return args -> {
            log.info("logging" +
                    myProducts.save(new Product("AirPods v3 2021","Headphones", 699.0,
                            "1)Smaller stems , " +
                                    "2)MagSafe Charging , " +
                                    "3)Case Adaptive EQ , " +
                                    "4)Up to 30 hours battery life")));

            log.info("logging" +
                    myProducts.save(new Product("iPhone 13","Cellular", 4000.0,
                            "1)20% smaller notch , " +
                                    "2)Improved camera sensors ISP , " +
                                    "3)A15 Bionic processor , " +
                                    "4)Released September 24")));

            log.info("logging" +
                    myProducts.save(new Product("MacBook pro 2021","laptops", 5500.0,
                            "1)the camera 720p FaceTime HD camera , "+
                                    "2)the color Silver/Space Gray , "+
                                    "3)the memory 8GB/16GB , "+
                                    "4)the storage 256GB/512GB")));

            log.info("logging" +
                    myProducts.save(new Product("Ipad pro 12.9 2021","tablet", 4500.0,
                            "1)the display 12.9 inches , " +
                                    "2)the camera 12 MP (Triple camera) 12 MP front , " +
                                    "3)the hardware Apple M1. 8GB RAM , " +
                                    "4)the storage 128GB, not expandable")));
        };
    }

    @Bean
    CommandLineRunner initDatabase2 (OrderRepo myOrders){
        return args -> {
            log.info("logging" +
                    myOrders.save
                            (new Order(LocalDate.now(),"Order 1",25.0, List.of())));
            log.info("logging" +
                    myOrders.save
                            (new Order(LocalDate.now(),"Order 2",40.0, List.of())));
            log.info("logging" +
                    myOrders.save
                            (new Order(LocalDate.now(),"Order 3",47.0, List.of())));
        };
    }

}
