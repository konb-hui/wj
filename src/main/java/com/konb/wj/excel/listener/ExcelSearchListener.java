package com.konb.wj.excel.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.konb.wj.utils.ExcelUtils;
import lombok.SneakyThrows;

import java.util.Map;

/**
 * @author konb
 */
public class ExcelSearchListener extends AnalysisEventListener<Map<Integer, String>> {

    public ExcelSearchListener(String key, String filePath) {
        this.key = key;
        this.filePath = filePath;
    }

    private int blankCount;

    private String key;

    private String filePath;

    private boolean isExist = false;

    private static final int MAX_BLANK_COUNT = 5;

    @SneakyThrows
    @Override
    public void invoke(Map<Integer, String> data, AnalysisContext context) {
        if (blankCount >= MAX_BLANK_COUNT) {
            return;
        }
        if (StringUtils.isEmpty(data.get(0)) && StringUtils.isEmpty(data.get(1)) && StringUtils.isEmpty(data.get(2))) {
            blankCount ++;
        }
        for (int i = 0; i < data.size(); i ++) {
            System.out.println(data);
            blankCount = 0;
            if (StringUtils.isEmpty(data.get(i))) {
                continue;
            }
            if (data.get(i).contains(key)) {
                isExist = true;
                ExcelUtils.saveData(data.get(i));
                System.out.println(data.get(i));
            }
        }

    }

    @SneakyThrows
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        if (isExist) {
            ExcelUtils.saveData(filePath);
            System.out.println(filePath);
        }
        blankCount = 0;
    }
}
