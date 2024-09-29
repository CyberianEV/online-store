package org.store.api;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "JWToken request model")
public class JwtRequest {

    @Schema(description = "Username", required = true, example = "Albert_86")
    private String username;

    @Schema(description = "User password", required = true, example = "P@ssw0rd")
    private String password;

    public JwtRequest() {
    }

    public JwtRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
