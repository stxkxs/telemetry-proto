package io.stxkxs;

import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.aop.ObservedAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Conf {
  @Bean
  public ObservationRegistry registry() {
    var registry = ObservationRegistry.create();
    var handler = new ObservationHandler();
    registry.observationConfig().observationHandler(handler);
    return registry;
  }

  @Bean
  public ObservedAspect observed(ObservationRegistry registry) {
    return new ObservedAspect(registry);
  }
}
