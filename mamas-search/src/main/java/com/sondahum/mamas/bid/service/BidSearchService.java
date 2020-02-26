package com.sondahum.mamas.bid.service;

import com.sondahum.mamas.bid.domain.Bid;
import com.sondahum.mamas.user.domain.UserSearchFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BidSearchService {
    public Page<Bid> search(UserSearchFilter filter, String value, PageRequest of) {
        return null;
    }
}
