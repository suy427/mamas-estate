package com.sondahum.mamas.user.dto

import com.sondahum.mamas.estate.domain.Status
import com.sondahum.mamas.user.domain.User
import com.sondahum.mamas.common.model.Role

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

    // pros : front --> back으로 넘어올 때 id정보를 쓸 수 있다
    // cons : 그냥 entity가 통째로 넘어오는거랑 뭐가 다르지..?
    static class UpdateReq {
        Long id // TODO dto에 id 정보를 넣어야할까 말아야할까..?
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
