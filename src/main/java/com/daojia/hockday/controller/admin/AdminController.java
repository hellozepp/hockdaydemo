package com.daojia.hockday.controller.admin;

import com.daojia.hockday.controller.ArticleController;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: zhanglin
 * @Date: 2018/11/24
 * @Time: 5:58 PM
 */
@RestController
public class AdminController {
    private static final Logger logger = LoggerFactory.getLogger(ArticleController.class);

    /**
     * 1:可用 2:不可用
     *
     * @param articleContent
     * @return
     */
    @GetMapping(value = "/admin/groupContent")
    public Integer groupContent(String articleContent) {
        if (StringUtils.isBlank(articleContent)){
            return 2;
        }
        return 1;
    }
}
