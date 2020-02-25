package com.sondahum.mamas.user.service;

import com.sondahum.mamas.user.dao.UserRepository;
import com.sondahum.mamas.user.domain.User;
import com.sondahum.mamas.user.dto.UserDto;
import com.sondahum.mamas.user.exception.NoSuchUserException;
import com.sondahum.mamas.user.exception.UserAlreadyExistException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserInfoService {

    private final UserRepository userRepository;


    public UserInfoService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 유저 생성의 두가지 유형
     * 1) estate를 함께 등록하는 경우
     * --> 'estate 추가' 버튼이 createNewUserInfo()를 호출
     * --> 버튼 누름에 따라 estate 추가 form이 나옴.
     * <p>
     * 2) estate를 등록하지 않고 User정보만 등록
     * --> '그냥 그대로 name, phone, role만 등록시킴'
     */
    public UserDto.Response createNewUserInfo(UserDto.CreateReq userDto) {
        if (isSamePersonExist(userDto))
            throw new UserAlreadyExistException(userDto.getName());// TODO 이름이 같으면 A,B표시 등등 생각해보기

        User user = userRepository.save(userDto.toEntity());

        return new UserDto.Response(user);
    }

    @Transactional(readOnly = true)
    public boolean isSamePersonExist(UserDto.CreateReq userDto) {
        Optional<User> optionalUser = userRepository.findByName(userDto.getName());

        if (optionalUser.isPresent()) { // 같은 이름이 있으면
            User user = optionalUser.get();
            return userDto.getPhone().equals(user.getPhone()); // 핸드폰 번호까지 비교해서 같으면 duplicate
        }
        return false;
    }

    public UserDto.Response updateUserInfo(Long id, UserDto.UpdateReq userDto) {
        Optional<User> optional = userRepository.findById(id);
        User user = optional.orElseThrow(() -> new NoSuchUserException(id));

        user.updateUserInfo(userDto);// 예제에 보면 따로 repository에 변경된 entity를 save하지 않는다.
//        userRepository.save(user) // TODO 이유 알아내기~~

        return new UserDto.Response(user);
    }

    public UserDto.Response deleteUserInfo(Long id) {
        Optional<User> optional = userRepository.findById(id);
        User user = optional.orElseThrow(() -> new NoSuchUserException(id));

        userRepository.deleteById(id);

        return new UserDto.Response(user);
    }

    public UserDto.Response getUserById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        optionalUser.orElseThrow(() -> new NoSuchUserException(id));

        return new UserDto.Response(optionalUser.get());
    }
}
