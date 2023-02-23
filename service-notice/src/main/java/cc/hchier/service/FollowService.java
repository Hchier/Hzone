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
     * 查找某人的被关注信息
     *
     * @param username 用户名
     * @param pageNum  页面num
     * @return {@link RestResponse}<{@link List}<{@link FollowVO}>>
     */
    @PostMapping("/follow/followedInfo/{username}/{pageNum}")
    RestResponse<List<FollowVO>> followedInfo(@PathVariable("username") String username, @PathVariable("pageNum") Integer pageNum);
}
