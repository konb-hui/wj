package com.konb.wj.common.constant;

/**
 * @author konb
 */
public interface ChangeBatchConstant {

    //通知单中的固定字段

    public static final String OC_CHANGE = "OC/规格型号";

    public static final String TCON_CHANGE = "TCON芯片方案";

    public static final String PANEL_CHANGE = "屏/规格型号";

    public static final String ORDER_FINISH_TIME = "订单完成生产时间";


    //通知单特定字符

    public static final String CHANGE_DATE_FORMAT = "MM月dd日";

    public static final String NO_CHANGE = "不变";

    public static final String BEFORE_CHANGE_ORDER_FINISH_TIME = "修改前订单完成生产时间";

    public static final String AFTER_CHANGE_ORDER_FINISH_TIME = "修改后订单完成生产时间";

    //通知单更改记录类型

    public static final String CHANGE_PANEL_TYPE = "更改屏参";

    public static final String CHANGE_OC_TYPE = "更改OC";

    public static final String CHANGE_TCON_TYPE = "更改TCON";

    public static final String SPLIT_BATCH_TYPE = "拆分批次";

    public static final String EARLY_DELIVERY_TYPE = "交期提前";

    public static final String DELAY_DELIVERY_TYPE = "交期延后";

    public static final String CANCEL_ORDER_TYPE = "取消订单";

}
