package cc.hchier.mapper;

import cc.hchier.dto.UserRegisterDTO;
import cc.hchier.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author hchier
 */
@Mapper
public interface UserMapper {
//    int deleteByPrimaryKey(String username);

    /**
     * 插入用户信息
     *
     * @param userRegisterDTO 用户注册dto
     * @return int
     */
    int insert(UserRegisterDTO userRegisterDTO);

    /**
     * 根据用户名查询密码
     *
     * @param username 用户名
     * @return {@link String}
     */
    String selectPasswordByUsername(String username);

    /**
     * 更新用户信息
     *
     * @param user 用户
     * @return int
     */
    int update(User user);

    //    User selectByPrimaryKey(String username);
//
//    List<User> selectAll();
//
}