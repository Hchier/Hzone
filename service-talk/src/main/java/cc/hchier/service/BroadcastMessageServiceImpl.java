package cc.hchier.service;

import cc.hchier.dto.BroadcastMsgAddDTO;
import cc.hchier.dto.BroadcastMsgAddSuccessDTO;
import cc.hchier.entity.BroadcastMessage;
import cc.hchier.mapper.BroadcastMessageMapper;
import cc.hchier.nettyTalk.client.service.ClientService;
import cc.hchier.response.RestResponse;
import cc.hchier.vo.BroadcastMsgVO;
import cc.hchier.wsMsgs.BroadcastChatRecallMsg;
import cc.hchier.wsMsgs.WsMsgDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author by Hchier
 * @Date 2023/4/16 12:37
 */
@Service
@Slf4j
public class BroadcastMessageServiceImpl implements BroadcastMessageService {
    private final BroadcastMessageMapper broadcastMessageMapper;
    private final ClientService clientService;

    private final WsService wsService;

    public BroadcastMessageServiceImpl(BroadcastMessageMapper broadcastMessageMapper, ClientService clientService, WsService wsService) {
        this.broadcastMessageMapper = broadcastMessageMapper;
        this.clientService = clientService;
        this.wsService = wsService;
    }

    @Override
    public RestResponse<BroadcastMsgAddSuccessDTO> add(BroadcastMsgAddDTO dto) {
        if (broadcastMessageMapper.insert(dto) == 1) {
            clientService.broadcastTalk(dto);
            return RestResponse.ok(
                new BroadcastMsgAddSuccessDTO()
                    .setId(dto.getId())
                    .setFrom(dto.getFrom())
                    .setContent(dto.getContent())
                    .setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dto.getCreateTime()))
            );
        }
        return RestResponse.fail();
    }

    @Override
    public RestResponse<List<BroadcastMsgVO>> getBroadcastMsgs(String currentUser, int startIndex, int rowNum) {
        List<BroadcastMessage> broadcastMessageList = broadcastMessageMapper.selectList(startIndex, rowNum);
        List<BroadcastMsgVO> broadcastMsgVOList = new ArrayList<>();
        broadcastMessageList.forEach(item -> {
            broadcastMsgVOList.add(
                new BroadcastMsgVO()
                    .setId(item.getId())
                    .setFrom(item.getFrom())
                    .setContent(item.getContent())
                    .setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(item.getCreateTime()))
                    .setFromCurrentUser(currentUser.equals(item.getFrom()))
            );
        });
        return RestResponse.ok(broadcastMsgVOList);
    }

    @Override
    public RestResponse<Object> recall(int id, String from) {
        if (broadcastMessageMapper.recall(id, from) == 0) {
            return RestResponse.fail();
        }
        WsMsgDTO<Object> wsMsgDTO = WsMsgDTO.build(new BroadcastChatRecallMsg(id, from));
        wsService.sendWsDTO(wsMsgDTO);
        log.info("发送WsMsgDTO" + wsMsgDTO);
        return RestResponse.ok();
    }
}
