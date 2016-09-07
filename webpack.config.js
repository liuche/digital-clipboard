'use strict';
const webpack = require('webpack');
const WebpackShellPlugin = require('webpack-shell-plugin');


module.exports = {
  entry: ['babel-polyfill', './js/index.js'],
  output: {
    path: './app/src/main/res/raw',
    filename: 'extract.js'
  },

  module: {
    loaders: [{
      test: /\.js$/,
      loader: 'babel',
      include: /js/,
      query: {
        presets: ['es2015'],
      }
    }]
  },

  plugins: [
    new WebpackShellPlugin({
      // Hack: delete babelPolyfill check in extract.js
      onBuildExit: ['sed -i "" -e "/if (global._babelPolyfill)/{N;N;d;}" ./app/src/main/res/raw/extract.js' ]
    })
  ]
}
