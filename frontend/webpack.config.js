/* eslint-disable no-undef */

import { fileURLToPath } from 'url';
import path, { dirname } from 'path';
import ForkTsCheckerWebpackPlugin from 'fork-ts-checker-webpack-plugin';
import HTMLWebpackPlugin from 'html-webpack-plugin';
import MiniCssExtractPlugin from 'mini-css-extract-plugin';
import webpack from 'webpack';

const __filename = fileURLToPath(import.meta.url);
const __dirname = dirname(__filename);

let mode = 'development';
if (process.env.NODE_ENV === 'production') {
  mode = 'production';
}
const config = {
  mode,
  entry: './src/index.tsx',
  output: {
    filename: 'bundle.js',
    path: path.resolve(__dirname, 'build'),
    clean: true,
  },
  devServer: {
    hot: true,
    compress: false,
    port: 3000,
    historyApiFallback: true,
  },
  devtool: 'source-map',
  plugins: [
    new webpack.DefinePlugin({
      'process.env.BACKEND_HOST': JSON.stringify(process.env.BACKEND_HOST),
    }),
    new ForkTsCheckerWebpackPlugin(),
    new HTMLWebpackPlugin({
      template: './public/index.html',
      favicon: './public/assets/icons/icon-logo.png',
    }),
    new MiniCssExtractPlugin(),
  ],
  module: {
    rules: [
      {
        test: /\.html$/i,
        loader: 'html-loader',
      },
      {
        test: /\.(ts|js)x?$/,
        exclude: /node_modules/,
        use: {
          loader: 'babel-loader',
          options: {
            presets: ['@babel/preset-env', '@babel/preset-react', '@babel/preset-typescript'],
          },
        },
      },
      {
        test: /\.(png|jpg|jpeg|gif)$/i,
        type: 'asset/resource',
      },
      {
        test: /\.css$/i,
        use: [MiniCssExtractPlugin.loader, 'css-loader'],
      },
    ],
  },
  resolve: {
    extensions: ['.tsx', '.ts', '.js'],
  },
};
export default config;
