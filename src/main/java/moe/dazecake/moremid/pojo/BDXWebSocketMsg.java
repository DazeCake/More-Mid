package moe.dazecake.moremid.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

/**
 * BDXWebSocket msg pojo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BDXWebSocketMsg {
    String type;

    String cause;

    HashMap<String, Object> params;
}
