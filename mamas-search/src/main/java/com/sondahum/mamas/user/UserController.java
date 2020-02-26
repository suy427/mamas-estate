package com.sondahum.mamas.user;

import com.sondahum.mamas.common.model.PageRequest;
import com.sondahum.mamas.user.domain.UserSearchFilter;
import com.sondahum.mamas.user.dto.UserDto;
import com.sondahum.mamas.user.service.UserInfoService;
import com.sondahum.mamas.user.service.UserSearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
class UserController {

    private static final Logger logger =  LoggerFactory.getLogger(UserController.class);
    private final UserInfoService userInfoService;
    private final UserSearchService userSearchService;


    UserController(UserInfoService userInfoService, UserSearchService userSearchService) {
        this.userInfoService = userInfoService;
        this.userSearchService = userSearchService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto.DetailResponse createUser(@RequestBody @Valid UserDto.CreateReq userDto) {
        return userInfoService.createUserInfo(userDto);
    }

    @GetMapping
    public Page<UserDto.SearchResponse> searchUsers( // 서치 결과는 목록형으로... 그걸 누르면 디테일 정보 확인하게끔
                                                     @RequestParam(name = "type") final UserSearchFilter filter,
                                                     @RequestParam(name = "value", required = false) final String value,
                                                     final PageRequest pageRequest
    ) {
        return userSearchService.search(filter, value, pageRequest.of()).map(UserDto.SearchResponse::new);
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public UserDto.DetailResponse getUserDetail(@PathVariable final long id) {
        return userInfoService.getUserById(id);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public UserDto.DetailResponse updateUserInfo(@PathVariable final long id, @RequestBody final UserDto.UpdateReq dto) {
        return userInfoService.updateUserInfo(id, dto);
    }

}
