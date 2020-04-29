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
public class UserInfoDao {

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

        Optional<User> duplicatedUser = getDuplicatedUser(userDto.getName(), userDto.getPhone());

        if (duplicatedUser.isPresent())
            throw new UserAlreadyExistException(duplicatedUser.get());// TODO 이름이 같으면 A,B표시 등등 생각해보기

        return userRepository.save(userDto.toEntity());
    }

    public User getUserById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        optionalUser.orElseThrow(() -> new NoSuchEntityException(id));

        new Exception().printStackTrace();

        return optionalUser.get();
    }

    public User updateUserInfo(UserDto.UpdateReq dto) {
        Optional<User> optional = userRepository.findById(dto.getId());
        User user = optional.orElseThrow(() -> new NoSuchEntityException(dto.getId()));

        user.updateUserInfo(dto);// 예제에 보면 따로 repository에 변경된 entity를 save하지 않는다.

        return user;
    }

    public User deleteUserInfo(Long id) {
        Optional<User> optionalUser = userRepository.deleteByIdInQuery(id);

        return optionalUser.orElseThrow(() -> new NoSuchEntityException(id));
    }

    public Optional<User> getDuplicatedUser(String info) {
        if (Character.isDigit(info.charAt(0)))
            return getDuplicatedUser("", info);
        else
            return getDuplicatedUser(info, "");
    }

    @Transactional(readOnly = true)
    public Optional<User> getDuplicatedUser(String name, String phone) {
        Optional<User> optionalUser;

        if (name.isEmpty()) { // 폰번호만 입력한 경우
            optionalUser = userRepository.findByPhone_AndActive(phone, true);
        } else if (phone.isEmpty()) { // 이름만 입력한 경우
            optionalUser = userRepository.findByName_AndActive(name, true);
        } else { // 둘 다 입력한 경우
            optionalUser = userRepository.findByName_AndPhone_AndActive(name, phone, true);
        }
        return  optionalUser;
    }
}
