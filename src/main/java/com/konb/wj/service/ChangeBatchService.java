package com.konb.wj.service;

import com.konb.wj.pojo.ChangeBatch;
import com.baomidou.mybatisplus.extension.service.IService;
import com.konb.wj.pojo.Notice;
import com.konb.wj.pojo.vo.ChangeBatchQuery;
import com.konb.wj.pojo.vo.NoticeVo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author konb
 * @since 2021-05-31
 */
public interface ChangeBatchService extends IService<ChangeBatch> {

    /**
     * 保存批次修改记录
     * @param nid 通知单ID
     * @param list 要保存的记录
     */
    void saveChangeBatches(String nid, List<ChangeBatch> list);

    /**
     * 获得更改记录的所有机芯种类
     * @return Set<String>
     */
    Set<String> listAllKindTeams();

    /**
     * 修改通知单更改记录的格式
     * @param changeBatchList 更改记录LIST
     * @param changeBatchQuery 条件
     * @return Map<String, List<NoticeVo>>
     */
    Map<String, List<NoticeVo>> changeFormatOfResult(List<ChangeBatch> changeBatchList, ChangeBatchQuery changeBatchQuery);

}
