var path = require('path');
const CopyPlugin = require('copy-webpack-plugin');
module.exports = {
    entry: './src/frontend/main/app.js',
    devtool: 'sourcemaps',
    cache: true,
    mode: 'development',
    output: {
        path: __dirname,
        filename: './target/classes/frontend/built/bundle.js'
    },
    module: {
        rules: [
            {
                test: path.join(__dirname, '.'),
                exclude: /(node_modules)/,
                use: [{
                    loader: 'babel-loader',
                    options: {
                        presets: ["@babel/preset-env", "@babel/preset-react"]
                    }
                }]
            }


        ]
    },
    plugins: [
        new CopyPlugin([
            { from: 'src/frontend/index.html', to: 'target/classes/frontend' },
            { from: 'src/frontend/style/main.css', to: 'target/classes/frontend/style' },
        ]),
    ]

};