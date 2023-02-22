package cc.hchier.mapper;

import cc.hchier.dto.UserRegisterDTO;
import cc.hchier.entity.User;
import org.apache.ibatis.annotations.Mapper;

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

    /**
     * 各种num + 1，如favorNum，followNum
     * 暂时用各种num作为增量
     * 不为null时生效
     *
     * @param user 用户
     * @return int
     */
    int incrNum(User user);

    /**
     * 查找用户的email
     *
     * @param username 用户名
     * @return {@link String}
     */
    String selectEmailByUsername(String username);

    //    User selectByPrimaryKey(String username);
//
//    List<User> selectAll();
//
}