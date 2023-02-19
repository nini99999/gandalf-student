# PC前端说明

## 本地开发模式
* 安装node环境，版本>10
* 切换到本目录，执行npm install安装依赖
* 执行npm run serve启动本地调试


## 生产部署
* 执行npm run build
* 将生成的dist内容上传到服务器，由nginx挂载
* 配置nginx，将后端api包括websocket代理到同一个域名路径的api下


#### nginx配置示例:
```nginx
    location /school/api {
      proxy_pass https://localhost:8443/school;
    }

    location /school/api/websocket {
      proxy_http_version 1.1;
      proxy_set_header Upgrade $http_upgrade;
      proxy_set_header Connection "upgrade";
      proxy_pass https://localhost:8443/school/websocket;
    }

    location /school {
        alias /opt/gandalf-client;
    }
```