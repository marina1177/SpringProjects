package reactor.catalizator.chats;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
final class SQLChatRepository implements ChatRepository {

  private final SQLChatEntryRepository chatEntryRepository;
  private final ChatMetrics metrics;

//  @Override
//  public Mono<Void> save(final ChatEntity entity) {
//    return Mono.defer(() -> {
//      final SQLChatEntry newEntry = SQLChatEntry.fromDomain(entity);
//      return chatEntryRepository.findByChatID(newEntry.getChatID())
//          .map(oldEntry -> {
//            newEntry.setId(oldEntry.getId());
//            return newEntry;
//          }).defaultIfEmpty(newEntry);
//    }).flatMap(chatEntryRepository::save).then();
//  }
//
//  @Override
//  public Flux<ChatEntity> findAll() {
//    return chatEntryRepository.findAll().map(SQLChatEntry::toDomain);
//  }

  @Override
  public Mono<Void> deleteAll() {
    return chatEntryRepository.deleteAll();
  }

  @Override
  public Mono<Void> count() {

    metrics.getGaugeUploadRate().set(chatEntryRepository.count().block());
  }

}

