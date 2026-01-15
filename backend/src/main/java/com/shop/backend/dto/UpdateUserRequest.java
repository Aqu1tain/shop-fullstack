package com.shop.backend.dto;

import com.shop.backend.entity.Role;
import lombok.Data;

@Data
public class UpdateUserRequest {
    private String username;
    private String email;
    private Role role;
    private Boolean enabled;
}
