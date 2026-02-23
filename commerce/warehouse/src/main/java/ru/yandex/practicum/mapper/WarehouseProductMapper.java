package ru.yandex.practicum.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.yandex.practicum.dto.warehouse.request.NewProductInWarehouseRequest;
import ru.yandex.practicum.model.WarehouseProduct;
import ru.yandex.practicum.model.WarehouseStock;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface WarehouseProductMapper {

    @Mapping(target = "width", source = "request.dimension.width")
    @Mapping(target = "height", source = "request.dimension.height")
    @Mapping(target = "depth", source = "request.dimension.depth")
    @Mapping(target = "stock", ignore = true)
    WarehouseProduct toEntity(NewProductInWarehouseRequest request);

    @AfterMapping
    default void afterMapping(@MappingTarget WarehouseProduct product) {

        WarehouseStock stock = new WarehouseStock();
        stock.setProduct(product);
        stock.setAvailableQuantity(0L);

        product.setStock(stock);
    }
}
