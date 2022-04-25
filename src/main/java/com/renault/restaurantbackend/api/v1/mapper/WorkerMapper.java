package com.renault.restaurantbackend.api.v1.mapper;

import com.renault.restaurantbackend.api.v1.model.WorkerDTO;
import com.renault.restaurantbackend.domain.Worker;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface WorkerMapper {

  WorkerMapper INSTANCE = Mappers.getMapper(WorkerMapper.class);

  @Mapping(source = "login", target = "loginDTO")
  WorkerDTO workerToWorkerDTO(Worker worker);
}
