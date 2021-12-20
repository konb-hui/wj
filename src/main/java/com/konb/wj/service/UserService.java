package com.konb.wj.service;

import com.konb.wj.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author konb
 * @since 2021-02-23
 */
public interface UserService extends IService<User> {

    /**
     * 通过用户名和密码获取当前用户
     * @param user 用户
     * @return user
     */
    User getByNameAndPwd(User user);
}
