package com.konb.wj.pojo.tree;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author konb
 */
@Data
public class TreeNode {

    private String value;

    private String label;

    private List<TreeNode> children;

    public void addNode(TreeNode node) {
        if (this.children == null) {
            this.children = new ArrayList<>();
        }
        children.add(node);
    }

}
