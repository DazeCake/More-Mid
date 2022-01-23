package moe.dazecake.moremid.service;

import moe.dazecake.moremid.pojo.BDXWebSocketMsg;

public interface WebSocketMsgService {
    //json消息反序列化
    BDXWebSocketMsg msgDecoder(String msg);

    //cause选择器
    void causeSelector(BDXWebSocketMsg bdxWebSocketMsg);

    void onJoin(BDXWebSocketMsg bdxWebSocketMsg);

    void onLeft(BDXWebSocketMsg bdxWebSocketMsg);

    void onCmd(BDXWebSocketMsg bdxWebSocketMsg);

    void onChat(BDXWebSocketMsg bdxWebSocketMsg);

    void onMobDie(BDXWebSocketMsg bdxWebSocketMsg);


}
