package org.store.api;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Sign Up Request Model")
public class SignUpRequest {
    @Schema(description = "Username, must be unique", required = true, example = "Albert_80")
    private String username;

    @Schema(description = "User's e-mail, must be unique", required = true, example = "albert80@mail.com")
    private String email;

    @Schema(description = "User's password", required = true, example = "P@ssw0rd")
    private String password;

    @Schema(description = "User's password again, must match with password field", required = true, example = "P@ssw0rd")
    private String passwordConfirmation;

    public SignUpRequest() {
    }

    public SignUpRequest(String username, String email, String password, String passwordConfirmation) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.passwordConfirmation = passwordConfirmation;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }
}
