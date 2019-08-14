const del = require('del')
const webpack = require('webpack')
const webConfig = require('./webpack.web.base.conf')
const config = require('../config')
const BabiliWebpackPlugin = require('babili-webpack-plugin')
const CopyWebpackPlugin = require('copy-webpack-plugin')
const path = require('path')


process.env.BASE_API = config.build.env.BASE_API
webConfig.devtool = config.build.devtool

webConfig.plugins.push(
  new BabiliWebpackPlugin(),
  new CopyWebpackPlugin([{
    from: path.join(__dirname, '../static'),
    to: path.join(__dirname, '../dist/web/static'),
    ignore: ['.*']
  }]),
  new webpack.DefinePlugin({
    'process.env.NODE_ENV': config.build.env.NODE_ENV
  }),
  new webpack.LoaderOptionsPlugin({
    minimize: true
  })
)

del.sync(['dist/web/*', '!.gitkeep'])
webConfig.mode = config.dev.env.NODE_ENV
webpack(webConfig, (err, stats) => {
  if (err || stats.hasErrors()) console.log(err)

  console.log(stats.toString({
    chunks: false,
    colors: true
  }))

  process.exit()
})