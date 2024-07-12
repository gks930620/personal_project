package jpabook.jpashop.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class MemberDto{
    private String id;
    private String password;
    private String username;
    private String email;
    private String hp; //전화번호
    private String birthday; //생일


}