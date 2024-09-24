package org.store.core.converters;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.store.api.PageDto;

@Component
public class PageConverter {
    public <T> PageDto<T> entityToDto(Page<T> p) {
        return new PageDto<>(
                p.getContent(),
                p.getNumber(),
                p.getTotalPages(),
                p.getSize(),
                p.getTotalElements()
        );
    }
}
