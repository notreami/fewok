package com.fewok.test.general;

import com.fewok.test.AbstractTestNG;
import feign.Feign;
import feign.Request;
import feign.RequestLine;
import feign.Retryer;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;
import org.testng.annotations.Test;

/**
 * @author notreami on 17/11/14.
 */
public class TestHttpFeign extends AbstractTestNG {
    interface GitHub {
//        @Body()
//        @HeaderMap
//        @Headers()
//        @Param()
//        @QueryMap
        @RequestLine("GET /t/406958")
        String contributors();
    }

    @Test
    public void test_http() {
        GitHub gitHub = Feign.builder()
//                .requestInterceptor() //默认提供 BasicAuthRequestInterceptor 模版，操作方式是，实现RequestInterceptor模版
                .logLevel(feign.Logger.Level.FULL) //Log Level：NONE、BASIC、HEADERS和FULL，NONE为不打印，FULL为打印headers、body和metadata，BASIC打印request method和url或response状态码和headers，HEADERS打印request或response的基本信息
//                .contract(new Contract.Default())//检查request相关的约定
                .client(new OkHttpClient()) //最终发送request请求以及接收response响应 Client.Default、feign-httpclient、feign-okhttp、feign-ribbon
                .retryer(new Retryer.Default()) //默认的period为100ms，maxPeriod为1000ms，maxAttempts为5次
                .logger(new Slf4jLogger())  //内置三个Logger，分别是JavaLogger、ErrorLogger和NoOpLogger，默认为NoOpLogger
//                .encoder() //在request执行前进行encode操作  有feign-gson、feign-jackson、feign-jaxb、feign-jackson-jaxb和feign-sax
//                .decoder() //收到response时进行decode操作   有feign-gson、feign-jackson、feign-jaxb、feign-jackson-jaxb和feign-sax
//                .errorDecoder()  //用于异常的response
                .options(new Request.Options(10 * 1000, 10 * 1000))
                .decode404()
                .target(GitHub.class, "https://www.v2ex.com");
        String result = gitHub.contributors();
        System.out.println(result);
    }
}



