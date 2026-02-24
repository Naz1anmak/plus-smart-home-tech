package ru.yandex.practicum.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.yandex.practicum.dto.shoppingCart.response.ShoppingCartDto;
import ru.yandex.practicum.model.ShoppingCart;

import java.util.Map;
import java.util.UUID;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface CartMapper {

    @Mapping(target = "shoppingCartId", source = "cart.id")
    ShoppingCartDto toShoppingCartDto(ShoppingCart cart, Map<UUID, Long> products);
}
