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
                target: 'http://127.0.0.1:8000/school',
                changeOrigin: true,
                pathRewrite: {
                    '^/api': ''
                }
            }
        }
    }
}