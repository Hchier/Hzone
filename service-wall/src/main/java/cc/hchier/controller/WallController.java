package cc.hchier.controller;

import cc.hchier.response.RestResponse;
import cc.hchier.dto.WallAddDTO;
import cc.hchier.service.WallService;
import cc.hchier.vo.WallVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.Date;
import java.util.List;

/**
 * @author by Hchier
 * @Date 2023/2/20 9:39
 */
@Validated
@RestController
public class WallController {
    private final WallService wallService;

    public WallController(WallService wallService) {
        this.wallService = wallService;
    }

    @PostMapping("/wall/add")
    public RestResponse<WallVO> add(@Valid @RequestBody WallAddDTO dto, HttpServletRequest req) {
        String currentUser = req.getHeader("username");
        dto.setCommenter(currentUser).setCreateTime(new Date());
        return wallService.add(dto);
    }

    @PostMapping("/wall/hidden/{id}")
    public RestResponse<Object> hidden(@Min(message = "id无效", value = 1L) @PathVariable Integer id, HttpServletRequest req) {
        String currentUser = req.getHeader("username");
        return wallService.hidden(id, currentUser);
    }

    @PostMapping("/wall/delete/{id}")
    public RestResponse<Object> delete(@Min(message = "id无效", value = 1L) @PathVariable Integer id, HttpServletRequest req) {
        String currentUser = req.getHeader("username");
        return wallService.delete(id, currentUser);
    }

    @PostMapping("/wall/get/{commentee}/{pageNum}")
    public RestResponse<List<WallVO>> get(
        @PathVariable String commentee,
        @PathVariable Integer pageNum,
        HttpServletRequest req) {

        String currentUser = req.getHeader("username");
        return wallService.get(currentUser, commentee, pageNum * 20, 20);
    }
}
