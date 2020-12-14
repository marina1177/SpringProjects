package com.hello.chats;

import java.util.List;
import reactor.core.publisher.Mono;

public interface ChatRepository {

  Mono<Void> save(String string);
  Mono<Integer> count();

}
