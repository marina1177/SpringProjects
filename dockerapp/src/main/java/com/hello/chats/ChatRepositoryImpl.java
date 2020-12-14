package com.hello.chats;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class ChatRepositoryImpl implements ChatRepository {

  private  List list = new ArrayList();

  @Override
  public Mono<Void> save(String string) {
    list.add(string);
    return Mono.empty();
  }

  @Override
  public  Mono<Integer> count() {

    Mono<Integer> mono = Mono.just(list.size());

//    mono.doOnNext(res -> chatMetrics.getChatGauge().set(res.intValue()));
        //.subscribe(res ->chatMetrics.getChatGauge().set(res.intValue()));
    return Mono.just(list.size());
  }
}
