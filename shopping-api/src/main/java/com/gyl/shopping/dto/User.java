package com.gyl.shopping.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
public class User implements Serializable {
    private Integer id;

    private String username;

    private String password;

    private String personalizedSignature;

    private Integer role;

    private Date createTime;

    private Date updateTime;

    private String emailAddress;

}