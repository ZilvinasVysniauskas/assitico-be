package com.assistico.planner.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapperTest {
    UserMapperTest INSTANCE = Mappers.getMapper(UserMapperTest.class);

    @Mapping(source = "user.someNewField", target = "mappedNewFiled")
    UserD
}
