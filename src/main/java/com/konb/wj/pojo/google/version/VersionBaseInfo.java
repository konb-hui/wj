package com.konb.wj.pojo.google.version;

import com.konb.wj.excel.data.GoogleVersionData;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author konb
 */
@Data
public class VersionBaseInfo {

    public VersionBaseInfo() {
        versionDetailInfoList = new ArrayList<>();
    }

    private String versionName;

    private String isAuthorize;

    private String fingerPrint;

    private String productName;

    List<VersionDetailInfo> versionDetailInfoList;

    public void setData(GoogleVersionData googleVersionData) {
        VersionDetailInfo detailInfo = new VersionDetailInfo();
        detailInfo.setData(googleVersionData);
        this.versionDetailInfoList.add(detailInfo);
    }
}
