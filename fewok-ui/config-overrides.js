const tsImportPluginFactory = require('ts-import-plugin');
const {getLoader} = require("react-app-rewired");
const rewireLess = require('react-app-rewire-less');
const rewireReactHotLoader = require('react-app-rewire-hot-loader');

module.exports = function override(config, env) {
    //antd添加到typescript配置里
    const tsLoader = getLoader(
        config.module.rules,
        rule =>
            rule.loader &&
            typeof rule.loader === 'string' &&
            rule.loader.includes('ts-loader')
    );

    tsLoader.options = {
        getCustomTransformers: () => ({
            before: [tsImportPluginFactory({
                libraryName: 'antd',
                libraryDirectory: 'es',
                style: true,
            })]
        })
    };

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