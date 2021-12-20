import request from '@/utils/request'

export default {
    getImage() {
        return request({
            url: '/user/getImage',
            method: 'get'
        })
    },
    getTeams() {
        return request({
            url: '/wj/change-batch/getTeams',
            method: 'get'
        })
    },
    getChangeBatchByTeams(changeBatchQuery) {
        return request({
            url: '/wj/change-batch/getChangeBatchByTeams',
            method: 'post',
            data: changeBatchQuery
        })
    }
}