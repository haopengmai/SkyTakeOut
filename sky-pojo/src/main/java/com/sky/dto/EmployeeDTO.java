package com.sky.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class EmployeeDTO implements Serializable {

    private Integer id;

    private String username;

    private String name;

    private String phone;

    private String sex;

    private String idNumber;

}
