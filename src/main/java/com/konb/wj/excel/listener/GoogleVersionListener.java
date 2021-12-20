package com.konb.wj.excel.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.konb.wj.excel.data.GoogleVersionData;
import com.konb.wj.pojo.vo.GoogleVersionVo;

import java.util.List;

/**
 * @author konb
 */
public class GoogleVersionListener extends AnalysisEventListener<GoogleVersionData> {

    List<GoogleVersionVo> googleVersionCache;

    public GoogleVersionListener(List<GoogleVersionVo> googleVersionCache) {
        this.googleVersionCache = googleVersionCache;
    }

    @Override
    public void invoke(GoogleVersionData data, AnalysisContext context) {
        //带 VersionCode说明这个是新的版本
        if (data.getVersionCode() != null) {
            GoogleVersionVo googleVersionVo = new GoogleVersionVo();
            googleVersionVo.setVersionCode(data.getVersionCode());
            googleVersionVo.setModifiedItem(data.getModifiedItem());
            googleVersionVo.setData(data);
            this.googleVersionCache.add(googleVersionVo);
        } else {
            int last = this.googleVersionCache.size() - 1;
            this.googleVersionCache.get(last).setData(data);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        System.out.println("解析完成");
    }
}
