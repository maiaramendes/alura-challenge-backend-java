package br.alura.controller;

import br.alura.dto.CategoryDTO;
import br.alura.dto.CategoryRequestDTO;
import br.alura.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryDTO> getAll() {
        return categoryService.getAll();
    }

    @GetMapping("/{id}")
    public CategoryDTO find(@PathVariable final String id) {
        return categoryService.findById(id);
    }

    @PostMapping
    public CategoryDTO create(@RequestBody final CategoryRequestDTO dto) {
        return categoryService.create(dto);
    }

    @PatchMapping
    public CategoryDTO edit(@RequestBody final CategoryRequestDTO dto) {
        return categoryService.edit(dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable final String id) {
        categoryService.delete(id);
    }


}
