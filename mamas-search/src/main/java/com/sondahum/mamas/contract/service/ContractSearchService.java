package com.sondahum.mamas.contract.service;

import com.sondahum.mamas.contract.domain.Contract;
import com.sondahum.mamas.user.domain.UserSearchFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ContractSearchService {
    public Page<Contract> search(UserSearchFilter filter, String value, PageRequest of) {
        return null;
    }
}
