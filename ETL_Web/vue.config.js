'use strict'
const path = require('path');


function resolve(dir) {
	//此处使用path.resolve 或path.join 可自行调整
	return path.join(__dirname, dir)
}


const name = process.env.VUE_APP_TITLE || 'vue2Admin' // page title
const port = process.env.VUE_APP_PORT || 1333 // dev port

module.exports = {
	publicPath: "./",
	outputDir: "dist",
	assetsDir: "static",
	// 指定生成的 index.html 的输出路径 (相对于 outputDir)。也可以是一个绝对路径。
	indexPath: 'index.html',
	// 默认在生成的静态资源文件名中包含hash以控制缓存
	filenameHashing: true,

	lintOnSave: process.env.NODE_ENV === 'development',
	productionSourceMap: false,
	devServer: {
		hot: false,
		host: 'localhost',
		port: port,
		hotOnly: false,
		https: false,
		open: false,
		overlay: {
			warnings: false,
			errors: true
		},
		proxy: {
			[process.env.VUE_APP_BASE_API]: {
				target: process.env.VUE_API_DOMAIN,
				pathRewrite: {
					['^' + process.env.VUE_APP_BASE_API]: '/'
				}
			}
		}
	},
	configureWebpack: {
		// provide the app's title in webpack's name field, so that
		// it can be accessed in index.html to inject the correct title.
		name: name,
		resolve: {
			alias: {
				'@': resolve('src')
			}
		}
	},
	chainWebpack(config) {
		// it can improve the speed of the first screen, it is recommended to turn on preload
		// it can improve the speed of the first screen, it is recommended to turn on preload
		config.plugin('preload').tap(() => [{
			rel: 'preload',
			// to ignore runtime.js
			// https://github.com/vuejs/vue-cli/blob/dev/packages/@vue/cli-service/lib/config/app.js#L171
			fileBlacklist: [/\.map$/, /hot-update\.js$/, /runtime\..*\.js$/],
			include: 'initial'
		}])

		// when there are many pages, it will cause too many meaningless requests
		config.plugins.delete('prefetch')

		// set svg-sprite-loader
		config.module
			.rule('svg')
			.exclude.add(resolve('src/icons'))
			.end()
		config.module
			.rule('icons')
			.test(/\.svg$/)
			.include.add(resolve('src/icons'))
			.end()
			.use('svg-sprite-loader')
			.loader('svg-sprite-loader')
			.options({
				symbolId: 'icon-[name]'
			})
			.end()

		config
			.when(process.env.NODE_ENV !== 'development',
				config => {
					config
						.plugin('ScriptExtHtmlWebpackPlugin')
						.after('html')
						.use('script-ext-html-webpack-plugin', [{
							// `runtime` must same as runtimeChunk name. default is `runtime`
							inline: /runtime\..*\.js$/
						}])
						.end()
					config
						.optimization.splitChunks({
							chunks: 'all',
							cacheGroups: {
								libs: {
									name: 'chunk-libs',
									test: /[\\/]node_modules[\\/]/,
									priority: 10,
									chunks: 'initial' // only package third parties that are initially dependent
								},
								elementUI: {
									name: 'chunk-elementUI', // split elementUI into a single package
									priority: 20, // the weight needs to be larger than libs and app or it will be packaged into libs or app
									test: /[\\/]node_modules[\\/]_?element-ui(.*)/ // in order to adapt to cnpm
								},
								commons: {
									name: 'chunk-commons',
									test: resolve('src/components'), // can customize your rules
									minChunks: 3, //  minimum common number
									priority: 5,
									reuseExistingChunk: true
								}
							}
						})
					// https:// webpack.js.org/configuration/optimization/#optimizationruntimechunk
					config.optimization.runtimeChunk('single')
				}
			)
	}
}