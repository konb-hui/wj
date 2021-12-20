package com.konb.wj.excel.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelAnalysisStopException;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.konb.wj.common.constant.ChangeBatchConstant;
import com.konb.wj.pojo.ChangeBatch;
import com.konb.wj.pojo.Notice;
import com.konb.wj.utils.DateUtils;
import lombok.SneakyThrows;

import java.util.*;

/**
 * @author konb
 */
public class ExcelDataListener extends AnalysisEventListener<Map<Integer, String>> {

    public ExcelDataListener(List<ChangeBatch> list) {
        this.list = list;
        notice = new Notice();
    }

    public Notice getNotice() {
        return notice;
    }

    /**
     * 确认当前行是不是第一行
     */
    private boolean firstFlag = true;

    private boolean secondFlag = false;

    private Integer changeKey = 0;

    private final static String CHANGE = "更改后";

    private final static Integer TEAM_KEY = 10;

    private final static Integer BATCH_NUM_KEY = 5;

    /**
     * 保存临时数据
     */
    List<ChangeBatch> list;

    /**
     * 保存头
     */
    StringJoiner head = new StringJoiner("@@");

    /**
     * 上一条记录
     */
    Map<Integer, String> previousMap = new HashMap<>();

    /**
     * 保存通知单信息
     */
    Notice notice;

    /**
     * Excel文件头部行数计数
     */
    Integer headNum = 0;

    boolean isCorrectRow = true;

    Integer correctSize = 0;

    Map<String, Integer> changeTypeLocation = new HashMap<>(5);

    @Override
    public void invoke(Map<Integer, String> data, AnalysisContext context) {

        if (firstFlag) {
            if (secondFlag) {
                firstFlag = false;
                secondFlag = false;
                head.add(CHANGE);
                for (Integer i = changeKey; i < data.size(); i ++) {
                    if (StringUtils.isEmpty(data.get(i))) {
                        break;
                    }
                    String column = data.get(i).trim().replaceAll("[\r\n]", "");
                    String type = getType(column);
                    if (type != null && !ChangeBatchConstant.ORDER_FINISH_TIME.equals(type)) {
                        this.changeTypeLocation.put(type, i);
                    } else if (ChangeBatchConstant.ORDER_FINISH_TIME.equals(type)) {
                        this.changeTypeLocation.put(ChangeBatchConstant.AFTER_CHANGE_ORDER_FINISH_TIME, i);
                    }
                    head.add(column);
                }
                notice.setTemplate(head.toString());
            } else {
                for (Map.Entry<Integer, String> entry : data.entrySet()) {
                    if (CHANGE.equals(entry.getValue())) {
                        changeKey = entry.getKey();
                        secondFlag = true;
                        break;
                    }
                    if (! StringUtils.isEmpty(entry.getValue())) {
                        String column = entry.getValue().trim().replaceAll("[\r\n]", "");
                        if (ChangeBatchConstant.ORDER_FINISH_TIME.equals(getType(column))) {
                            this.changeTypeLocation.put(ChangeBatchConstant.BEFORE_CHANGE_ORDER_FINISH_TIME, entry.getKey());
                        }
                        head.add(column);
                    }
                }
            }

        } else {
            //当前行非正常格子数量
            if (! isCorrectRow) {
                return;
            }
            if (correctSize != 0 && data.size() != correctSize) {
                return;
            }
            if (StringUtils.isEmpty(data.get(TEAM_KEY)) &&
                    ! StringUtils.isEmpty(data.get(changeKey)) &&
                    ! StringUtils.isEmpty(previousMap.get(TEAM_KEY))) {
                mergeTwoMap(previousMap, data);
            }
            if (! StringUtils.isEmpty(data.get(TEAM_KEY))) {
                if (correctSize == 0) {
                    correctSize = data.size();
                }
                mapToChangeBatch(this.list, data, data.get(TEAM_KEY), data.get(BATCH_NUM_KEY));
            }
            previousMap = data;
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        firstFlag = true;
        this.changeTypeLocation.clear();
        headNum = 0;
        isCorrectRow = true;
        correctSize = 0;
    }

    @SneakyThrows
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        headNum ++;
        if (headNum == 2) {
            String[] heads;
            try {
                heads = headMap.get(1).split(" ");
            } catch (NullPointerException e) {
                throw new ExcelAnalysisStopException();
            }

                this.notice.setNoticeTime(DateUtils.stringToDate(heads[heads.length - 1], "yyyy-MM-dd"));
        }
        super.invokeHeadMap(headMap, context);
    }

    private void mergeTwoMap(Map<Integer, String> previousMap, Map<Integer, String> data) {
        for (int i = 0; i < changeKey; i ++) {
            data.put(i, previousMap.get(i));
        }
        data.put(BATCH_NUM_KEY, data.get(changeKey));
        //若是拆分批次，则在-1位置上插入SPLIT_BATCH
        data.put(-1, ChangeBatchConstant.SPLIT_BATCH_TYPE);
    }

    private void mapToChangeBatch(List<ChangeBatch> list, Map<Integer, String> data, String team, String batch) {
        ChangeBatch changeBatch = new ChangeBatch();
        changeBatch.setBatch(batch);
        changeBatch.setTeam(team);
        StringJoiner record = new StringJoiner("@@");
        for (Map.Entry<Integer, String> entry : data.entrySet()) {
            if (! StringUtils.isEmpty(entry.getValue())) {
                record.add(entry.getValue());
            }
        }
        changeBatch.setRecord(record.toString());
        this.setChangeBatchType(data, changeBatch);
        list.add(changeBatch);
    }

    /**
     * 通过当前通知单判断其可能存在的修改类型
     * @param content head没个格子的内容
     * @return 修改类型
     */
    private String getType(String content) {
        switch (content) {
            case ChangeBatchConstant.OC_CHANGE :
                return ChangeBatchConstant.CHANGE_OC_TYPE;
            case ChangeBatchConstant.PANEL_CHANGE :
                return ChangeBatchConstant.CHANGE_PANEL_TYPE;
            case ChangeBatchConstant.TCON_CHANGE:
                return ChangeBatchConstant.CHANGE_TCON_TYPE;
            case ChangeBatchConstant.ORDER_FINISH_TIME :
                return ChangeBatchConstant.ORDER_FINISH_TIME;
            default:
                return null;
        }
    }

    private void setChangeBatchType(Map<Integer, String> data, ChangeBatch changeBatch) {
        List<String> typeList = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : changeTypeLocation.entrySet()) {
            //存在修改前的时间，且更改后的时间不为 不变
            if (ChangeBatchConstant.BEFORE_CHANGE_ORDER_FINISH_TIME.equals(entry.getKey()) &&
             ! ChangeBatchConstant.NO_CHANGE.equals(data.get(changeTypeLocation.get(ChangeBatchConstant.AFTER_CHANGE_ORDER_FINISH_TIME)))) {

                int days;
                try {
                    //获取更改前后日期相隔天数
                    days = DateUtils
                            .getTwoDayDifference(
                                    DateUtils.stringToDate(data.get(changeTypeLocation.get(ChangeBatchConstant.BEFORE_CHANGE_ORDER_FINISH_TIME)),
                                            ChangeBatchConstant.CHANGE_DATE_FORMAT),
                                    DateUtils.stringToDate(data.get(changeTypeLocation.get(ChangeBatchConstant.AFTER_CHANGE_ORDER_FINISH_TIME)),
                                            ChangeBatchConstant.CHANGE_DATE_FORMAT));
                } catch (Exception e) {
                    throw new ExcelAnalysisStopException();
                }

                if (days > 0) {
                    typeList.add(ChangeBatchConstant.DELAY_DELIVERY_TYPE);
                } else {
                    typeList.add(ChangeBatchConstant.EARLY_DELIVERY_TYPE);
                    days = days * -1;
                }
                changeBatch.setDay(days);
            }
            //其他修改类型若存在且内容不为 不变，则继续添加该类型
            else if (! ChangeBatchConstant.NO_CHANGE.equals(data.get(entry.getValue())) &&
            ! ChangeBatchConstant.AFTER_CHANGE_ORDER_FINISH_TIME.equals(entry.getKey()) && ! ChangeBatchConstant.BEFORE_CHANGE_ORDER_FINISH_TIME.equals(entry.getKey())) {
                typeList.add(entry.getKey());
            }
        }
        //如果有更改屏参，则记录
        Integer panelLocation = changeTypeLocation.get(ChangeBatchConstant.CHANGE_PANEL_TYPE);
        if (panelLocation != null) {
            String panel = data.get(panelLocation);
            if (! ChangeBatchConstant.NO_CHANGE.equals(panel)) {
                changeBatch.setChangePanel(panel);
            }
        }

        //判断是否为拆分批次
        if (data.get(-1) != null) {
            typeList.add(ChangeBatchConstant.SPLIT_BATCH_TYPE);
        }

        changeBatch.setTypeList(typeList);
    }

}
