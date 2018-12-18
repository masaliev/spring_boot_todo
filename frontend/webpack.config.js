const path = require('path');
const VueLoaderPlugin = require('vue-loader/lib/plugin');

module.exports = {
  mode: 'development',
  devtool: 'source-map',
  entry: path.join(__dirname, 'src', 'js', 'main.js'),
  devServer: {
    contentBase: './dist',
    compress: true,
    port: 9090,
    allowedHosts: [
      'localhost:9082'
    ]
  },
  module: {
    rules: [
      {
        test: /\.js$/,
        exclude: /(node_modules|bower_components)/,
        use: {
          loader: 'babel-loader',
          options: {
            presets: ['@babel/preset-env']
          }
        }
      },
      {
        test: /\.vue$/,
        loader: 'vue-loader'
      },
      {
	      test: /\.css$/,
	      use: [
		      'vue-style-loader',
		      'css-loader'
	      ]
      }
    ]
  },
  plugins: [
    new VueLoaderPlugin()
  ],
  resolve: {
      modules: [
          path.join(__dirname, 'src', 'js'),
          path.join(__dirname, 'node_modules'),
      ],
	  alias: {
		  vue: 'vue/dist/vue.js'
	  }
  }
}
