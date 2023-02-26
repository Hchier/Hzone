package cc.hchier.service;

import cc.hchier.RestResponse;
import cc.hchier.vo.FollowVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * @author hchier
 */
@FeignClient(value = "service-follow")
public interface FollowService {

    /**
     * 查找关注某人或某话题的用户的用户名
     *
     * @param followee followee
     * @param type     类型
     * @param pageNum  页面num
     * @return {@link RestResponse}<{@link List}<{@link FollowVO}>>
     */
    @PostMapping("/follow/followedInfo/{followee}/{type}/{pageNum}")
    RestResponse<List<String>> followedInfo(
        @PathVariable("followee") String followee,
        @PathVariable("type") Integer type,
        @PathVariable("pageNum") Integer pageNum);
}
