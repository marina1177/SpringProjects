package com.decorator.output;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table(SQLChatEntry.TABLE_NAME)
@NoArgsConstructor
@AllArgsConstructor
 class SQLChatEntry {

  static final String TABLE_NAME = "search_chats";

  @Id
  @Column("id")
  private Long id;

  @Column("chat_id")
  private Long chatID;

  @Column("enabled")
  private Boolean enabled;

  @Column("name")
  private String name;

  @Column("icon")
  private String icon;

  @Column("keywords")
  private List<String> keyWords;

  @Column("link_name")
  private String linkName;

  @Column("type")
  private String type;

  @Column("icon_ext")
  private String iconExt;

  @Column("description")
  private String description;

  @Column("score")
  private Float score;

  static SQLChatEntry fromDomain(final ChatEntity chatEntity) {
    return new SQLChatEntry(null, chatEntity.getId(), chatEntity.getEnabled(),
        chatEntity.getName(), chatEntity.getIcon(), chatEntity.getKeyWords(), chatEntity.getLinkName(),
        chatEntity.getType(), chatEntity.getIconExt(), chatEntity.getDescription(), chatEntity.getScore());
  }

  ChatEntity toDomain() {
    return ChatEntity.builder()
        .id(chatID)
        .enabled(enabled)
        .name(name)
        .icon(icon)
        .keyWords(keyWords)
        .linkName(linkName)
        .type(type)
        .iconExt(iconExt)
        .description(description)
        .score(score)
        .build();
  }

}
