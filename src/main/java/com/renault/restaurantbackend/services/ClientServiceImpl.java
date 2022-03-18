package com.renault.restaurantbackend.services;

import com.renault.restaurantbackend.api.v1.mapper.ClientMapper;
import com.renault.restaurantbackend.api.v1.model.ClientDTO;
import com.renault.restaurantbackend.api.v1.model.ClientListDTO;
import com.renault.restaurantbackend.domain.Client;
import com.renault.restaurantbackend.repositories.ClientRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
  private final ClientMapper clientMapper;
  private final ClientRepository clientRepository;

  @Override
  public ClientListDTO getAllClients() {
    List<Client> clientList = clientRepository.findAll();
    ClientListDTO clientListDTO = new ClientListDTO(new ArrayList<>());
    for (Client client : clientList) {
      clientListDTO.getClients().add(clientMapper.clientToClientDTO(client));
    }
    return clientListDTO;
  }

  @Override public ClientDTO createClient(String name) {
    Client newClient = new Client();
    newClient.setName(name); newClient.setCheckInTime(LocalDateTime.now());
    clientRepository.save(newClient);
    return clientMapper.clientToClientDTO(newClient);
  }
}
