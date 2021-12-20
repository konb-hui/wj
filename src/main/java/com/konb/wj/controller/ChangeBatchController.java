package com.konb.wj.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.konb.wj.pojo.ChangeBatch;
import com.konb.wj.pojo.result.R;
import com.konb.wj.pojo.vo.ChangeBatchQuery;
import com.konb.wj.pojo.vo.NoticeVo;
import com.konb.wj.service.ChangeBatchService;
import lombok.val;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author konb
 * @since 2021-05-31
 */
@RestController
@RequestMapping("/wj/change-batch")
public class ChangeBatchController {

    @Autowired
    private ChangeBatchService changeBatchService;

    @GetMapping("getTeams")
    public R getTeams() {
        Set<String> teams = this.changeBatchService.listAllKindTeams();
        return R.ok().data("teams", teams);
    }

    @PostMapping("getChangeBatchByTeams")
    public R getChangeBatchByTeams(@RequestBody ChangeBatchQuery changeBatchQuery) {

        QueryWrapper<ChangeBatch> queryWrapper = new QueryWrapper<>();
        if (CollectionUtils.isNotEmpty(changeBatchQuery.getTeams())) {
            queryWrapper.in("team", changeBatchQuery.getTeams());
        } else {
            return R.ok();
        }

        if (StringUtils.isNotEmpty(changeBatchQuery.getKey())) {
            queryWrapper.like("name", changeBatchQuery.getKey());
        }

        if (StringUtils.isNotEmpty(changeBatchQuery.getBatch())) {
            queryWrapper.eq("batch", changeBatchQuery.getBatch());
        }

        List<ChangeBatch> data = this.changeBatchService.list(queryWrapper);

        Map<String, List<NoticeVo>> result = this.changeBatchService.changeFormatOfResult(data, changeBatchQuery);

        return R.ok().data("data", result);

    }

}

