package cc.hchier.controller;

import cc.hchier.RestResponse;
import cc.hchier.consts.ResponseCode;
import cc.hchier.service.PrivateMessageService;
import cc.hchier.service.UserService;
import cc.hchier.talk.client.service.ClientService;
import cc.hchier.dto.PrivateChatAddDTO;
import cc.hchier.vo.PrivateMessageVO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * @author by Hchier
 * @Date 2023/2/25 9:50
 */
@RestController
public class TalkController {
    private final ClientService clientService;
    private final UserService userService;
    private final PrivateMessageService privateMessageService;

    public TalkController(ClientService clientService, UserService userService, PrivateMessageService privateMessageService) {
        this.clientService = clientService;
        this.userService = userService;
        this.privateMessageService = privateMessageService;
    }

    @PostMapping("/talk/createChannel")
    public RestResponse<Object> createChannel(HttpServletRequest req) {
        return (clientService.createChannel(req.getHeader("username"))) != null ? RestResponse.ok() : RestResponse.fail();
    }

    @PostMapping("/talk/privateTalk")
    public RestResponse<Object> privateTalk(@Valid @RequestBody PrivateChatAddDTO dto, HttpServletRequest req) {
        if (userService.existUser(dto.getTo()).getCode() != ResponseCode.OK.getCode()) {
            return RestResponse.build(ResponseCode.TARGET_USER_NOT_EXIST);
        }
        return clientService.privateTalk(dto.setFrom(req.getHeader("username")));
    }

    @PostMapping("/talk/getPrivateMsgsWith/{to}/{pageSize}")
    public RestResponse<List<PrivateMessageVO>> getPrivateMsgsWith(
        @PathVariable String to,
        @PathVariable Integer pageSize,
        HttpServletRequest req) {
        return privateMessageService.getPrivateMessages(req.getHeader("username"), to, pageSize * 10, 10);
    }

    @PostMapping("/talk/recall/{id}")
    public RestResponse<Object> recall(@PathVariable Integer id, HttpServletRequest req) {
        return privateMessageService.recall(id, req.getHeader("username"));
    }

    @PostMapping("/talk/setMsgsRead/{from}")
    public RestResponse<Integer> setMsgsRead(@PathVariable String from, HttpServletRequest req) {
        return privateMessageService.setMsgsRead(from, req.getHeader("username"));
    }
}
