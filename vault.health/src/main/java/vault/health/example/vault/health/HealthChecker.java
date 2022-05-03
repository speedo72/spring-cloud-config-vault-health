package vault.health.example.vault.health;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class HealthChecker {
    Logger logger = LoggerFactory.getLogger(HealthChecker.class.getSimpleName());
    ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    List<HealthIndicator> indicators;

    public HealthChecker(List<HealthIndicator> indicators) {
        this.indicators = indicators;
        executor.scheduleAtFixedRate(runnable, 10000, 30000, TimeUnit.MILLISECONDS);
    }

    private final Runnable runnable = () -> {
        logger.info("Checking health for size {}", indicators.size());
        for (HealthIndicator indicator : indicators) {
            logger.info("Checking {}", indicator.getClass().getSimpleName());
            if (Status.DOWN.equals(indicator.health().getStatus())) {
                logger.error("Bad");
            } else {
                logger.info("Good");
            }
        }
    };
}
