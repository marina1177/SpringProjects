package reactor.catalizator.chats;

import io.micrometer.core.instrument.MeterRegistry;
import java.util.concurrent.atomic.AtomicLong;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@RequiredArgsConstructor
public class ChatMetrics {

  private final AtomicLong gaugeUploadRate;

  static ChatMetrics init(final MeterRegistry meterRegistry) {
    return new ChatMetrics(meterRegistry.gauge("search.chat.gauge.upload.cards", new AtomicLong(0)));
  }
}

