package nl.brighton.zolder.controller;

import nl.brighton.zolder.dto.Location;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class LocationController {

  @MessageMapping("/get")
  @SendTo("/location/get")
  public Location getLocation(@Payload String user) {
    return new Location("", 1, 2, user, "2", 123);
  }

}
