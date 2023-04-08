package cc.hchier.service;

import cc.hchier.RestResponse;
import cc.hchier.consts.WsMsgType;
import cc.hchier.dto.PrivateChatAddDTO;
import cc.hchier.dto.PrivateMsgRecallDTO;
import cc.hchier.dto.WsMsgDTO;
import cc.hchier.entity.PrivateMessage;
import cc.hchier.mapper.PrivateMessageMapper;
import cc.hchier.vo.ChatUserVO;
import cc.hchier.vo.PrivateMessageVO;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author by Hchier
 * @Date 2023/2/25 20:37
 */
@Service
public class PrivateMessageServiceImpl implements PrivateMessageService {
    private final PrivateMessageMapper privateMessageMapper;
    private final UserService userService;

    public PrivateMessageServiceImpl(PrivateMessageMapper privateMessageMapper, UserService userService) {
        this.privateMessageMapper = privateMessageMapper;
        this.userService = userService;
    }

    @Override
    public boolean add(PrivateChatAddDTO dto) {
        return privateMessageMapper.insert(dto) == 1;
    }

    @Override
    public RestResponse<List<PrivateMessageVO>> getPrivateMessages(String username1, String username2, int startIndex, int rowNum) {
        List<PrivateMessage> privateMessageList = privateMessageMapper.select(username1, username2, startIndex, rowNum);
        List<PrivateMessageVO> privateMessageVOList = new ArrayList<>();
        for (PrivateMessage privateMessage : privateMessageList) {
            privateMessageVOList.add(
                new PrivateMessageVO()
                    .setId(privateMessage.getId())
                    .setFrom(privateMessage.getFrom())
                    .setTo(privateMessage.getTo())
                    .setContent(privateMessage.getContent())
                    .setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(privateMessage.getCreateTime()))
                    .setFromCurrentUser(username1.equals(privateMessage.getFrom())));
        }
        return RestResponse.ok(privateMessageVOList);
    }

    @Override
    public RestResponse<Object> recall(PrivateMsgRecallDTO dto) {
        if (privateMessageMapper.recall(dto.getId(), dto.getSender()) == 0) {
            return RestResponse.fail();
        }
        userService.sendWsDTO(WsMsgDTO.build(WsMsgType.PrivateMsgRecall.getCode(), dto.getReceiver(), dto));
        return RestResponse.ok();
    }

    @Override
    public RestResponse<Integer> setMsgsRead(String from, String to) {
        return RestResponse.ok(privateMessageMapper.setMsgsRead(from, to));
    }

    @Override
    public RestResponse<List<ChatUserVO>> getChatUserVOList(String receiver) {
        return RestResponse.ok(privateMessageMapper.getChatUserVOList(receiver));
    }
}
