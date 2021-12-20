package com.konb.wj.pojo.vo;

import com.konb.wj.excel.data.GoogleVersionData;
import com.konb.wj.pojo.google.version.VersionBaseInfo;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author konb
 */
@Data
public class GoogleVersionVo {

    public GoogleVersionVo() {
        versionBaseInfoList = new ArrayList<>();
    }

    private String versionCode;

    private String modifiedItem;

    List<VersionBaseInfo> versionBaseInfoList;

    public void setData(GoogleVersionData googleVersionData) {
        if (googleVersionData.getVersionName() != null) {
            VersionBaseInfo baseInfo = new VersionBaseInfo();
            baseInfo.setProductName(googleVersionData.getProductName());
            baseInfo.setFingerPrint(googleVersionData.getFingerPrint());
            baseInfo.setVersionName(googleVersionData.getVersionName());
            baseInfo.setIsAuthorize(googleVersionData.getIsAuthorize());
            baseInfo.setData(googleVersionData);
            this.versionBaseInfoList.add(baseInfo);
        } else {
            int last = this.versionBaseInfoList.size() - 1;
            this.versionBaseInfoList.get(last).setData(googleVersionData);
        }
    }

}
