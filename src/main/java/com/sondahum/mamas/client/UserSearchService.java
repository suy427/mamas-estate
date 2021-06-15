package com.sondahum.mamas.client;

import com.sondahum.mamas.client.adaptor.out.persistence.Client;
import com.sondahum.mamas.client.adaptor.out.persistence.ClientRepository;
import com.sondahum.mamas.dto.ClientDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
public class UserSearchService {
    private final ClientRepository clientRepository;

    public UserSearchService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<Client> specify(String query) {
        return null;
    }

    public Page<Client> search(final ClientDto.SearchReq query) {
        return null;
    }
}
