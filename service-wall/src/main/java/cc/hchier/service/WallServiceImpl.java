package cc.hchier.service;

import cc.hchier.response.RestResponse;
import cc.hchier.enums.NoticeType;
import cc.hchier.dto.NoticeAddDTO;
import cc.hchier.dto.WallAddDTO;
import cc.hchier.entity.Wall;
import cc.hchier.mapper.WallMapper;
import cc.hchier.vo.WallVO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author by Hchier
 * @Date 2023/2/20 12:47
 */
@Service
public class WallServiceImpl implements WallService {
    private final WallMapper wallMapper;
    private final RabbitTemplate rabbitTemplate;

    public WallServiceImpl(WallMapper wallMapper, RabbitTemplate rabbitTemplate) {
        this.wallMapper = wallMapper;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public RestResponse<WallVO> add(WallAddDTO dto) {
        if (wallMapper.insert(dto) == 1) {
            rabbitTemplate.convertAndSend(
                "directExchange",
                "sendNotice",
                new NoticeAddDTO()
                    .setSender(dto.getCommenter())
                    .setReceiver(dto.getCommentee())
                    .setType(NoticeType.WALL_MESSAGE.getCode())
                    .setContent(dto.getContent())
                    .setLink(dto.getCommentee())
                    .setCreateTime(new Date())
            );
            return RestResponse.ok(
                new WallVO()
                    .setId(dto.getId())
                    .setContent(dto.getContent())
                    .setSender(dto.getCommenter())
                    .setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dto.getCreateTime()))
                    .setHiddenPermission(dto.getCommentee().equals(dto.getCommenter()))
                    .setDeletePermission(true)
            );
        }
        return RestResponse.fail();
    }

    @Override
    public RestResponse<Object> hidden(int id, String currentUser) {
        if (wallMapper.hiddenByPrimaryKey(id, currentUser) == 1) {
            return RestResponse.ok();
        }
        return RestResponse.fail();
    }

    @Override
    public RestResponse<Object> delete(int id, String currentUser) {
        if (wallMapper.deleteByPrimaryKey(id, currentUser) == 1) {
            return RestResponse.ok();
        }
        return RestResponse.fail();
    }

    @Override
    public RestResponse<List<WallVO>> get(String currentUser, String commentee, int startIndex, int rowNum) {
        List<Wall> wallList = wallMapper.selectByCommentee(commentee, startIndex, rowNum);
        List<WallVO> wallVOList = new ArrayList<>(wallList.size());
        for (Wall wall : wallList) {
            wallVOList.add(
                new WallVO()
                    .setId(wall.getId())
                    .setContent(wall.getContent())
                    .setSender(wall.getCommenter())
                    .setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(wall.getCreateTime()))
                    .setHiddenPermission(wall.getCommentee().equals(currentUser))
                    .setDeletePermission(wall.getCommenter().equals(currentUser)));
        }
        return RestResponse.ok(wallVOList);
    }
}
