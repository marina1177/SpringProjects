package reactor.catalizator.chats;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

interface SQLChatEntryRepository extends ReactiveCrudRepository<SQLChatEntry, Long> {

  Mono<SQLChatEntry> findByChatID(final Long chatID);
}
