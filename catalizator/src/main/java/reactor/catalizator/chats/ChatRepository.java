package reactor.catalizator.chats;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ChatRepository {

//  Mono<Void> save(final ChatEntity entry);
//
//  Flux<ChatEntity> findAll();

  Mono<Void> deleteAll();

  Mono<Void> count();
}
