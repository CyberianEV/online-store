package org.store.core.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserInfoDto {
    private String userInfo;

    public UserInfoDto(String username, String email) {
        this.userInfo = username + " - " + email;
    }
}
