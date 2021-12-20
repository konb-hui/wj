package com.konb.wj.controller;


import com.konb.wj.service.ChangeTypeBatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author konb
 * @since 2021-07-09
 */
@RestController
@RequestMapping("/change-type-batch")
public class ChangeTypeBatchController {

    @Autowired
    private ChangeTypeBatchService changeTypeBatchService;

}

