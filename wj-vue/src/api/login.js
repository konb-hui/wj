import request from '@/utils/request'

export default {
    //登录
    login(user) {
        return request({
            url: '/user/login',
            method: 'post',
            data: user
        })
    }
}