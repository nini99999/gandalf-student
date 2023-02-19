import axios from 'axios';

export default class Http {
    async request(method, url, {query, body, headers}, download = false) {
        try {
            let options = {
                method,
                url,
                headers: headers,
                params: query,
                data: body
            };
            if (download) {
                options.responseType = 'blob'
            }
            let response = await axios.request(options);
            return {status: response.status, headers: response.headers, body: response.data}
        } catch ({message, response}) {
            if (response && response.data) {
                return {status: response.status, headers: response.headers, body: response.data}
            } else {
                console.error(`[Error]${message}: ${url}`);
                throw new Error();
            }
        }
    }

    get(url, options) {
        return this.request('get', url, options);
    }

    post(url, options) {
        return this.request('post', url, options);
    }
}
