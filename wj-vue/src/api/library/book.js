import request from '@/utils/request'

export default {
    //获取图书类型
    list(tid, current, size, key) {
        return request({
            url: `/book/list/${tid}/${current}/${size}`,
            method: 'post',
            data: {
                key
            }
        })
    },
    saveOrUpdate(book) {
        return request({
            url: `/book/addOrUpdate`,
            method: 'post',
            data: book
        })
    }
}