package com.fewok.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.resource.ResourceUrlProvider;

/**
 * @author notreami on 18/7/3.
 */
@ControllerAdvice
public class ResourceUrlController {

    @Autowired
    private ResourceUrlProvider resourceUrlProvider;

    /**
     * js、css增加md5
     * @return
     */
    @ModelAttribute("urls")
    public ResourceUrlProvider urls() {
        return this.resourceUrlProvider;
    }
}
