package com.sondahum.mamas.user

import com.sondahum.mamas.bid.dao.BidRepository
import com.sondahum.mamas.common.AbstractService
import com.sondahum.mamas.estate.dao.EstateRepository
import com.sondahum.mamas.user.dao.UserRepository
import com.sondahum.mamas.user.domain.User
import com.sondahum.mamas.user.dto.UserDto
import com.sondahum.mamas.user.exception.NoSuchUserException
import com.sondahum.mamas.user.exception.UserAlreadyExistException
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserInfoServiceImpl extends AbstractService implements UserInfoService {

    private final UserRepository userRepository
    private final BidRepository bidRepository
    private final EstateRepository estateRepository

    UserInfoServiceImpl(UserRepository userRepository, BidRepository bidRepository, EstateRepository estateRepository) {
        this.userRepository = userRepository
        this.bidRepository = bidRepository
        this.estateRepository = estateRepository
    }

    /**
     *
     * 유저 생성의 두가지 유형
     * 1) estate를 함께 등록하는 경우
     *  --> 'estate 추가' 버튼이 createNewUserInfo()를 호출
     *  --> 버튼 누름에 따라 estate 추가 form이 나옴.
     *
     * 2) estate를 등록하지 않고 User정보만 등록
     *  --> '그냥 그대로 name, phone, role만 등록시킴'
     */
    @Override
    UserDto.Response createNewUserInfo(UserDto.CreateReq userDto) {
        if (isExistingName(userDto.name))
            throw new UserAlreadyExistException() // TODO 이름 기반으로 중복 체크.. (이름, 번호) 이름이 같으면 A,B표시 등등 생각해보기

        User user = userRepository.save(userDto.toEntity())

        return new UserDto.Response(user)
    }

    @Transactional(readOnly = true)
    boolean isExistingName(String name) {
        return userRepository.findByName(name) != null
    }

    @Override
    UserDto.Response updateUserInfo(UserDto.UpdateReq userDto) {
        Optional<User> optional = userRepository.findById(userDto.id)
        User user = optional.orElseThrow({ -> new NoSuchUserException() })

        user.updateUserInfo(userDto) // 예제에 보면 따로 repository에 변경된 entity를 save하지 않는다.
//        userRepository.save(user)

        UserDto.Response response = new UserDto.Response(user)

        return response
    }

    @Override
    UserDto.Response deleteUserInfo(Long id) {
        Optional<User> optional = userRepository.findById(id)
        User user = optional.orElseThrow({ -> new NoSuchUserException() })

        userRepository.deleteById(id)

        return new UserDto.Response(user)
    }

    @Override
    Page<UserDto.Response> getUserInfoList() {
        return null
    }

    @Override
    Page<UserDto.Response> searchUser() {
        return null
    }
}
