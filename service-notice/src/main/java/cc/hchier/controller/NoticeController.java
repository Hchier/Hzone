package cc.hchier.controller;

import cc.hchier.RestResponse;
import cc.hchier.service.NoticeService;
import cc.hchier.vo.NoticeVO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author by Hchier
 * @Date 2023/2/23 13:22
 */
@RestController
public class NoticeController {
    private final NoticeService noticeService;

    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @PostMapping("/notice/delete/{id}")
    public RestResponse<Object> delete(@PathVariable Integer id, HttpServletRequest req) {
        return noticeService.delete(id, req.getHeader("username"));
    }

    @PostMapping("/notice/get/{pageNum}")
    public RestResponse<List<NoticeVO>> get(@PathVariable Integer pageNum, HttpServletRequest req) {
        return noticeService.get(req.getHeader("username"), pageNum * 20, 20);
    }
}
