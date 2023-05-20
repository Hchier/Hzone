package cc.hchier.controller;

import cc.hchier.response.RestResponse;
import cc.hchier.dto.FollowCancelDTO;
import cc.hchier.dto.FollowDTO;
import cc.hchier.service.FollowService;
import cc.hchier.vo.FollowTopicVO;
import cc.hchier.vo.FollowUserVO;

import io.seata.core.exception.TransactionException;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * @author by Hchier
 * @Date 2023/2/22 20:23
 */
@RestController
public class FollowController {
    private final FollowService followService;

    public FollowController(FollowService followService) {
        this.followService = followService;
    }

    @SneakyThrows
    @PostMapping("/follow/follow")
    public RestResponse<Object> follow(@RequestBody FollowDTO dto, HttpServletRequest req) {
        return followService.follow(dto.setFollower(req.getHeader("username")).setCreateTime(new Date()));
    }

    @SneakyThrows
    @PostMapping("/follow/unFollow")
    public RestResponse<Object> unFollow(@RequestBody FollowCancelDTO dto, HttpServletRequest req) {
        return followService.unFollow(dto.setFollower(req.getHeader("username")));
    }

    @PostMapping("/follow/followerList/{followee}/{type}/{pageNum}")
    public RestResponse<List<FollowUserVO>> followerList(
        @PathVariable String followee,
        @PathVariable Integer type,
        @PathVariable Integer pageNum,
        HttpServletRequest req) {
        return followService.getFollowerList(
            followee,
            type,
            req.getHeader("username"),
            pageNum * 10,
            10);
    }

    @PostMapping("/follow/exist/{follower}/{followee}/{type}")
    public RestResponse<Boolean> exist(@PathVariable String follower, @PathVariable String followee, @PathVariable Integer type) {
        return RestResponse.ok(followService.existFollow(follower, followee, type));
    }

    @PostMapping("/follow/topicList/{follower}/{pageNum}")
    public RestResponse<List<FollowTopicVO>> topicList(
        @PathVariable String follower,
        @PathVariable Integer pageNum,
        HttpServletRequest req) {
        return followService.getFollowTopicList(follower, req.getHeader("username"), 10 * pageNum, 10);
    }

    @PostMapping("/follow/userList/{follower}/{pageNum}")
    public RestResponse<List<FollowUserVO>> userList(
        @PathVariable String follower,
        @PathVariable Integer pageNum,
        HttpServletRequest req) {
        return followService.getFollowUserList(follower, req.getHeader("username"), 10 * pageNum, 10);
    }
}
