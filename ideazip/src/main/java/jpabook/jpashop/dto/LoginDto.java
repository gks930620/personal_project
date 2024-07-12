package jpabook.jpashop.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;


@Getter
public class LoginDto {


    @QueryProjection
    public LoginDto(String id, String password
            , String name,String role) {
        this.id=id;
        this.password=password;
        this.name = name;
        this.role=role;
    }
    private String id;
    private String password;
    private String name;   //memberName
    private String role;






}
