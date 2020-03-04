package com.sondahum.mamas.domain.user;


import com.sondahum.mamas.common.error.exception.NoSuchEntityException;
import com.sondahum.mamas.common.error.exception.EntityAlreadyExistException;
import com.sondahum.mamas.domain.user.exception.NoSuchUserException;
import com.sondahum.mamas.domain.user.exception.UserAlreadyExistException;
import com.sondahum.mamas.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserInfoService {

    private final UserRepository userRepository;

    /**
     * 유저 생성의 두가지 유형
     * 1) estate를 함께 등록하는 경우
     * --> 'estate 추가' 버튼이 createNewUserInfo()를 호출
     * --> 버튼 누름에 따라 estate 추가 form이 나옴.
     * <p>
     * 2) estate를 등록하지 않고 User정보만 등록
     * --> '그냥 그대로 name, phone, role만 등록시킴'
     */
    public UserDto.DetailResponse createUserInfo(UserDto.CreateReq userDto) {
        if (isSamePersonExist(userDto))
            throw new UserAlreadyExistException(userDto.getName());// TODO 이름이 같으면 A,B표시 등등 생각해보기

        User user = userRepository.save(userDto.toEntity());

        return new UserDto.DetailResponse(user);
    }

    @Transactional(readOnly = true)
    public boolean isSamePersonExist(UserDto.CreateReq userDto) {
        Optional<User> optionalUser =
                userRepository.findByNameAndPhone_WholeNumber(userDto.getName(), userDto.getPhone().getWholeNumber());

        return optionalUser.isPresent();
    }

    public UserDto.DetailResponse getUserById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        optionalUser.orElseThrow(() -> new NoSuchUserException(id));

        return new UserDto.DetailResponse(optionalUser.get());
    }

    public UserDto.DetailResponse updateUserInfo(Long id, UserDto.UpdateReq userDto) {
        Optional<User> optional = userRepository.findById(id);
        User user = optional.orElseThrow(() -> new NoSuchUserException(id));

        user.updateUserInfo(userDto);// 예제에 보면 따로 repository에 변경된 entity를 save하지 않는다.

        return new UserDto.DetailResponse(user);
    }

    public UserDto.DetailResponse deleteUserInfo(Long id) {
        Optional<User> optional = userRepository.findById(id);
        User user = optional.orElseThrow(() -> new NoSuchUserException(id));

        userRepository.deleteById(id);

        return new UserDto.DetailResponse(user);
    }
}
