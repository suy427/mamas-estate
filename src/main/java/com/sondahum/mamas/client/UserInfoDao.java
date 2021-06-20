package com.sondahum.mamas.client;


import com.sondahum.mamas.client.adaptor.out.persistence.ClientRepository;
import com.sondahum.mamas.error.exception.NoSuchEntityException;
import com.sondahum.mamas.client.exception.NotEnoughInfoException;
import com.sondahum.mamas.client.exception.UserAlreadyExistException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserInfoDao {

    private final ClientRepository clientRepository;

    /**
     * 유저 생성의 두가지 유형
     * 1) estate를 함께 등록하는 경우
     * --> 'estate 추가' 버튼이 createNewUserInfo()를 호출
     * --> 버튼 누름에 따라 estate 추가 form이 나옴.
     * <p>
     * 2) estate를 등록하지 않고 User정보만 등록
     * --> '그냥 그대로 name, phone, role만 등록시킴'
     */
    public User createUserInfo(User user) {
        if (user.getName() == null && user.getPhone() == null)
            throw new NotEnoughInfoException(user.getName(), user.getPhone());

        Optional<User> duplicatedUser = getDuplicatedUser(user.getName(), user.getPhone());

        if (duplicatedUser.isPresent())
            throw new UserAlreadyExistException(duplicatedUser.get());// TODO 이름이 같으면 A,B표시 등등 생각해보기

        return clientRepository.save(user);
    }

    public User findUserByName(String name) {
        return clientRepository.findByName_AndActiveTrue(name)
                .orElseThrow(() -> new NoSuchEntityException(1L)); // todo exception 정의하기
    }

    public User getUserById(Long id) {
        Optional<User> optionalUser = clientRepository.findById(id);
        optionalUser.orElseThrow(() -> new NoSuchEntityException(id));

        new Exception().printStackTrace();

        return optionalUser.get();
    }

    public User updateUserInfo(long id, ClientDto.UpdateReq dto) {
        Optional<User> optional = clientRepository.findById(id);
        User user = optional.orElseThrow(() -> new NoSuchEntityException(id));

        return user.updateUserInfo(dto);// 예제에 보면 따로 repository에 변경된 entity를 save하지 않는다.
    }

    public User deleteUserSoft(Long id) {
        User user = clientRepository.findById(id)
                .orElseThrow(() -> new NoSuchEntityException(id));

        user.setActive(false);
        return user;
    }

    public void deleteUserHard(long id) {
        clientRepository.deleteById(id);
    }

    private Optional<User> getDuplicatedUser(String info) {
        if (Character.isDigit(info.charAt(0)))
            return getDuplicatedUser("", info);
        else
            return getDuplicatedUser(info, "");
    }

    private Optional<User> getDuplicatedUser(String name, String phone) {
        Optional<User> optionalUser;

        if (name == null) { // 폰번호만 입력한 경우
            optionalUser = clientRepository.findByPhone_AndActiveTrue(phone);
        } else if (phone == null) { // 이름만 입력한 경우
            optionalUser = clientRepository.findByName_AndActiveTrue(name);
        } else { // 둘 다 입력한 경우
            optionalUser = clientRepository.findByName_AndPhone_AndActiveTrue(name, phone);
        }
        return  optionalUser;
    }
}
