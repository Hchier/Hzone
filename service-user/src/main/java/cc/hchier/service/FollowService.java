package cc.hchier.service;

import cc.hchier.RestResponse;
import cc.hchier.consts.FollowType;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author hchier
 */
@FeignClient(value = "service-follow")
public interface FollowService {
    /**
     * 判断是否关注
     *
     * @param follower 关注者
     * @param followee 被关注者
     * @param type     关注类型->{@link FollowType}
     * @return {@link RestResponse}<{@link Boolean}>
     */
    @PostMapping("/follow/exist/{follower}/{followee}/{type}")
    RestResponse<Boolean> existFollow(
        @PathVariable("follower") String follower,
        @PathVariable("followee") String followee,
        @PathVariable("type") Integer type);
}
