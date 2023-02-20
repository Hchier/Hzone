package cc.hchier.service;

import cc.hchier.RestResponse;
import cc.hchier.dto.WallAddDTO;
import cc.hchier.entity.Wall;
import cc.hchier.mapper.WallMapper;
import cc.hchier.vo.WallVO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author by Hchier
 * @Date 2023/2/20 12:47
 */
@Service
public class WallServiceImpl implements WallService {
    private final WallMapper wallMapper;

    public WallServiceImpl(WallMapper wallMapper) {
        this.wallMapper = wallMapper;
    }

    @Override
    public RestResponse<Object> add(WallAddDTO dto) {
        if (wallMapper.insert(dto) == 1) {
            return RestResponse.ok(dto.getId());
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
                    .setCommenter(wall.getCommenter())
                    .setCommenterAvatar("pic")
                    .setCreateTime(wall.getCreateTime())
                    .setHiddenPermission(wall.getCommentee().equals(currentUser))
                    .setDeletePermission(wall.getCommenter().equals(currentUser)));
        }
        return RestResponse.ok(wallVOList);
    }
}