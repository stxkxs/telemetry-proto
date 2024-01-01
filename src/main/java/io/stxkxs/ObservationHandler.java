package io.stxkxs;

import io.micrometer.observation.Observation;
import lombok.extern.log4j.Log4j2;
import org.springframework.lang.NonNull;

@Log4j2
public class ObservationHandler implements io.micrometer.observation.ObservationHandler<Observation.Context> {

  @Override
  public void onStart(Observation.Context context) {
    log.info("before running the observation for context [{}]", context.getName());
  }

  @Override
  public void onStop(Observation.Context context) {
    log.info("after running the observation for context [{}]", context.getName());
  }

  @Override
  public boolean supportsContext(@NonNull Observation.Context context) {
    return true;
  }
}
