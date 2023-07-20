package com.voronov.market.mapper;

import com.voronov.market.dto.UserDto;
import com.voronov.market.model.UserEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

/**
 * @author Alexey Voronov on 19.07.2023
 */
@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto map(UserEntity userEntity);

    @InheritInverseConfiguration
    UserEntity map(UserDto dto);
}
