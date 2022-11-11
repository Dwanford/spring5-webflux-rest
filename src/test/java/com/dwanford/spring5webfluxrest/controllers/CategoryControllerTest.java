package com.dwanford.spring5webfluxrest.controllers;

import com.dwanford.spring5webfluxrest.domain.Category;
import com.dwanford.spring5webfluxrest.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;

class CategoryControllerTest {

    WebTestClient webTestClient;
    CategoryRepository categoryRepository;
    CategoryController categoryController;

    @BeforeEach
    void setUp() {
        categoryRepository = Mockito.mock(CategoryRepository.class);
        categoryController = new CategoryController(categoryRepository);
        webTestClient = WebTestClient.bindToController(categoryController).build();
    }

    @Test
    void list() {
        BDDMockito.given(categoryRepository.findAll())
                .willReturn(Flux.just(Category.builder().description("Foo").build(),
                                    Category.builder().description("Bar").build()));

        webTestClient.get()
                .uri("/api/v1/categories/")
                .exchange()
                .expectBodyList(Category.class)
                .hasSize(2);
    }

    @Test
    void getById() {
        BDDMockito.given(categoryRepository.findById("someId"))
                .willReturn(Mono.just(Category.builder().description("Foo").build()));

        webTestClient.get()
                .uri("/api/v1/categories/someId")
                .exchange()
                .expectBody(Category.class);
    }

    @Test
    void create() {
        BDDMockito.given(categoryRepository.saveAll(any(Publisher.class)))
                .willReturn(Flux.just(Category.builder().description("Foo").build()));

        Mono<Category> categoryToSave = Mono.just(Category.builder().description("Boo").build());

        webTestClient.post()
                .uri("/api/v1/categories")
                .body(categoryToSave, Category.class)
                .exchange()
                .expectStatus()
                .isCreated();
    }

    @Test
    void update() {
        BDDMockito.given(categoryRepository.save(any(Category.class)))
                .willReturn(Mono.just(Category.builder().description("Foo").build()));

        Mono<Category> categoryToUpdate = Mono.just(Category.builder().description("Boo").build());

        webTestClient.put()
                .uri("/api/v1/categories/someid")
                .body(categoryToUpdate, Category.class)
                .exchange()
                .expectStatus()
                .isOk();

    }
}