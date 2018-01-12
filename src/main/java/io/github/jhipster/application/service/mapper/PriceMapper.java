package io.github.jhipster.application.service.mapper;

import io.github.jhipster.application.domain.*;
import io.github.jhipster.application.service.dto.PriceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Price and its DTO PriceDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PriceMapper extends EntityMapper<PriceDTO, Price> {


    @Mapping(target = "products", ignore = true)
    Price toEntity(PriceDTO priceDTO);

    default Price fromId(Long id) {
        if (id == null) {
            return null;
        }
        Price price = new Price();
        price.setId(id);
        return price;
    }
}
