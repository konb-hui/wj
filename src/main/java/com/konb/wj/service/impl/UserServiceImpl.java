package com.konb.wj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.konb.wj.pojo.User;
import com.konb.wj.mapper.UserMapper;
import com.konb.wj.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author konb
 * @since 2021-02-23
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public User getByNameAndPwd(User user) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .eq("username", user.getUsername())
                .eq("password", user.getPassword());
        return this.baseMapper.selectOne(queryWrapper);
    }
}
