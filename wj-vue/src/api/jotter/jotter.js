import request from '@/utils/request'

export default {
    //登录
    getFolderTree() {
        return request({
            url: '/directory/getDirectoryTree',
            method: 'get',
        })
    },
    addFolder(directory) {
        return request({
            url: '/directory/add',
            method: 'post',
            data: directory
        })
    },
    getFolder(parent) {
        return request({
            url: `/directory/getDirectoryByParent/${parent}`,
            method: 'get',
        })
    },
    addJotter(jotter) {
        return request({
            url: '/jotter/add',
            method: 'post',
            data: jotter
        })
    },
    getJotter(id) {
        return request({
            url: `/jotter/getJotter/${id}`,
            method: 'get',
        })
    },
    getDirectoryIds(parent) {
        return request({
            url: `/directory/getDirectoryIds/${parent}`,
            method: 'get',
        })
    },
    updateJotter(jotter) {
        return request({
            url: '/jotter/update',
            method: 'post',
            data: jotter
        })
    },
    getNewJotter(jotterVo) {
        return request({
            url: '/jotter/getNewJotter',
            method: 'post',
            data: jotterVo
        })
    },
    deleteJotter(id) {
        return request({
            url: `/jotter/delete/${id}`,
            method: 'get',
        })
    },
    getDeletedJotter() {
        return request({
            url: `/jotter/getDeletedJotter`,
            method: 'get',
        })
    },
    recoverJotter(id) {
        return request({
            url: `/jotter/recoverJotter/${id}`,
            method: 'get',
        })
    }
}