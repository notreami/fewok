package com.fewok.server.config.sys;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.fewok.server.config.filter.BAInterceptor;
import com.google.common.collect.Lists;
//import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
//import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author notreami on 16/8/7.
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    private static final FastJsonHttpMessageConverter FAST_JSON_HTTP_MESSAGE_CONVERTER;

    static {
        //定义一个转换消息的对象
        FAST_JSON_HTTP_MESSAGE_CONVERTER = new FastJsonHttpMessageConverter();
        //添加fastjson的配置信息
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(
                SerializerFeature.QuoteFieldNames,//输出key时使用双引号
//                SerializerFeature.UseSingleQuotes,//输出key时使用单引号
                SerializerFeature.WriteMapNullValue,//Map值为null也输出
//                SerializerFeature.WriteEnumUsingToString,//用枚举toString()值输出
                SerializerFeature.WriteEnumUsingName,//用枚举name()输出
//                SerializerFeature.UseISO8601DateFormat,//Date使用ISO8601格式输出
                SerializerFeature.WriteNullListAsEmpty,//List字段如果为null,输出为[],而非null
                SerializerFeature.WriteNullStringAsEmpty,//字符类型字段如果为null,输出为”“,而非null
//                SerializerFeature.WriteNullNumberAsZero,//数值字段如果为null,输出为0,而非null
//                SerializerFeature.WriteNullBooleanAsFalse,//Boolean字段如果为null,输出为false,而非null
//                SerializerFeature.SkipTransientField,//类中的Get方法对应的Field是transient，序列化时将会被忽略
//                SerializerFeature.SortField,//按字段名称排序后输出
//                SerializerFeature.PrettyFormat,//结果格式化
//                SerializerFeature.WriteClassName,//序列化时写入类型信息
                SerializerFeature.DisableCircularReferenceDetect, //消除对同一对象循环引用的问题
                SerializerFeature.WriteSlashAsSpecial,//对斜杠’/’进行转义
//                SerializerFeature.BrowserCompatible,//将中文都会序列化，字节数会多一些，但是能兼容IE 6
                SerializerFeature.WriteDateUseDateFormat,//全局修改日期格式  yyyy-MM-dd HH:mm:ss
                SerializerFeature.NotWriteRootClassName,//
//                SerializerFeature.BeanToArray,//将对象转为array输出
//                SerializerFeature.WriteNonStringKeyAsString,//将非字符串类型的key当成字符串来处理
//                SerializerFeature.NotWriteDefaultValue,//不输出缺省值
                SerializerFeature.BrowserSecure,//防御 xss 安全攻击
                SerializerFeature.IgnoreNonFieldGetter,//忽略没有getter的字段
//                SerializerFeature.WriteNonStringValueAsString,//非字符串类型的值输出为字符串
                SerializerFeature.IgnoreErrorGetter,//忽略报错的getter方法
                SerializerFeature.WriteBigDecimalAsPlain//BigDecimal 序列化 消除科学计数
//                SerializerFeature.MapSortField//Map的序列化后排序再输出
        );
        List<MediaType> supportedMediaTypes = Lists.newArrayList();
        supportedMediaTypes.add(MediaType.APPLICATION_JSON);
        supportedMediaTypes.add(MediaType.valueOf("application/*+json"));


        FAST_JSON_HTTP_MESSAGE_CONVERTER.setSupportedMediaTypes(supportedMediaTypes);
        FAST_JSON_HTTP_MESSAGE_CONVERTER.setDefaultCharset(StandardCharsets.UTF_8);
        //在转换器中添加配置信息
        FAST_JSON_HTTP_MESSAGE_CONVERTER.setFastJsonConfig(fastJsonConfig);
    }

    /**
     * 请求字符编码
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean characterEncodingFilterRegistration() {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);

        FilterRegistrationBean reg = new FilterRegistrationBean();
        reg.setFilter(filter);
        reg.addUrlPatterns("/*");
        return reg;
    }

    /**
     * 响应字符编码
     *
     * @return
     */
    private StringHttpMessageConverter customStringHttpMessageConverter() {
        //添加application/json;charset=UTF-8 编码，默认是text/html 编码
        List<MediaType> supportedMediaTypes = Lists.newArrayList();
        supportedMediaTypes.add(MediaType.APPLICATION_JSON);
        supportedMediaTypes.add(MediaType.TEXT_PLAIN);
        supportedMediaTypes.add(MediaType.ALL);

        StringHttpMessageConverter stringConverter = new StringHttpMessageConverter();
        stringConverter.setDefaultCharset(StandardCharsets.UTF_8);
        stringConverter.setSupportedMediaTypes(supportedMediaTypes);
        stringConverter.setWriteAcceptCharset(false);
        return stringConverter;
    }

    /**
     * 扩展现有的消息转换器链表,会被系统后续添加的消息转换器替换
     *
     * @param converters
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        super.configureMessageConverters(converters);
    }

    /**
     * 扩展现有的消息转换器链表,会替换后续系统添加的消息转换器替换
     *
     * @param converters
     */
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        List<HttpMessageConverter<?>> httpMessageConverters = Lists.newArrayList();
        for (HttpMessageConverter<?> httpMessageConverter : converters) {
            if (httpMessageConverter instanceof StringHttpMessageConverter ||
                    httpMessageConverter instanceof MappingJackson2HttpMessageConverter) {
                continue;
            }
            httpMessageConverters.add(httpMessageConverter);
        }
        httpMessageConverters.add(customStringHttpMessageConverter());
        httpMessageConverters.add(FAST_JSON_HTTP_MESSAGE_CONVERTER);//使用FastJson

        converters.clear();
        converters.addAll(httpMessageConverters);
    }

    /**
     * 同源策略-CORS跨域
     *
     * @return
     */
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*"); // 允许所有的外域发起跨域请求
        corsConfiguration.addAllowedHeader("Origin, X-Requested-With, Content-Type, Accept, Location, Authorization, userId, token"); // 允许跨域请求包含的头信息
        corsConfiguration.addAllowedMethod("GET, POST, PUT, OPTIONS, DELETE"); // 允许外域发起请求HTTP Method
        corsConfiguration.setMaxAge(3600L);
        corsConfiguration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(source);
    }

    /**
     * BA认证
     *
     * @return
     */
//    @Bean
//    public BAInterceptor baFilter() {
//        return new BAInterceptor();
//    }

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(baFilter());
//    }


//    @Bean
//    public EmbeddedServletContainerCustomizer containerCustomizer() {
//        return (container -> {
//            ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "/errors/401.html");
//            ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/errors/404.html");
//            ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/errors/500.html");
//            container.addErrorPages(error401Page, error404Page, error500Page);
//        });
//    }

    /**
     * 替换默认线程池
     *
     * @return
     */
    @Bean
    public Executor customExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(8);//核心线程数，默认为1
        executor.setMaxPoolSize(100);//最大线程数，默认为Integer.MAX_VALUE
        executor.setQueueCapacity(1000);//队列最大长度，一般需要设置值>=notifyScheduledMainExecutor.maxNum；默认为Integer.MAX_VALUE
        executor.setKeepAliveSeconds(60);//线程池维护线程所允许的空闲时间，默认为60s
        executor.setThreadNamePrefix("taskExecutor-");

        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
        //线程池对拒绝任务（无线程可用）的处理策略，目前只支持AbortPolicy、CallerRunsPolicy；默认为后者
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }

}
