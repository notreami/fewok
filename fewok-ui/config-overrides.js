const {injectBabelPlugin} = require('react-app-rewired');
const rewireLess = require('react-app-rewire-less');
const rewireReactHotLoader = require('react-app-rewire-hot-loader');

module.exports = function override(config, env) {

    //antd添加到配置里
    config = injectBabelPlugin(['import', {libraryName: 'antd', style: true}], config);

    //修改antd主题
    config = rewireLess.withLoaderOptions({
        modifyVars: {"@primary-color": "#1DA57A"},
    })(config, env);


    //根据环境设置
    console.log("ui project environment:" + process.env.NODE_ENV);
    if (process.env.NODE_ENV === 'production') {

    } else {
        //react热加载
        config = rewireReactHotLoader(config, env);
    }
    return config;
};