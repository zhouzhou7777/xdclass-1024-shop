package net.xdclass.mapper;

import net.xdclass.model.UserDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.xdclass.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhouzhou7777
 * @since 2023-12-02
 */
public interface UserMapper extends BaseMapper<UserDO> {

    @Select("select * form user where username=#{username}")
    User findByUserName(String username);

    @Insert("insert into user(username,password,create_time,updata_time)" +
            " values(#{username},#{password},now(),now())")
    void add(String username, String password);

    @Update("update user set nickname=#{nickname},email=#{email},update_time=#{updateTime} where id=#{id}")
    void update(User user);

    @Update("update user set user_pic=#{avataUrl},update_time=now() where id=#{id}")
    void updateAvatar(String avatarUrl, Integer id);

    @Update("update user set password=#{md5String}, update_time=now() where id=#{id}")
    void updatePwd(String md5String, Integer id);
}
