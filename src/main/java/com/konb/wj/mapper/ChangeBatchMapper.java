package com.konb.wj.mapper;

import com.konb.wj.pojo.ChangeBatch;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.Set;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author konb
 * @since 2021-05-31
 */
public interface ChangeBatchMapper extends BaseMapper<ChangeBatch> {

    Set<String> listAllKindTeams();

}
