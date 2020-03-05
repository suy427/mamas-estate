package com.sondahum.mamas.controller;

import com.sondahum.mamas.common.model.PageRequest;
import com.sondahum.mamas.dto.EstateDto;
import com.sondahum.mamas.domain.estate.EstateInfoService;
import com.sondahum.mamas.domain.estate.EstateSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/estate")
@RequiredArgsConstructor
@Slf4j
public class EstateController {

    private final EstateInfoService estateInfoService;
    private final EstateSearchService estateSearchService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EstateDto.DetailResponse createEstate(@RequestBody @Valid EstateDto.CreateReq estateDto) {
        return estateInfoService.createEstateInfo(estateDto);
    }

    @GetMapping
    public Page<EstateDto.SearchResponse> searchEstates( // 이걸로 검색과 전체 유저 불러오기 가능
                                                         @RequestParam(name = "query", required = false) final EstateDto.SearchReq query,
                                                         final PageRequest pageRequest
    ) {
        return estateSearchService.search(query, pageRequest.of(query.getSortOrders())).map(EstateDto.SearchResponse::new);
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public EstateDto.DetailResponse getEstateDetail(@PathVariable final long id) {
        return estateInfoService.getEstateById(id);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public EstateDto.DetailResponse updateEstateInfo(@PathVariable final long id, @RequestBody final EstateDto.UpdateReq dto) {
        return estateInfoService.updateEstateInfo(id, dto);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public EstateDto.DetailResponse deleteContract(@PathVariable final long id) {
        return estateInfoService.deleteEstateInfo(id);
    }


}
