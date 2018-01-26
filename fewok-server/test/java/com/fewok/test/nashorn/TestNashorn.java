package com.fewok.test.nashorn;

import jdk.nashorn.api.scripting.NashornScriptEngineFactory;
import jdk.nashorn.api.scripting.ScriptObjectMirror;

import javax.script.*;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * nashorn
 *
 * @author notreami on 18/1/24.
 */
public class TestNashorn {
    public static void main(String[] args) throws Exception {
//        ScriptEngineManager factory = new ScriptEngineManager();
//        ScriptEngine engine = factory.getEngineByName("nashorn");

        ScriptEngine engine = new NashornScriptEngineFactory().getScriptEngine("-ot=true -strict -fv -doe=false");

        //重新加载js
        engine.eval("print('Hello World!');");


        engine.eval(new FileReader("fewok-server/test/resources/static/script.js"));

        Invocable invocable = (Invocable) engine;

        Object result = invocable.invokeFunction("fun1", "Peter Parker");
        System.out.println(result);
        System.out.println(result.getClass());

        invocable.invokeFunction("fun2", LocalDateTime.now());


        Bindings bindings = new SimpleBindings();
        ScriptContext sc = new SimpleScriptContext();
//
//        sc.setBindings(nashornEngine.createBindings(), ScriptContext.ENGINE_SCOPE);
//        sc.setAttribute("consoleLogInfo", consoleLogInfo, ScriptContext.ENGINE_SCOPE);
//        sc.setAttribute("consoleLogError", consoleLogError, ScriptContext.ENGINE_SCOPE);
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
}
