import request from '@/utils/request'

export default {
    //获取图书类型
    list() {
        return request({
            url: '/libraryType/list',
            method: 'get'
        })
    }
}