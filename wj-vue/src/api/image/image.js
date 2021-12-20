import request from '@/utils/request'

export default {
    addImage(file) {
        return request({
            url: '/image/add',
            method: 'post',
            data: file
        })
    },
    getImages(page) {
        return request({
            url: `/image/getImages/${page.size}/${page.current}`,
            method: 'get',
        })
    },
    deleteImage(id) {
        return request({
            url: `/image/deleteImage/${id}`,
            method: 'get',
        })
    },
    downloadImage(id) {
        return request({
            url: `/image/downloadImage/${id}`,
            method: 'get',
            responseType: 'blob'
        })
    }
}