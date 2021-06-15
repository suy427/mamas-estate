package com.sondahum.mamas.estate.adaptor.in;

import com.sondahum.mamas.estate.EstateInfoService;
import com.sondahum.mamas.dto.EstateDto;
import com.sondahum.mamas.estate.EstateSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/estates")
@RequiredArgsConstructor
@Slf4j
public class EstateController {

    private final EstateInfoService estateInfoService;
    private final EstateSearchService estateSearchService;


    @PostMapping(value = "/estate")
    public EstateDto.DetailForm createEstate(@RequestBody @Valid EstateDto.CreateReq estateDto) {
        return new EstateDto.DetailForm(estateInfoService.createEstateInfo(estateDto));
    }

    @GetMapping
    public Page<EstateDto.SimpleForm> searchEstates(@RequestParam("query") EstateDto.SearchReq query) {
        return estateSearchService.search(query).map(EstateDto.SimpleForm::new);
    }

    @GetMapping(value = "/estate")
    public List<EstateDto.SimpleForm> specifyEstate(@RequestParam(name = "query") String query) {
        return estateSearchService.specify(query).stream()
                .map(EstateDto.SimpleForm::new)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/user/{userId}")
    public List<EstateDto.SimpleForm> getUserEstateList(@PathVariable final long userId) {
        return estateInfoService.getUserEstateList(userId).stream()
                .map(EstateDto.SimpleForm::new)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/{id}")
    public EstateDto.DetailForm getEstateDetail(@PathVariable final long id) {
        return new EstateDto.DetailForm(estateInfoService.getEstateById(id));
    }

    @PutMapping(value = "/{id}")
    public EstateDto.DetailForm updateEstateInfo(@PathVariable final long id, @RequestBody final EstateDto.UpdateReq dto) {
        return new EstateDto.DetailForm(estateInfoService.updateEstateInfo(id, dto));
    }

    @PutMapping(value = "/delete/{id}")
    public EstateDto.SimpleForm deleteEstateSoft(@PathVariable final long id) {
        return new EstateDto.SimpleForm(estateInfoService.deleteEstateSoft(id));
    }

    @DeleteMapping(value = "/{id}")
    public void deleteEstateHard(@PathVariable final long id) {
        estateInfoService.deleteEstateHard(id);
    }

}
