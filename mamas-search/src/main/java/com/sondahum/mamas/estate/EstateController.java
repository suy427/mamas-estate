package com.sondahum.mamas.estate;

import com.sondahum.mamas.common.model.PageRequest;
import com.sondahum.mamas.estate.domain.EstateSearchFilter;
import com.sondahum.mamas.estate.dto.EstateDto;
import com.sondahum.mamas.estate.service.EstateInfoService;
import com.sondahum.mamas.estate.service.EstateSearchService;
import com.sondahum.mamas.user.domain.UserSearchFilter;
import com.sondahum.mamas.user.dto.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/estate")
public class EstateController {

    private static final Logger logger =  LoggerFactory.getLogger(EstateController.class);
    private final EstateInfoService estateInfoService;
    private final EstateSearchService estateSearchService;


    EstateController(EstateInfoService estateInfoService, EstateSearchService estateSearchService) {
        this.estateInfoService = estateInfoService;
        this.estateSearchService = estateSearchService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EstateDto.Response createNewUser(@RequestBody @Valid EstateDto.CreateReq estateDto) {
        return estateInfoService.createNewEstateInfo(estateDto);
    }

    @GetMapping
    public Page<EstateDto.Response> getUsers( // 이걸로 검색과 전체 유저 불러오기 가능
                                            @RequestParam(name = "type") final EstateSearchFilter filter,
                                            @RequestParam(name = "value", required = false) final String value,
                                            final PageRequest pageRequest
    ) {
        return estateSearchService.search(filter, value, pageRequest.of()).map(EstateDto.Response::new);
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public EstateDto.Response getUser(@PathVariable final long id) {
        return estateInfoService.getEstateById(id);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public EstateDto.Response updateMyAccount(@PathVariable final long id, @RequestBody final EstateDto.UpdateReq dto) {
        return estateInfoService.updateEstateInfo(id, dto);
    }


}
