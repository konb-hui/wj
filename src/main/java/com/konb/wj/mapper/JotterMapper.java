package com.konb.wj.mapper;

import com.konb.wj.pojo.Jotter;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author konb
 * @since 2021-02-25
 */
@Mapper
public interface JotterMapper extends BaseMapper<Jotter> {
    void recoverJotter(String id);

    List<Jotter> getDeletedJotter();
}
