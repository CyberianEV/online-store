package org.store.core.integrations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.store.api.CartDto;

@Component
@RequiredArgsConstructor
public class CartServiceIntegration {
    private final RestTemplate restTemplate;

    public CartDto getCurrentCart() {
        return restTemplate.getForObject("http://localhost:8191/store/api/v1/cart", CartDto.class);
    }

    public void clearCart() {
        restTemplate.getForObject("http://localhost:8191/store/api/v1/cart/clear", Object.class);
    }
}
