package com.jh.loginappbackend.mapper;

import com.jh.loginappbackend.dto.RegisterUserDto;
import com.jh.loginappbackend.dto.UserDto;
import com.jh.loginappbackend.model.AppUser;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = StrictMapperConfig.class)
public interface UserMapper {

  @BeanMapping(ignoreUnmappedSourceProperties = "password")
  @Mapping(target = "id", ignore = true)
  AppUser fromDto(RegisterUserDto registerUserDto);

  @BeanMapping(ignoreUnmappedSourceProperties = {
      "id", "password"
  })
  @Mapping(target = "id", ignore = true)
  @Mapping(source = "email", target = "username")
  UserDto toDto(AppUser user);

}
