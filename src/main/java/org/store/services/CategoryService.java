package org.store.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.store.entities.Category;
import org.store.repositories.CategoryRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Optional<Category> findByTitle(String title) {
        return categoryRepository.findByTitle(title);
    }
}
