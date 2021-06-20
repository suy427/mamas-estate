package com.sondahum.mamas.client.adaptor.in;

import com.sondahum.mamas.client.ClientDto;
import com.sondahum.mamas.client.UserInfoService;
import com.sondahum.mamas.client.UserSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
class UserController {
    private final UserInfoService userInfoService;
    private final UserSearchService userSearchService;


    @PostMapping(value = "/user")
    public ClientDto.DetailForm createUser(@RequestBody @Valid ClientDto.CreateReq userDto) {
        return new ClientDto.DetailForm(userInfoService.createUserInfo(userDto));
    }

    @GetMapping(value = "/specify")
    public List<ClientDto.SimpleForm> specifyUser(@RequestParam(value = "query") final String query) {
        return userSearchService.specify(query).stream()
                .map(ClientDto.SimpleForm::new)
                .collect(Collectors.toList());
    }

    @GetMapping
    public Page<ClientDto.SimpleForm> searchUsers(ClientDto.SearchReq query) {
        return userSearchService.search(query).map(ClientDto.SimpleForm::new);
    }

    @GetMapping(value = "/{id}")
    public ClientDto.DetailForm getUserDetail(@PathVariable final long id) {
        return new ClientDto.DetailForm(userInfoService.getUserById(id));
    }

    @PutMapping(value = "/{id}")
    public ClientDto.DetailForm updateUserInfo(@PathVariable final long id, @RequestBody final ClientDto.UpdateReq dto) {
        return new ClientDto.DetailForm(userInfoService.updateUserInfo(id, dto));
    }

    @PutMapping(value = "/delete/{id}")
    public ClientDto.DetailForm deleteUserSoft(@PathVariable final long id) {
        return new ClientDto.DetailForm(userInfoService.deleteUserSoft(id));
    }

    @DeleteMapping(value = "/{id}")
    public void deleteUserHard(@PathVariable final long id) {
        userInfoService.deleteUserHard(id);
    }

}