package org.store.api;

public class JwtResponse {
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
