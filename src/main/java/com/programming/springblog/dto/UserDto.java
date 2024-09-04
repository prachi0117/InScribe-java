package com.programming.springblog.dto;

import lombok.Getter;
import lombok.Setter;


public class UserDto {
    private Integer id;
    private String username;

    // Constructor
    public UserDto() {
        this.id = id;
        this.username = username;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // Getters and Setters
    
}
