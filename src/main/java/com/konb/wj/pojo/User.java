package com.konb.wj.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * @author konb
 */
@Data
public class User implements Serializable {

    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;
    private String username;
    private String password;

}
