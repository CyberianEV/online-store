package org.store.auth.exceptions;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(description = "Application Error Model")
public class AppError {
    @Schema(description = "Code of error", example = "VALIDATION_ERROR")
    private String errorCode;

    @Schema(description = "Message description or details", example = "User with username Albert_80 already exists")
    private String message;
}
