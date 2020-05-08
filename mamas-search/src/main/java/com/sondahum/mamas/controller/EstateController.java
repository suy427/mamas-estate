package com.sondahum.mamas.controller;

import com.sondahum.mamas.common.model.PageRequest;
import com.sondahum.mamas.domain.bid.BidInfoService;
import com.sondahum.mamas.domain.estate.EstateInfoService;
import com.sondahum.mamas.dto.BidDto;
import com.sondahum.mamas.dto.EstateDto;
import com.sondahum.mamas.domain.estate.EstateSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;


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

    @PostMapping
    public EstateDto.SimpleForm setEstate(@RequestBody @Valid EstateDto.CreateReq estateDto) {
        return new EstateDto.SimpleForm(estateInfoService.createEstateInfo(estateDto));
    }

    @GetMapping
    public Page<EstateDto.SimpleForm> searchEstates(EstateDto.SearchReq query, final PageRequest pageRequest) {
        return estateSearchService.search(query, pageRequest.of(query.getSortOrders())).map(EstateDto.SimpleForm::new);
    }

    // bidding할 때, bidding할 땅 찾을때.. --> contract할 때도 똑같이 쓴다.
    // 없으면 클라이언트에서 등록 폼 주고 addNewEstate를 한다. --> 없는건 못한다.
    // 결과로 나온 땅을 골라서 누르면 bid 입력폼으로 간다. --> contract입력폼도 가능. --> 이건 둘 다 클라에서..
    // 얘는 그냥 estatecontroller에 가고 팝업을 estateController를 통해서 띄워야하나..?
    @GetMapping(value = "/estate")
    public List<EstateDto.SimpleForm> specifyEstate(@RequestParam(name = "query") String query) {
        return estateSearchService.specify(query).stream()
                .map(EstateDto.SimpleForm::new)
                .collect(Collectors.toList());
    }

    @GetMapping
    public List<EstateDto.SimpleForm> getUserEstateList(long id) {
        return estateInfoService.getUserEstateList(id).stream()
                .map(EstateDto.SimpleForm::new)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/{id}")
    public EstateDto.DetailForm getEstateDetail(@PathVariable final long id) {
        return new EstateDto.DetailForm(estateInfoService.getEstateById(id));
    }

    @PutMapping
    public EstateDto.DetailForm updateEstateInfo(@RequestBody final EstateDto.UpdateReq dto) {
        return new EstateDto.DetailForm(estateInfoService.updateEstateInfo(dto));
    }

    @PutMapping
    public EstateDto.SimpleForm updateEstate(EstateDto.UpdateReq estateDto) {
        return new EstateDto.SimpleForm(estateInfoService.updateEstateInfo(estateDto));
    }

    @PutMapping(value = "/{id}")
    public EstateDto.SimpleForm deleteEstateSoft(@PathVariable final long id) {
        return new EstateDto.SimpleForm(estateInfoService.deleteEstateSoft(id));
    }

    @DeleteMapping(value = "/{id}")
    public void deleteEstateHard(@PathVariable final long id) {
        estateInfoService.deleteEstateHard(id);
    }

}
