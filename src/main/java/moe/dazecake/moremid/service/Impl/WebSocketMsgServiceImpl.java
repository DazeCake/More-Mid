package moe.dazecake.moremid.service.Impl;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import moe.dazecake.moremid.pojo.BDXWebSocketMsg;
import moe.dazecake.moremid.service.WebSocketMsgService;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class WebSocketMsgServiceImpl implements WebSocketMsgService {
    @Override
    public BDXWebSocketMsg msgDecoder(String msg) {
        Gson gson = new Gson();
        return gson.fromJson(msg, BDXWebSocketMsg.class);
    }

    @Override
    public void causeSelector(BDXWebSocketMsg bdxWebSocketMsg) {
        switch (bdxWebSocketMsg.getCause()) {
            case "join":
                onJoin(bdxWebSocketMsg);
                break;
            case "left":
                onLeft(bdxWebSocketMsg);
                break;
            case "cmd":
                onCmd(bdxWebSocketMsg);
                break;
            case "chat":
                onChat(bdxWebSocketMsg);
                break;
            case "mobdie":
                onMobDie(bdxWebSocketMsg);
                break;
        }
    }

    @Override
    public void onJoin(BDXWebSocketMsg bdxWebSocketMsg) {

    }

    @Override
    public void onLeft(BDXWebSocketMsg bdxWebSocketMsg) {

    }

    @Override
    public void onCmd(BDXWebSocketMsg bdxWebSocketMsg) {

    }

    @Override
    public void onChat(BDXWebSocketMsg bdxWebSocketMsg) {

    }

    @Override
    public void onMobDie(BDXWebSocketMsg bdxWebSocketMsg) {

    }
}
