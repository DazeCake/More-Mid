package moe.dazecake.moremid.config;

import moe.dazecake.moremid.pojo.BDXWebSocketMsg;
import moe.dazecake.moremid.service.Impl.WebSocketMsgServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import javax.annotation.Resource;
import java.net.URI;

@Slf4j
@Configuration
public class WebSocketConfig {

    @Value("${ws.url}")
    String wsUrl;

    @Resource
    WebSocketMsgServiceImpl webSocketMsgService;

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

    @Bean
    public WebSocketClient webSocketClient() {
        try {
            WebSocketClient webSocketClient = new WebSocketClient(new URI(wsUrl), new Draft_6455()) {
                @Override
                public void onOpen(ServerHandshake handshake) {
                    log.info("[websocket] 连接成功");
                }

                @Override
                public void onMessage(String message) {
                    log.info("[websocket] 收到消息={}", message);
                    //反序列化
                    BDXWebSocketMsg bdxWebSocketMsg = webSocketMsgService.msgDecoder(message);
                    webSocketMsgService.causeSelector(bdxWebSocketMsg);
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    log.info("[websocket] 退出连接");
                }

                @Override
                public void onError(Exception ex) {
                    log.info("[websocket] 连接错误={}", ex.getMessage());
                }
            };
            webSocketClient.connect();
            return webSocketClient;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
