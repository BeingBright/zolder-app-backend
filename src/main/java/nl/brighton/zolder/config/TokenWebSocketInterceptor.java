package nl.brighton.zolder.config;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

@RequiredArgsConstructor
@Component
public class TokenWebSocketInterceptor extends HttpSessionHandshakeInterceptor {

  @Override
  public boolean beforeHandshake(ServerHttpRequest request,
      ServerHttpResponse response, WebSocketHandler webSocketHandler,
      Map<String, Object> map) throws Exception {
    System.out.println(request.getHeaders());
    System.out.println(map);
    return true;
  }

}
