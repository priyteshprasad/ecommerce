package com.priytesh.ecommerce.controller;

import com.priytesh.ecommerce.response.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping
    public ApiResponse HomeControllerHandler(){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Welcome dear ok, to ecommerce multi vendor system ok");
        return apiResponse;
    }
}
