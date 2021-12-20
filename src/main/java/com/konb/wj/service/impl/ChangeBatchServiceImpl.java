package com.konb.wj.service.impl;

import com.konb.wj.pojo.ChangeBatch;
import com.konb.wj.mapper.ChangeBatchMapper;
import com.konb.wj.pojo.Notice;
import com.konb.wj.pojo.vo.ChangeBatchQuery;
import com.konb.wj.pojo.vo.NoticeVo;
import com.konb.wj.service.ChangeBatchService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.konb.wj.service.ChangeTypeBatchService;
import com.konb.wj.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author konb
 * @since 2021-05-31
 */
@Service
public class ChangeBatchServiceImpl extends ServiceImpl<ChangeBatchMapper, ChangeBatch> implements ChangeBatchService {

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private ChangeTypeBatchService changeTypeBatchService;

    private Map<String, Notice> noticeCache = new HashMap<>();


    @Override
    public void saveChangeBatches(String nid, List<ChangeBatch> list) {
        for (ChangeBatch batch : list) {
            batch.setNid(nid);
        }
        this.saveBatch(list);
        this.changeTypeBatchService.saveBatchChangeBatch(list);
    }

    @Override
    public Set<String> listAllKindTeams() {
        return this.baseMapper.listAllKindTeams();
    }

    @Override
    public Map<String, List<NoticeVo>> changeFormatOfResult(List<ChangeBatch> changeBatchList, ChangeBatchQuery changeBatchQuery) {
        List<String> teams = changeBatchQuery.getTeams();
        Map<String, List<NoticeVo>> result = new HashMap<>();
        for (String team : teams) {
            result.put(team, new ArrayList<>());
        }
        for (ChangeBatch batch : changeBatchList) {
            NoticeVo nVo = isNoticeExist(result.get(batch.getTeam()), batch.getNid());
            if (nVo == null) {
                //为添加通知信息则新增通知单信息
                if (! this.noticeCache.containsKey(batch.getNid())) {
                    this.noticeCache.put(batch.getNid(), this.noticeService.getById(batch.getNid()));
                }
                Notice notice = this.noticeCache.get(batch.getNid());
                if (! (changeBatchQuery.isOnlyPanel() && ! notice.getTemplate().split("更改后")[1].contains("屏/规格型号"))) {
                    List<String> columns = Arrays.asList(batch.getRecord().split("@@"));
                    NoticeVo noticeVo = new NoticeVo();
                    noticeVo.setTemplate(Arrays.asList(notice.getTemplate().split("@@")));
                    noticeVo.setNotice(notice);
                    noticeVo.getChangeData().add(columns);
                    result.get(batch.getTeam()).add(noticeVo);
                }
            } else {
                //已添加通知单信息则直接将数据填入
                List<String> columns = Arrays.asList(batch.getRecord().split("@@"));
                nVo.getChangeData().add(columns);
            }
        }
        return result;
    }

    private NoticeVo isNoticeExist(List<NoticeVo> noticeVoList, String nid) {
        for (NoticeVo noticeVo : noticeVoList) {
            if (nid.equals(noticeVo.getNotice().getId())) {
                return noticeVo;
            }
        }
        return null;
    }
}
