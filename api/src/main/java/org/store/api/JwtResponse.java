package org.store.api;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response for JWT Request")
public class JwtResponse {

    @Schema(description = "JSON Web Token", required = true, example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c")
    private String jwToken;

    public JwtResponse() {
    }

    public JwtResponse(String jwToken) {
        this.jwToken = jwToken;
    }

    public String getJwToken() {
        return jwToken;
    }

    public void setJwToken(String jwToken) {
        this.jwToken = jwToken;
    }
}
