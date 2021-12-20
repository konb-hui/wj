package com.konb.wj.pojo.vo;

import lombok.Data;

import java.util.List;

/**
 * @author konb
 */
@Data
public class ChangeBatchQuery {

    private String key;

    private String batch;

    private boolean onlyPanel;

    private List<String> teams;

}
