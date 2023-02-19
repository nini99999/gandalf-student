import BaseApi from './BaseApi'
import config from '../config'

class WebsocketApi extends BaseApi {
    constructor() {
        super();
        this.socket = new WebSocket(config.websocket);
        this.socket.onopen = this.onOpen.bind(this);
        this.socket.onmessage = this.onMessage.bind(this);
        this.socket.onclose = this.onClose.bind(this);
        this.socket.onerror = this.onError.bind(this);
        this.listeners = {}
    }

    onOpen() {
        console.info('onOpen....');
        setInterval(() => {
            this.socket.send('ping');
        }, 30000)
    }

    onMessage(e) {
        console.info('onMessage....', e.data);
        let i = e.data.indexOf('-{');
        if (i > 0) {
            let type = e.data.substr(0, i);
            let data = JSON.parse(e.data.substr(i + 1));
            if (this.listeners[type]) {
                this.listeners[type](data);
            }
        }
    }

    onClose() {
        console.info('onClose....');
    }

    onError(e) {
        console.info('onError....', e);
    }

    addListener(event, callback) {
        this.listeners[event] = callback;
    }
}

export default new WebsocketApi();
