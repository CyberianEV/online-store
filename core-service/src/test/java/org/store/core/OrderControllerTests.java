package org.store.core;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.store.core.services.OrderService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTests {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private OrderService orderService;

    @Test
    public void createOrderWithoutUsernameHeaderTest() throws Exception {
        mvc
                .perform(
                        post("/api/v1/orders")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createOrderWithUsernameHeaderTest() throws Exception {
        mvc
                .perform(
                        post("/api/v1/orders")
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("username", "User")
                )
                .andExpect(status().isCreated());
    }
}
