package com.konb.wj.controller;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.enums.CellExtraTypeEnum;
import com.konb.wj.config.FileConfig;
import com.konb.wj.excel.listener.ExcelDataListener;
import com.konb.wj.pojo.ChangeBatch;
import com.konb.wj.pojo.Notice;
import com.konb.wj.pojo.User;
import com.konb.wj.pojo.result.R;
import com.konb.wj.service.UserService;
import com.konb.wj.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author konb
 * @since 2021-02-23
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private FileConfig fileConfig;

    @PostMapping("login")
    public R login(@RequestBody(required = false) User user, HttpServletRequest request) {

        User user1 = this.userService.getByNameAndPwd(user);

        HttpSession session = request.getSession();

        if(user1 == null) {
            return R.error().message("用户名或密码错误");
        } else {
            String token = JwtUtil.getJwtToken(user1.getId(), user.getUsername());
            return R.ok().data("token", token);
        }
    }

}

