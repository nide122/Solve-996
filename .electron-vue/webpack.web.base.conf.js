'use strict'
const {
  VueLoaderPlugin
} = require('vue-loader')
const MiniCssExtractPlugin = require('mini-css-extract-plugin')
const HtmlWebpackPlugin = require('html-webpack-plugin')
const webpack = require('webpack')
const path = require('path')
const utils = require('./utils')

process.env.BABEL_ENV = 'web'

function resolve(dir) {
  return path.join(__dirname, '..', dir)
}

let webConfig = {
  // devtool: '#cheap-module-eval-source-map',
  entry: {
    web: path.join(__dirname, '../src/renderer/main.js')
  },
  module: {
    rules: [{
        test: /\.scss$/,
        use: ['vue-style-loader', 'css-loader', 'sass-loader']
      },
      {
        test: /\.sass$/,
        use: ['vue-style-loader', 'css-loader', 'sass-loader?indentedSyntax']
      },
      {
        test: /\.less$/,
        use: ['vue-style-loader', 'css-loader', 'less-loader']
      },
      {
        test: /\.css$/,
        use: ['vue-style-loader', 'css-loader']
      },
      {
        test: /\.html$/,
        use: 'vue-html-loader'
      },
      {
        test: /\.js$/,
        use: 'babel-loader',
        include: [path.resolve(__dirname, '../src/renderer')],
        exclude: /node_modules/
      },
      {
        test: /\.vue$/,
        use: {
          loader: 'vue-loader',
          options: {
            extractCSS: true,
            loaders: {
              sass: 'vue-style-loader!css-loader!sass-loader?indentedSyntax=1',
              scss: 'vue-style-loader!css-loader!sass-loader',
              less: 'vue-style-loader!css-loader!less-loader'
            }
          }
        }
      },
      {
        test: /\.(png|jpe?g|gif|svg)(\?.*)?$/,
        loader: 'url-loader',
        exclude: [resolve('src/renderer/icons')],
        options: {
          limit: 10000,
          name: utils.assetsPath('img/[name].[hash:7].[ext]')
        }
      },
      {
        test: /\.svg$/,
        loader: 'svg-sprite-loader',
        include: [resolve('src/renderer/icons')],
        options: {
          symbolId: 'icon-[name]'
        }
      },

      {
        test: /\.(woff2?|eot|ttf|otf)(\?.*)?$/,
        use: {
          loader: 'url-loader',
          query: {
            limit: 10000,
            name: 'fonts/[name].[ext]'
          }
        }
      }
    ]
  },
  plugins: [
    new VueLoaderPlugin(),
    new MiniCssExtractPlugin({
      filename: 'styles.css'
    }),
    // new HtmlWebpackPlugin({
    //   filename: 'index.html',
    //   template: path.resolve(__dirname, '../src/index.ejs'),
    //   minify: {
    //     collapseWhitespace: true,
    //     removeAttributeQuotes: true,
    //     removeComments: true
    //   },
    //   nodeModules: false
    // }),
    new HtmlWebpackPlugin({
      filename: 'index.html',
      template: 'index.html',
      inject: true,
      // favicon: resolve('favicon.ico'),
      title: '骄傲科技',
      minify: {
        removeComments: true,
        collapseWhitespace: true,
        removeAttributeQuotes: true
        // more options:
        // https://github.com/kangax/html-minifier#options-quick-reference
      }
      // default sort mode uses toposort which cannot handle cyclic deps
      // in certain cases, and in webpack 4, chunk order in HTML doesn't
      // matter anyway
    }),
    new webpack.DefinePlugin({
      'process.env.IS_WEB': 'true',
      'process.env.NODE_ENV': JSON.stringify(process.env.NODE_ENV)
    }),
    new webpack.HotModuleReplacementPlugin(),
    new webpack.NoEmitOnErrorsPlugin()
  ],
  output: {
    filename: '[name].[hash].js',
    path: path.join(__dirname, '../dist/web')
  },
  resolve: {
    alias: {
      '@': path.join(__dirname, '../src/renderer'),
      'vue$': 'vue/dist/vue.esm.js'
    },
    extensions: ['.js', '.vue', '.json', '.css']
  },
  target: 'web'
}


module.exports = webConfig