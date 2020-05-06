package com.sondahum.mamas.controller;

import com.sondahum.mamas.common.model.PageRequest;
import com.sondahum.mamas.domain.estate.EstateInfoService;
import com.sondahum.mamas.dto.EstateDto;
import com.sondahum.mamas.domain.estate.EstateSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
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
    public EstateDto.DetailForm createEstate(@RequestBody @Valid EstateDto.CreateReq estateDto) {
        return new EstateDto.DetailForm(estateInfoService.createEstateInfo(estateDto));
    }

    @GetMapping
    public Page<EstateDto.SimpleForm> searchEstates(@RequestParam(name = "query", required = false) final EstateDto.SearchReq query, final PageRequest pageRequest) {
        return estateSearchService.search(query, pageRequest.of(query.getSortOrders())).map(SimpleForm::new);
    }

    @GetMapping(value = "/{id}")
    public EstateDto.DetailForm getEstateDetail(@PathVariable final long id) {
        return new EstateDto.DetailForm(estateInfoService.getEstateById(id));
    }

    @PutMapping
    public EstateDto.DetailForm updateEstateInfo(@RequestBody final EstateDto.UpdateReq dto) {
        return new EstateDto.DetailForm(estateInfoService.updateEstateInfo(dto));
    }

    @DeleteMapping(value = "/{id}")
    public EstateDto.DetailForm deleteContract(@PathVariable final long id) {
        return new EstateDto.DetailForm(estateInfoService.deleteEstateInfo(id));
    }


}
