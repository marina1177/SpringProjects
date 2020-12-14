package com.decorator.output;

import org.springframework.data.repository.CrudRepository;

interface SQLChatEntryRepository extends CrudRepository<SQLChatEntry, Long> {

  SQLChatEntry findByChatID(final Long chatID);
}
