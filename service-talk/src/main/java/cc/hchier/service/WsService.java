package cc.hchier.service;

import cc.hchier.response.RestResponse;
import cc.hchier.wsMsgs.WsMsgDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author hchier
 */
@FeignClient(value = "service-ws")
public interface WsService {
    /**
     * 发送私信
     *
     * @param dto 数据传输对象
     * @return {@link RestResponse}<{@link Object}>
     */
    @PostMapping("/ws/sendWsDTO")
    RestResponse<Object> sendWsDTO(WsMsgDTO<Object> dto);
}
