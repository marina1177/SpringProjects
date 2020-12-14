package com.hello.chats;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatController {

  private final ChatRepository chatRepository;

  @GetMapping(value = "/chat")
  public String load(){
    chatRepository.save("chat");
    chatRepository.count();
    return "get Chat metric";
  }

}
