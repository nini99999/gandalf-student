let {host, pathname, protocol} = window.location;
protocol = protocol == 'https:' ? 'wss:' : 'ws:';

export default {
    apiDomain: 'api',
    websocket: `${protocol}//${host}${pathname}/api/websocket`
}