package ru.yandex.practicum.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.dto.shoppingStore.enums.ProductCategory;
import ru.yandex.practicum.dto.shoppingStore.enums.ProductState;
import ru.yandex.practicum.dto.shoppingStore.request.CreateProductDto;
import ru.yandex.practicum.dto.shoppingStore.request.SetProductQuantityStateRequest;
import ru.yandex.practicum.dto.shoppingStore.request.UpdateProductDto;
import ru.yandex.practicum.dto.shoppingStore.response.PageProductDto;
import ru.yandex.practicum.dto.shoppingStore.response.ProductDto;
import ru.yandex.practicum.mapper.ProductMapper;
import ru.yandex.practicum.model.Product;
import ru.yandex.practicum.repository.StoreRepository;
import ru.yandex.practicum.service.StoreService;
import ru.yandex.practicum.util.PageValidator;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {
    private final StoreRepository storeRepository;
    private final ProductMapper productMapper;
    private final StoreReadService storeReadService;

    @Override
    @Transactional
    public ProductDto createNewProduct(CreateProductDto productDto) {
        Product product = productMapper.fromCreateDto(productDto);
        Product savedProduct = storeRepository.save(product);

        log.info("Создан новый продукт c ID: {}", savedProduct.getId());
        return productMapper.toDto(savedProduct);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDto getProductById(UUID productId) {
        Product product = storeReadService.getProductById(productId);

        log.info("Получен продукт с ID: {}", productId);
        return productMapper.toDto(product);
    }

    @Override
    @Transactional(readOnly = true)
    public PageProductDto<ProductDto> getProductsByCategory(ProductCategory category, Pageable pageable) {
        Page<Product> productPage = storeReadService.getProductsByCategory(category, pageable);
        PageValidator.validatePage(productPage);

        log.info("Получены продукты категории: {}, страница: {}", category, pageable.getPageNumber());
        return PageProductDto.from(productPage, productMapper::toDto);
    }

    @Override
    @Transactional
    public ProductDto updateProduct(UpdateProductDto productDto) {
        Product product = storeReadService.getProductById(productDto.productId());
        product = productMapper.updateEntityFromDto(productDto, product);
        product = storeRepository.save(product);

        log.info("Обновлен продукт с ID: {}", productDto.productId());
        return productMapper.toDto(product);
    }

    @Override
    @Transactional
    public boolean setProductQuantityState(SetProductQuantityStateRequest stateRequest) {
        Product product = storeReadService.getProductById(stateRequest.productId());
        product.setQuantityState(stateRequest.quantityState());
        storeRepository.save(product);

        log.info("Установлено состояние количества для продукта с ID: {} в {}", stateRequest.productId(), stateRequest.quantityState());
        return true;
    }

    @Override
    @Transactional
    public boolean removeProductFromStore(UUID productId) {
        Product product = storeReadService.getProductById(productId);
        product.setProductState(ProductState.DEACTIVATE);
        storeRepository.save(product);
        log.info("Продукт с ID: {} удален из ассортимента", productId);
        return true;
    }
}
