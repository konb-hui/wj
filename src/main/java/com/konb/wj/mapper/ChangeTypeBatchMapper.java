package com.konb.wj.mapper;

import com.konb.wj.pojo.ChangeTypeBatch;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author konb
 * @since 2021-07-09
 */
public interface ChangeTypeBatchMapper extends BaseMapper<ChangeTypeBatch> {

    /**
     * 通过更改类型获得对应的更改批次ID
     * @param type 更改类型
     * @return List<String>
     */
    List<String> getChangeBatchIdsByType(String type);

}
