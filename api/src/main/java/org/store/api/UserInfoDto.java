package org.store.api;

public class UserInfoDto {
    private String userInfo;

    public UserInfoDto() {
    }

    public UserInfoDto(String username, String email) {
        this.userInfo = username + " - " + email;
    }

    public String getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(String userInfo) {
        this.userInfo = userInfo;
    }
}
