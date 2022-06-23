package nl.brighton.zolder.resource;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class WebSocketMessagingController {

  private final SimpMessagingTemplate simpMessagingTemplate;

  public void sendUpdate(String destination) {
    simpMessagingTemplate.convertAndSend(String.format("/topic%s", destination), "");
  }

  public void sendUpdate(String destination, Object payload) {
    simpMessagingTemplate.convertAndSend(String.format("/topic%s", destination), payload);
  }

}
