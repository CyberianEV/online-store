package org.store.core.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.store.core.utils.ProfilingUtils;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class InfoController {
    private final ProfilingUtils profilingUtils;

    @GetMapping("/services_info")
    public List<String> getServicesExecutionTime() {
        List<String> result = new ArrayList<>();
        profilingUtils.getServicesExecutionTime().forEach((name, time) -> result.add(String.format("%s: %d ms", name, time)));
        return result;
    }
}
