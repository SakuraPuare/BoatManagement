package com.sakurapuare.boatmanagement.common;

import lombok.Data;

import java.util.List;

@Data
public class AuthInfo {
    public long userId;

    public String username;

    public List<String> roles;
    
    public List<String> permissions;
}
