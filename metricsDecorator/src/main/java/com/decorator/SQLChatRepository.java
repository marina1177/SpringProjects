package com.decorator;

import com.decorator.output.SQLChatEntryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
final class SQLChatRepository implements ChatRepository {

  private final SQLChatEntryRepository chatEntryRepository;

  @Override
  public Long count() {
    return chatEntryRepository.;
  }
}
