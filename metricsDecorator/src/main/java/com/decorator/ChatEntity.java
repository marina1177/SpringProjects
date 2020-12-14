package com.decorator;


import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(of = {"id"})
public class ChatEntity {

  private Long id;
  private Boolean enabled;
  private String name;
  private String icon;
  private List<String> keyWords;
  private String linkName;
  private String type;
  private String iconExt;
  private String description;
  private Float score;

}
