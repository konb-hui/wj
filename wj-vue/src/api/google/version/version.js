import request from '@/utils/request'

export default {
    list(query) {
        return request({
            url: '/google/version/list',
            method: 'post',
            data: query
        })
    }
}