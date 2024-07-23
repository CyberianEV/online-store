package org.store.core.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.store.core.services.OrderService;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@CrossOrigin("*")
public class OrderController {
    private final OrderService orderService;

//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public void createNewOrder (Principal principal) {
//        orderService.createNewOrder(principal.getName());
//    }
}
