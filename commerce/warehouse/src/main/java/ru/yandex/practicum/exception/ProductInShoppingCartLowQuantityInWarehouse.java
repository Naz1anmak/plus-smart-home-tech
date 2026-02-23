package ru.yandex.practicum.exception;

import lombok.Getter;
import ru.yandex.practicum.dto.warehouse.response.ProductProblem;

import java.util.List;

@Getter
public class ProductInShoppingCartLowQuantityInWarehouse extends RuntimeException {
    private final List<ProductProblem> problems;

    public ProductInShoppingCartLowQuantityInWarehouse(List<ProductProblem> problems) {
        this.problems = problems;
    }
}
