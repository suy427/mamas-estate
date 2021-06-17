package com.sondahum.mamas.manager.adaptor.in;

import com.sondahum.mamas.manager.application.port.in.FindManager;
import com.sondahum.mamas.security.SpringSecurities;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/account")
@RequiredArgsConstructor
public class SignInController {
    private final FindManager findManager;

    @GetMapping("/signin")
    public String view() {
        return SpringSecurities.authenticated() ? "redirect:/" : "contents/manager/login";
    }
}
