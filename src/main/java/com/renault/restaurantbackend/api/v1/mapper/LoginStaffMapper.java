package com.renault.restaurantbackend.api.v1.mapper;

import com.renault.restaurantbackend.api.v1.model.LoginStaffDTO;
import com.renault.restaurantbackend.domain.LoginStaff;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LoginStaffMapper {

  LoginStaffMapper INSTANCE = Mappers.getMapper(LoginStaffMapper.class);

  LoginStaffDTO LoginToLoginDTO(LoginStaff login);

}
