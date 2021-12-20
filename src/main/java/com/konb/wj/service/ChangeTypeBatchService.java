package com.konb.wj.service;

import com.konb.wj.pojo.ChangeBatch;
import com.konb.wj.pojo.ChangeTypeBatch;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author konb
 * @since 2021-07-09
 */
public interface ChangeTypeBatchService extends IService<ChangeTypeBatch> {

    /**
     * 批量保存changeBatch的type
     * @param list List<ChangeBatch>
     */
    void saveBatchChangeBatch(List<ChangeBatch> list);

    /**
     * 通过更改类型获得对应的更改批次ID
     * @param type 更改类型
     * @return List<String>
     */
    List<String> getChangeBatchIdsByType(String type);

}
