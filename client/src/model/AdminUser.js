import BaseApi from "../api/BaseApi"
import loginApi from '../api/loginApi'
import sysApi from '../api/sysApi'
import {mgr} from './permission'

const TOKEN = 'x-token';

class AdminUser {
    async checkOnline() {
        let token = window.sessionStorage.getItem(TOKEN) || window.localStorage.getItem(TOKEN);
        if (token) {
            BaseApi.installAuthorization(token);
            try {
                await this.loadData();
                return true;
            } catch (e) {
                console.error(e);
            }
        }
        return false
    }

    async login(username, password, remember) {
        let token = await loginApi.login(username, password);
        BaseApi.installAuthorization(token);
        window.sessionStorage.setItem(TOKEN, token);
        if (remember) {
            window.localStorage.setItem(TOKEN, token);
        }
        await this.loadData();
    }

    async changePassword(password, oldPassword) {
        await sysApi.changePassword({password, oldPassword});
        this.data.isNew = false;
    }

    quit() {
        window.sessionStorage.removeItem(TOKEN);
        window.localStorage.removeItem(TOKEN);
        delete this.data;
    }

    async loadData() {
        if (!this.data) {
            this.data = await sysApi.getUser();
            // this.data.isNew = false;
            this.data.permissions = mgr.get(this.data.roleMemo);
        }
        return this.data
    }
}

export default new AdminUser()