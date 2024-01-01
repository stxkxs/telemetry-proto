package io.stxkxs;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.observation.annotation.Observed;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@Log4j2
@RestController
@RequestMapping("/v1")
@AllArgsConstructor
public class TraceController {
  private final MeterRegistry meterRegistry;

  @GetMapping("/trace")
  @Observed(name = "trace.controller.execute",
      contextualName = "user.request.context",
      lowCardinalityKeyValues = {"type0", "type1"})
  public ResponseEntity<Object> trace() {
    log.info("received trace request");

    Gauge.builder("trace.controller.random.count", () -> new Random().nextInt())
        .tag("demo", "true")
        .description("demo prometheus gauge metric")
        .register(meterRegistry);

    return ResponseEntity.ok().build();
  }
}
