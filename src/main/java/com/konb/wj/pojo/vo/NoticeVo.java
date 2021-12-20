package com.konb.wj.pojo.vo;

import com.konb.wj.pojo.Notice;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author konb
 */
@Data
public class NoticeVo {

    public NoticeVo() {
        changeData = new ArrayList<>();
    }

    private Notice notice;

    private List<List<String>> changeData;

    private List<String> template;

    private String excelPath;

}
