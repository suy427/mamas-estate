package com.sondahum.mamas.domain.user;


import com.sondahum.mamas.domain.user.exception.UserAlreadyExistException;
import com.sondahum.mamas.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserInfoService {

    private final UserInfoDao userInfoDao;

    public User createUserInfo(UserDto.CreateReq userDto) { // 기본정보 생성 --> 있으면 할필요 없다.
        User user;
        try {
            user = userInfoDao.createUserInfo(userDto);
        } catch (UserAlreadyExistException e) {
            user = e.getUser();
        }
        return user;
    }

    public User getUserById(long id) {
        return userInfoDao.getUserById(id);
    }

    public User updateUserInfo(UserDto.UpdateReq dto) {
        return userInfoDao.updateUserInfo(dto);
    }

    // 지울때 연관관계까지 지우게되면 있던 정보들이 엉망이된다.
    // 그냥 active를 false로 하고 다른 곳에서 기록은 남되, 접근이 안되도록하자.
    public User deleteUserSoft(long id) {
        return userInfoDao.deleteUserSoft(id);
    }

    public void deleteUserHard(long id) {
        userInfoDao.deleteUserHard(id);
    }
}
