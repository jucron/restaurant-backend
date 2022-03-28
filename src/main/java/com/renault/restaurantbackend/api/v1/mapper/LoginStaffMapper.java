package com.renault.restaurantbackend.api.v1.mapper;

import com.renault.restaurantbackend.api.v1.model.CookDTO;
import com.renault.restaurantbackend.api.v1.model.LoginStaffDTO;
import com.renault.restaurantbackend.domain.Cook;
import com.renault.restaurantbackend.domain.LoginStaff;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LoginStaffMapper {

  LoginStaffMapper INSTANCE = Mappers.getMapper(LoginStaffMapper.class);

  @Mapping(source = "id", target = "id")
  LoginStaffDTO LoginToLoginDTO(LoginStaff login);

}
