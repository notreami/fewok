package com.fewok.test.operation;

import com.fewok.dsl.util.BasicAuth;

/**
 * @author notreami on 17/9/15.
 */
public class TestBasicAuth {
    public static void main(String[] args) throws Exception {
        BasicAuth.Data data = BasicAuth.createBasicAuthData("isearchapiflight", "b8b857333da27e1c5635111f3ad3ec0c", "GET", "/inter/city/all");
        System.out.println("Date: " + data.getDate());
        System.out.println("Authorization：" + data.getAuth());
        boolean result = BasicAuth.checkBasicAuthData(data, "b8b857333da27e1c5635111f3ad3ec0c", "GET", "/inter/city/all");
        System.out.println(result);
    }
}
