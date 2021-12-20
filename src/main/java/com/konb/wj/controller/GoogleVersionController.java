package com.konb.wj.controller;

import com.alibaba.excel.EasyExcel;
import com.konb.wj.excel.data.GoogleVersionData;
import com.konb.wj.excel.listener.GoogleVersionListener;
import com.konb.wj.pojo.result.R;
import com.konb.wj.pojo.vo.GoogleVersionQuery;
import com.konb.wj.pojo.vo.GoogleVersionVo;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author konb
 */
@RestController
@RequestMapping("/google/version")
public class GoogleVersionController {

    private List<GoogleVersionVo> googleVersionData;

    @PostMapping("list")
    public R list(@RequestBody(required = false)GoogleVersionQuery googleVersionQuery) {

        googleVersionQuery.setPath("D:/6886 DVB版本管理表.xlsx");

        if (this.googleVersionData == null) {
            this.googleVersionData = new ArrayList<>();
        }

        if (this.googleVersionData.size() < 1) {
            EasyExcel
                    .read(googleVersionQuery.getPath(), GoogleVersionData.class, new GoogleVersionListener(this.googleVersionData))
                    .sheet()
                    .doRead();
        }

        return R.ok().message("查询成功").data("data", this.googleVersionData);
    }

}
