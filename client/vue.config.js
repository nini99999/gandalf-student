module.exports = {
    publicPath: './',
    css: {
        loaderOptions: {
            sass: {
                data: `@import "@/view/style/config.scss";`
            }
        }
    },
    devServer: {
        proxy: {
            '/api': {
                target: 'https://127.0.0.1:8443/school',
                changeOrigin: true,
                pathRewrite: {
                    '^/api': ''
                }
            }
        }
    }
}