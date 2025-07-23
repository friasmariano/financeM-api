package com.finance.manager.mappers;

import com.finance.manager.models.Pot;
import com.finance.manager.models.responses.PotResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PotMapper {
    PotResponse toResponse(Pot pot);
}
