package cc.hchier.controller;

import cc.hchier.response.RestResponse;
import cc.hchier.ws.MyEndpoint;
import cc.hchier.wsMsgs.WsMsgDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author by Hchier
 * @Date 2023/4/8 9:31
 */
@Slf4j
@RestController
public class WsController {
    private final MyEndpoint myEndpoint;

    public WsController(MyEndpoint myEndpoint) {
        this.myEndpoint = myEndpoint;
    }

    @PostMapping("/ws/sendWsDTO")
    public RestResponse<Object> sendPrivateMsg(@RequestBody WsMsgDTO<Object> dto) throws IOException {
        log.info("Controller收到WsMsgDTO：" + dto);
        myEndpoint.sendMessage(dto);
        return RestResponse.ok();
    }

    @PostMapping("/ws/isOnline/{username}")
    public RestResponse<Boolean> isOnline(@PathVariable String username) {
        return RestResponse.ok(myEndpoint.existUser(username));
    }
}
