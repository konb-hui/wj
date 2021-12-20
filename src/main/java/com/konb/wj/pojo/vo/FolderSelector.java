package com.konb.wj.pojo.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author konb
 */
@Data
public class FolderSelector {

    private String id;

    private String name;

    private List<FolderSelector> children = new ArrayList<>();

}
