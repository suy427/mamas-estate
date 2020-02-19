package com.sondahum.mamas.user.dto

import com.sondahum.mamas.estate.domain.Status
import com.sondahum.mamas.user.domain.User
import com.sondahum.mamas.model.Role

import java.time.LocalDate

class UserDto {


    static class CreateReq {
        String name
        String phone
        String role

        User toEntity() {
            return new User(
                    name: name,
                    phone: phone,
                    role: Role.findByName(role)
            )
        }
    }


    // list상에서 일어나는 event들은 모두 id값을 얻어올 수 있다.
    // entity에서 객체의 값을 바꾸면 그게 table에도 반영이 된다...!!!!!!
    // 그러니까 얘는 entity로 바꾸고 자시고 할게 없네..!!!!
    // 무튼 한마디로 정리하면 그냥 바꾸고 싶은 필드만 있으면 된다.
    static class UpdateReq {
        String name
        String phone
        String role


    }


    static class Response {
        String name
        String phone
        Role role

        Integer owningEstateAmount
        Integer soldEstateAmount
        Integer buyingEstateAmount
        Integer boughtEstateAmount
        LocalDate recentContractDate

        Response(User user) {
            this.name = user.name
            this.phone = user.phone
            this.role = user.role
            this.owningEstateAmount = user.getSellingList().size()// 현재 파는거
            this.soldEstateAmount = user.soldList.size()
            this.buyingEstateAmount = user.getBuyingList().size()
            this.boughtEstateAmount = user.boughtList.size()
            this.recentContractDate = getRecentContractDate()

        }
    }
}
