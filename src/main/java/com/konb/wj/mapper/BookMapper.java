package com.konb.wj.mapper;

import com.konb.wj.pojo.Book;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author konb
 * @since 2021-02-24
 */
@Mapper
public interface BookMapper extends BaseMapper<Book> {

}
