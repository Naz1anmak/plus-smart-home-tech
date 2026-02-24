package ru.yandex.practicum.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.dto.shoppingCart.response.ShoppingCartDto;
import ru.yandex.practicum.dto.warehouse.request.AddProductToWarehouseRequest;
import ru.yandex.practicum.dto.warehouse.request.NewProductInWarehouseRequest;
import ru.yandex.practicum.dto.warehouse.response.AddressDto;
import ru.yandex.practicum.dto.warehouse.response.BookedProductsDto;
import ru.yandex.practicum.dto.warehouse.response.ProductProblem;
import ru.yandex.practicum.exception.ProductInShoppingCartLowQuantityInWarehouse;
import ru.yandex.practicum.mapper.WarehouseProductMapper;
import ru.yandex.practicum.model.WarehouseProduct;
import ru.yandex.practicum.model.WarehouseStock;
import ru.yandex.practicum.repository.WarehouseRepository;
import ru.yandex.practicum.service.WarehouseService;
import ru.yandex.practicum.validation.WarehouseValidationService;

import java.security.SecureRandom;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class WarehouseServiceImpl implements WarehouseService {
    private static final String PRODUCT_NOT_FOUND_MESSAGE = "Продукт не найден в складе";
    private static final String LOW_QUANTITY_MESSAGE = "Недостаточно товара в складе";
    private static final String[] ADDRESSES = new String[]{"ADDRESS_1", "ADDRESS_2"};
    private static final String CURRENT_ADDRESS = ADDRESSES[Random.from(new SecureRandom()).nextInt(0, ADDRESSES.length)];

    private final WarehouseRepository warehouseRepository;
    private final WarehouseValidationService validationService;
    private final WarehouseProductMapper warehouseProductMapper;
    private final WarehouseReadService warehouseReadService;

    @Override
    @Transactional
    public void newProductInWarehouse(NewProductInWarehouseRequest request) {
        validationService.validateProductIdNotExist(request.productId());

        WarehouseProduct product = warehouseProductMapper.toEntity(request);

        log.info("Создан новый продукт с id {} для склада", product.getProductId());
        warehouseRepository.save(product);
    }

    @Override
    @Transactional
    public void addProductToWarehouse(AddProductToWarehouseRequest request) {
        WarehouseProduct product = warehouseReadService.getProductById(request.productId());

        WarehouseStock stock = product.getStock();
        stock.setAvailableQuantity(
                stock.getAvailableQuantity() + request.quantity()
        );
        log.info("Количество товара с id {} увеличено на {} единиц. Новое количество: {}",
                product.getProductId(), request.quantity(), stock.getAvailableQuantity());
    }

    @Override
    @Transactional
    public BookedProductsDto checkProductQuantityEnoughForShoppingCart(ShoppingCartDto shoppingCart) {
        Map<UUID, Long> requestedProducts = shoppingCart.products();

        List<WarehouseProduct> products = warehouseRepository.findAllWithStock(requestedProducts.keySet());

        List<ProductProblem> problems = collectProblems(
                requestedProducts,
                products
        );

        if (!problems.isEmpty()) {
            log.error("Проблемы с наличием товаров для корзины с id {}: {}", shoppingCart.shoppingCartId(), problems);
            throw new ProductInShoppingCartLowQuantityInWarehouse(problems);
        }

        log.info("Проверка наличия товаров для корзины прошла успешно. Все товары в наличии в достаточном количестве");
        return calculateBookingInfo(products, requestedProducts);
    }

    @Override
    @Transactional(readOnly = true)
    public AddressDto getWarehouseAddress() {
        log.info("Получение адреса склада");
        return new AddressDto(CURRENT_ADDRESS, CURRENT_ADDRESS, CURRENT_ADDRESS, CURRENT_ADDRESS, CURRENT_ADDRESS);
    }

    private List<ProductProblem> collectProblems(Map<UUID, Long> requested,
                                                 List<WarehouseProduct> foundProducts) {

        List<ProductProblem> problems = new ArrayList<>();

        Map<UUID, WarehouseProduct> productMap = foundProducts.stream()
                .collect(Collectors.toMap(
                        WarehouseProduct::getProductId,
                        Function.identity()
                ));

        for (Map.Entry<UUID, Long> entry : requested.entrySet()) {

            UUID productId = entry.getKey();
            Long requestedQty = entry.getValue();

            WarehouseProduct product = productMap.get(productId);

            if (product == null) {
                problems.add(new ProductProblem(
                        productId,
                        PRODUCT_NOT_FOUND_MESSAGE,
                        0L,
                        requestedQty
                ));
                continue;
            }

            Long availableQty = product.getStock().getAvailableQuantity();

            if (availableQty < requestedQty) {
                problems.add(new ProductProblem(
                        productId,
                        LOW_QUANTITY_MESSAGE,
                        availableQty,
                        requestedQty
                ));
            }
        }

        return problems;
    }

    private BookedProductsDto calculateBookingInfo(List<WarehouseProduct> products,
                                                   Map<UUID, Long> requested) {
        double totalWeight = 0.0;
        double totalVolume = 0.0;
        boolean fragilePresent = false;

        for (WarehouseProduct product : products) {

            Long quantity = requested.get(product.getProductId());

            totalWeight += product.getWeight() * quantity;

            double volume = product.getWidth() * product.getHeight() * product.getDepth();

            totalVolume += volume * quantity;

            if (Boolean.TRUE.equals(product.getFragile())) {
                fragilePresent = true;
            }
        }

        return new BookedProductsDto(totalWeight, totalVolume, fragilePresent);
    }
}
