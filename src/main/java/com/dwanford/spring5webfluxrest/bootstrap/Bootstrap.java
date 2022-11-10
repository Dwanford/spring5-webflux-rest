package com.dwanford.spring5webfluxrest.bootstrap;

import com.dwanford.spring5webfluxrest.domain.Category;
import com.dwanford.spring5webfluxrest.domain.Vendor;
import com.dwanford.spring5webfluxrest.repositories.CategoryRepository;
import com.dwanford.spring5webfluxrest.repositories.VendorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {

    private final VendorRepository vendorRepository;
    private final CategoryRepository categoryRepository;

    public Bootstrap(VendorRepository vendorRepository, CategoryRepository categoryRepository) {
        this.vendorRepository = vendorRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        if(categoryRepository.count().block() == 0)
            loadCategories();
        if(vendorRepository.count().block() == 0)
            loadVendors();

    }

    private void loadCategories() {
        categoryRepository.save(Category.builder().description("Fruits").build()).block();
        categoryRepository.save(Category.builder().description("Dried").build()).block();
        categoryRepository.save(Category.builder().description("Fresh").build()).block();
        categoryRepository.save(Category.builder().description("Exotic").build()).block();
        categoryRepository.save(Category.builder().description("Nuts").build()).block();

        System.out.println("Category data loaded  = " + categoryRepository.count().block());
    }

    private void loadVendors() {
        vendorRepository.save(Vendor.builder().firstName("John").lastName("Doe").build()).block();
        vendorRepository.save(Vendor.builder().firstName("Patrick").lastName("Bateman").build()).block();
        vendorRepository.save(Vendor.builder().firstName("Travis").lastName("Bickle").build()).block();

        System.out.println("Vendors data loaded  = " + vendorRepository.count().block());
    }
}
