import request from '@/utils/request'

export default {
    //删除所有通知单及其记录
    deleteAll() {
        return request({
            url: '/notice/deleteAll',
            method: 'delete'
        })
    },
    uploadByPath(noticeVo) {
        return request({
            url: `/notice/uploadByPath`,
            method: 'post',
            data: noticeVo
        })
    },
}