package com.konb.wj.pojo.google.version;

import com.konb.wj.excel.data.GoogleVersionData;
import lombok.Data;
import org.springframework.beans.BeanUtils;

/**
 * @author konb
 */
@Data
public class VersionDetailInfo {

    private String client;

    private String batch;

    private String otaRecord;

    private String otaVersion;

    private String otaTime;

    public void setData(GoogleVersionData googleVersionData) {
        BeanUtils.copyProperties(googleVersionData, this);
    }

}
