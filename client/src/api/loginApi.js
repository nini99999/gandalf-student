import BaseApi from './BaseApi'

class LoginApi extends BaseApi {
    postRequest({headers}) {
        if (headers.authorization) {
            return headers.authorization;
        }
        throw new Error('401')
    }

    login(userName, password) {
        return this.httpPost(`/login`, {userName, password}, {}, '登录失败')
    }
}

export default new LoginApi()