package cc.hchier.mapper;

import cc.hchier.dto.UserRegisterDTO;
import cc.hchier.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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

    /**
     * 查找某用户名是否已被使用
     *
     * @param username 用户名
     * @return int
     */
    int existUser(@Param("username") String username);

    /**
     * 查询用户
     *
     * @param username 用户名
     * @return {@link User}
     */
    User selectUser(@Param("username") String username);

    /**
     * 通过电子邮件更新密码
     *
     * @param password 密码
     * @param email    电子邮件
     * @return int
     */
    int updatePasswordByEmail(@Param("password") String password, @Param("email") String email);
}