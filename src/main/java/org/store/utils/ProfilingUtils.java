package org.store.utils;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Data
@NoArgsConstructor
public class ProfilingUtils {
    private Map<String, Long> servicesExecutionTime;

    public void addExecutionTime(String serviceName, Long exTime) {
        servicesExecutionTime.merge(serviceName, exTime, Long::sum);
    }

    @PostConstruct
    public void init() {
        servicesExecutionTime = new ConcurrentHashMap<>();
    }
}
