import Http from '../common/http'
import config from '../config'

const HEADERS = {};

export default class BaseApi {
    constructor() {
        this.domain = config.apiDomain;
        this.http = new Http();
    }

    static installAuthorization(token) {
        HEADERS['Authorization'] = token
    }

    static toast(message) {
    }

    preRequest({query, body, headers}) {
        headers = Object.assign({}, HEADERS, headers);
        return {query, body, headers}
    }

    postRequest({headers, body}) {
        let {status, msg, data, error, message} = body;
        if (error) {
            throw new Error(`[${status}]${error}: ${message}`)
        }
        if (status === '0000') {
            return data
        } else {
            throw new Error(status + ':' + msg)
        }
    }

    handlerError(err, errorMessage) {
        console.error(err.message);
        if (errorMessage) {
            console.error(errorMessage);
        }
        BaseApi.toast(err.message || errorMessage);
    }

    async request(method, url, options, errorMessage, download = false) {
        if (!/^https?/.test(url)) {
            url = this.domain + url
        }
        options = this.preRequest(options);
        try {
            let response = await this.http.request(method, url, options, download);
            if (download && response.status === 200) {
                let file = response.headers['content-disposition'].split('=')[1];
                const blob = new Blob([response.body]);
                const link = document.createElement('a');
                link.download = file;
                link.style.display = 'none';
                link.href = URL.createObjectURL(blob);
                document.body.appendChild(link);
                link.click();
                URL.revokeObjectURL(link.href);
                document.body.removeChild(link);
            } else {
                return this.postRequest(response)
            }
        } catch (e) {
            this.handlerError(e, errorMessage);
            throw e
        }
    }

    url(api, query = {}, jwt) {
        if (jwt) {
            query['Authorization'] = HEADERS['Authorization']
        }
        let args = Object.keys(query).map(k => `${k}=${query[k]}`).join('&');
        if (args) {
            api += '?' + args
        }
        return this.domain + api
    }

    httpGet(url, query, errorMessage) {
        return this.request('get', url, {query}, errorMessage)
    }

    httpPost(url, body, query, errorMessage, download) {
        return this.request('post', url, {query, body}, errorMessage, download)
    }

    httpPut(url, body, query, errorMessage) {
        return this.request('put', url, {query, body}, errorMessage)
    }

    httpPatch(url, body, query, errorMessage) {
        return this.request('patch', url, {query, body}, errorMessage)
    }

    httpDelete(url, query, errorMessage) {
        return this.request('delete', url, {query}, errorMessage)
    }
}
