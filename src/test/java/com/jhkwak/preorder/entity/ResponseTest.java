package com.jhkwak.preorder.entity;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ResponseTest {

    private ResponseCode responseCode;

    @Test
    void responseTest(){
        Response response = new Response(responseCode.USER_ALREADY_EXIST);
        System.out.println(response.isSuccess());
        System.out.println(response.getStateCode());
        System.out.println(response.getDescription());
    }

}