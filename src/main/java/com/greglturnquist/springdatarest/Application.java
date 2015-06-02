package com.greglturnquist.springdatarest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.greglturnquist.springdatarest.jpa.Book;
import com.greglturnquist.springdatarest.mongodb.Album;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = Book.class)
@EnableMongoRepositories(basePackageClasses = Album.class)
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
