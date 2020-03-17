package com.sondahum.mamas.domain.user;


import com.sondahum.mamas.common.error.exception.NoSuchEntityException;
import com.sondahum.mamas.domain.user.exception.NotEnoughInfoException;
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
    public User createUserInfo(UserDto.CreateReq userDto) {
        if (userDto.getName().isEmpty() && userDto.getPhone().isEmpty())
            throw new NotEnoughInfoException(userDto.getName(), userDto.getPhone());

        String existInfo = userExist(userDto);

        if (!existInfo.isEmpty())
            throw new UserAlreadyExistException(existInfo);// TODO 이름이 같으면 A,B표시 등등 생각해보기

        return userRepository.save(userDto.toEntity());
    }

    public User getUserById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        optionalUser.orElseThrow(() -> new NoSuchEntityException(id));

        return optionalUser.get();
    }

    public User updateUserInfo(Long id, UserDto.UpdateReq userDto) {
        Optional<User> optional = userRepository.findById(id);
        User user = optional.orElseThrow(() -> new NoSuchEntityException(id));

        user.updateUserInfo(userDto);// 예제에 보면 따로 repository에 변경된 entity를 save하지 않는다.

        return user;
    }

    public User deleteUserInfo(Long id) {
        Optional<User> optional = userRepository.findById(id);
        User user = optional.orElseThrow(() -> new NoSuchEntityException(id));

        userRepository.deleteById(id);

        return user;
    }

    @Transactional(readOnly = true)
    public String userExist(UserDto.CreateReq userDto) {
        Optional<User> optionalUser;

        if (userDto.getName().isEmpty()) { // 폰번호만 입력한 경우
            optionalUser = userRepository.findByPhone(userDto.getPhone());
            return optionalUser.map(user -> user.phone).orElse(null);
        } else { // 이름만 등록한 경우
            optionalUser = userRepository.findByName(userDto.getName());
            return optionalUser.map(user -> user.name).orElse(null);
        }
    }
}
