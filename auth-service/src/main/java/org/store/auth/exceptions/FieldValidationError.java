package org.store.auth.exceptions;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class FieldValidationError {
    private List<String> errors;

    public FieldValidationError(List<String> errors) {
        this.errors = errors;
    }
}
