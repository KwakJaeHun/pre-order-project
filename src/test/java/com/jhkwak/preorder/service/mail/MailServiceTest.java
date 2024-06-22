package com.jhkwak.preorder.service.mail;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MailServiceTest {
    @Autowired
    MailService mailService;

    @Test
    void sendMailTest(){
        String url = "http://test";
        String text = "<p>다음 링크를 클릭하여 이메일 인증을 완료하세요:</p><p><a href=\"" + url + "\">" + url + "</a></p>";

        mailService.sendEmail("", "test", text);
    }

}