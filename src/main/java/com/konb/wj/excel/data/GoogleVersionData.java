package com.konb.wj.excel.data;

import lombok.Data;

/**
 * @author konb
 */
@Data
public class GoogleVersionData {

    private String versionCode;

    private String versionName;

    private String isAuthorize;

    private String fingerPrint;

    private String modifiedItem;

    private String productName;

    private String client;

    private String batch;

    private String otaRecord;

    private String otaVersion;

    private String otaTime;

}
