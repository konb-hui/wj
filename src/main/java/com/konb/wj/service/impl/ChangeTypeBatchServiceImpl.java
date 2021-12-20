package com.konb.wj.service.impl;

import com.konb.wj.pojo.ChangeBatch;
import com.konb.wj.pojo.ChangeTypeBatch;
import com.konb.wj.mapper.ChangeTypeBatchMapper;
import com.konb.wj.service.ChangeTypeBatchService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author konb
 * @since 2021-07-09
 */
@Service
public class ChangeTypeBatchServiceImpl extends ServiceImpl<ChangeTypeBatchMapper, ChangeTypeBatch> implements ChangeTypeBatchService {

    @Override
    public void saveBatchChangeBatch(List<ChangeBatch> list) {
        for (ChangeBatch batch : list) {
            List<ChangeTypeBatch> changeTypeBatches = new ArrayList<>();
            for (String type : batch.getTypeList()) {
                ChangeTypeBatch changeTypeBatch = new ChangeTypeBatch();
                changeTypeBatch.setType(type);
                changeTypeBatch.setBid(batch.getId());
                changeTypeBatches.add(changeTypeBatch);
            }
            this.saveBatch(changeTypeBatches);
        }
    }

    @Override
    public List<String> getChangeBatchIdsByType(String type) {
        return this.baseMapper.getChangeBatchIdsByType(type);
    }
}
