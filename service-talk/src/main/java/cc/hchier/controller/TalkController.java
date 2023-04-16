package cc.hchier.controller;

import cc.hchier.dto.*;
import cc.hchier.response.RestResponse;
import cc.hchier.enums.ResponseCode;
import cc.hchier.service.BroadcastMessageService;
import cc.hchier.service.PrivateMessageService;
import cc.hchier.service.UserService;
import cc.hchier.nettyTalk.client.service.ClientService;
import cc.hchier.vo.BroadcastMsgVO;
import cc.hchier.vo.ChatMsgVO;
import cc.hchier.vo.ChatUserVO;
import cc.hchier.vo.PrivateMessageVO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;
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

    private final BroadcastMessageService broadcastMessageService;

    public TalkController(ClientService clientService, UserService userService, PrivateMessageService privateMessageService, BroadcastMessageService broadcastMessageService) {
        this.clientService = clientService;
        this.userService = userService;
        this.privateMessageService = privateMessageService;
        this.broadcastMessageService = broadcastMessageService;
    }

    @PostMapping("/talk/createChannel/{username}")
    public RestResponse<Object> createChannel(@PathVariable String username) {
        return (clientService.getChannel(username)) != null ? RestResponse.ok() : RestResponse.fail();
    }

    @PostMapping("/talk/closeChannel/{username}")
    public RestResponse<Boolean> closeChannel(@PathVariable String username) {
        clientService.closeChannel(username);
        return RestResponse.ok(true);
    }

    @PostMapping("/talk/privateTalk")
    public RestResponse<ChatMsgVO> privateTalk(@Valid @RequestBody PrivateChatAddDTO dto, HttpServletRequest req) {
        if (userService.existUser(dto.getTo()).getCode() != ResponseCode.OK.getCode()) {
            return RestResponse.build(ResponseCode.TARGET_USER_NOT_EXIST);
        }
        return clientService.privateTalk(dto.setFrom(req.getHeader("username")));
    }

    @PostMapping("/talk/getPrivateMsgsWith/{username}/{pageSize}")
    public RestResponse<List<ChatMsgVO>> getPrivateMsgsWith(
        @PathVariable String username,
        @PathVariable Integer pageSize,
        HttpServletRequest req) {
        return privateMessageService.getPrivateMessages(req.getHeader("username"), username, pageSize * 10, 10);
    }

    @PostMapping("/talk/privateMsgRecall")
    public RestResponse<Object> recall(@Valid @RequestBody PrivateMsgRecallDTO dto, HttpServletRequest req) {
        return privateMessageService.recall(dto.setSender(req.getHeader("username")));
    }

    @PostMapping("/talk/setMsgsRead/{from}")
    public RestResponse<Integer> setMsgsRead(@PathVariable String from, HttpServletRequest req) {
        return privateMessageService.setMsgsRead(from, req.getHeader("username"));
    }

    @PostMapping("/talk/getChatUserVOList")
    public RestResponse<List<ChatUserVO>> getChatUserVOList(HttpServletRequest req) {
        return privateMessageService.getChatUserVOList(req.getHeader("username"));
    }

    @PostMapping("/talk/broadcastTalk")
    public RestResponse<BroadcastMsgAddSuccessDTO> broadcastTalk(@Valid @RequestBody BroadcastMsgAddDTO dto, HttpServletRequest req) {
        return broadcastMessageService.add(dto.setFrom(req.getHeader("username")).setCreateTime(new Date()));
    }

    @PostMapping("/talk/getBroadcastMsgs/{pageSize}")
    public RestResponse<List<BroadcastMsgVO>> getBroadcastMsgs(@PathVariable Integer pageSize, HttpServletRequest req) {
        return broadcastMessageService.getBroadcastMsgs(req.getHeader("username"), pageSize * 10, 10);
    }

    @PostMapping("/talk/broadcastMsgRecall/{id}")
    public RestResponse<Object> broadcastMsgRecall(@PathVariable Integer id, HttpServletRequest req) {
        return broadcastMessageService.recall(id, req.getHeader("username"));
    }
}
