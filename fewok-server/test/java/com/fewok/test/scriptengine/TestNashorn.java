package com.fewok.test.scriptengine;

import jdk.nashorn.api.scripting.NashornScriptEngineFactory;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import org.testng.annotations.Test;

import javax.script.*;
import java.io.FileReader;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;
import java.util.concurrent.ScheduledExecutorService;

/**
 * nashorn
 *
 * @author notreami on 18/1/24.
 */
public class TestNashorn {
    private static ScheduledExecutorService globalScheduledThreadPool = Executors.newScheduledThreadPool(20);
    public static void main(String[] args) throws Exception {
//        ScriptEngineManager factory = new ScriptEngineManager();
//        ScriptEngine engine = factory.getEngineByName("nashorn");

        ScriptEngine engine = new NashornScriptEngineFactory().getScriptEngine("-ot=true -strict -fv -doe=false");

        ScriptContext sc = new SimpleScriptContext();
        sc.setBindings(engine.createBindings(), ScriptContext.ENGINE_SCOPE);
        sc.setAttribute("__NASHORN_POLYFILL_TIMER__", globalScheduledThreadPool, ScriptContext.ENGINE_SCOPE);

        engine.setContext(sc);
        //读取脚本程序并运行
//        engine.eval("print('Hello World!');");


//        engine.eval(new FileReader("fewok-server/test/resources/static/script.js"));
        engine.eval(new FileReader("fewok-server/test/resources/static/global-polyfill.js"));
        engine.eval(new FileReader("fewok-server/test/resources/static/timer-polyfill.js"));
        engine.eval(new FileReader("fewok-server/test/resources/static/setTimeout.js"));
//        engine.eval(new FileReader("fewok-server/test/resources/static/clearTimeout.js"));
//        engine.eval(new FileReader("fewok-server/test/resources/static/setInterval.js"));
//        engine.eval(new FileReader("fewok-server/test/resources/static/clearInterval.js"));

//        Invocable invocable = (Invocable) engine;
//
//        //Java 平台调用脚本程序中的函数或方法
//        Object result = invocable.invokeFunction("fun1", "Peter Parker");
//        System.out.println(result);
//        System.out.println(result.getClass());
//
//        invocable.invokeFunction("fun2", LocalDateTime.now());

//        Bindings bindings = new SimpleBindings();


//
//        sc.setBindings(nashornEngine.createBindings(), ScriptContext.ENGINE_SCOPE);
//        sc.setAttribute("consoleLogInfo", consoleLogInfo, ScriptContext.ENGINE_SCOPE);
//        sc.setAttribute("consoleLogError", consoleLogError, ScriptContext.ENGINE_SCOPE);

//        Thread.sleep(5000);
//        globalScheduledThreadPool.shutdownNow();
    }

    /**
     * js调用java
     * @param name
     * @return
     */
    public static String fun3(String name) {
        System.out.format("Hi there from Java, %s", name);
        return "greetings from java";
    }

    /**
     * javascript传递对象至java（js调用java的时候）
     * @param mirror
     */
    public static void fun4(ScriptObjectMirror mirror) {
        System.out.println(mirror.getClassName() + ": " +
                Arrays.toString(mirror.getOwnKeys(true)));
    }

    public static void fun5(ScriptObjectMirror person) {
        System.out.println("Full Name is: " + person.callMember("getFullName"));
    }

    @Test
    public void test_phaser(){
        Phaser phaser=new Phaser();
        phaser.register();
    }
}
