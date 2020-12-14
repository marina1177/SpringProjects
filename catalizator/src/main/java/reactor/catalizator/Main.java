package reactor.catalizator;

import java.time.Duration;
import java.util.Arrays;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class Main {

  public static void main(String[] args) throws InterruptedException {
    Mono.empty();
    Flux.empty();

    Mono<Integer> mono = Mono.just(1);
    Flux<Integer> flux = Flux.just(1, 2, 3);

    Flux<Integer> fluxFromMono = mono.flux();
    Mono<Boolean> monoFromFlux = flux.any(s -> s.equals(1));

    Mono<Integer> integerMono = flux.elementAt(1);

    Flux.range(1, 5);//.subscribe(System.out::println);
    Flux.fromIterable(Arrays.asList(1, 2, 3));//.subscribe(System.out::println);

    //===============================================
    //как генератор, нужен один консьюмер
    //будет бесконечно писать ы конссоль, однако если убрать sout, приложение отработает без блокировки
    Flux.<String>generate(sink -> {
      sink.next("hello");
    })
        .delayElements(Duration.ofMillis(
            500))//выполнение в отдельном потоке, когда основной поток доходит до конца, второстепенный тоже гибнет
        .take(4);//вместо цикла for со счетчиком
    //.subscribe(System.out::println);

    Flux<Object> producer = Flux.generate(
        () -> 2354,
        (state, sink) -> {
          if (state > 2366) {
            sink.complete(); //аналог .take(4) = последовательность становится конечной
          } else {
            sink.next("Step: " + state);
          }
          return state + 3;
        }
    );
    //  .subscribe(System.out::println);
    //==============================================================

    Flux // also .push(однопоточный)
        .create(sink ->{
            sink.next("DB returns: "+producer.blockFirst());
  })
        .subscribe(System.out::println); // многопоточный, если Flux генерится в разны потоках
    Thread.sleep(4000l);

  }

}
