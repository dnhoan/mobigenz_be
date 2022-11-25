package com.api.mobigenz_be.controllers;

import com.api.mobigenz_be.constants.Constant;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constant.Api.Path.PUBLIC)
@CrossOrigin("*")
public class CommonController {

}
