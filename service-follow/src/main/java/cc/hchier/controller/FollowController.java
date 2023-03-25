package cc.hchier.controller;

import cc.hchier.RestResponse;
import cc.hchier.dto.FollowCancelDTO;
import cc.hchier.dto.FollowDTO;
import cc.hchier.service.FollowService;
import cc.hchier.vo.FollowVO;
import io.seata.core.exception.TransactionException;
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

    @PostMapping("/follow/follow")
    public RestResponse<Object> follow(@RequestBody FollowDTO dto, HttpServletRequest req) throws TransactionException {
        return followService.follow(dto.setFollower(req.getHeader("username")).setCreateTime(new Date()));
    }

    @PostMapping("/follow/unFollow")
    public RestResponse<Object> unFollow(@RequestBody FollowCancelDTO dto, HttpServletRequest req) throws TransactionException {
        return followService.unFollow(dto.setFollower(req.getHeader("username")));
    }

    @PostMapping("/follow/followInfo/{username}/{pageNum}")
    public RestResponse<List<FollowVO>> followInfo(@PathVariable String username, @PathVariable Integer pageNum) {
        return followService.followInfo(username, pageNum * 20, 20);
    }

    @PostMapping("/follow/followedInfo/{followee}/{type}/{pageNum}")
    public RestResponse<List<String>> followedInfo(
        @PathVariable String followee,
        @PathVariable Integer type,
        @PathVariable Integer pageNum) {
        return followService.followedInfo(followee, type, pageNum * 20, 20);
    }

    @PostMapping("/follow/exist/{follower}/{followee}/{type}")
    public RestResponse<Boolean> exist(@PathVariable String follower, @PathVariable String followee, @PathVariable Integer type) {
        return RestResponse.ok(followService.existFollow(follower, followee, type));
    }
}
