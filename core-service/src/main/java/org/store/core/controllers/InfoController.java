package org.store.core.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.store.api.UserInfoDto;
import org.store.core.exceptions.ResourceNotFoundException;
import org.store.core.services.UserService;
import org.store.core.entities.User;
import org.store.core.utils.ProfilingUtils;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class InfoController {
    private final UserService userService;
    private final ProfilingUtils profilingUtils;

    @GetMapping("/get_my_email")
    public UserInfoDto getEmail(Principal principal) {
        String username = principal.getName();
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("User %s does not exist", username)));
        return new UserInfoDto(user.getUsername(), user.getEmail());
    }

    @GetMapping("/services_info")
    public List<String> getServicesExecutionTime() {
        List<String> result = new ArrayList<>();
        profilingUtils.getServicesExecutionTime().forEach((name, time) -> result.add(String.format("%s: %d ms", name, time)));
        return result;
    }
}
