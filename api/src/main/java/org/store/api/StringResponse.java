package org.store.api;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Multipurpose response containing simple string")
public class StringResponse {
    @Schema(description = "String content", required = true, example = "Some useful information")
    private String value;

    public StringResponse() {
    }

    public StringResponse(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
