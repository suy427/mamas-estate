package com.sondahum.mamas.manager.adaptor.in;

import com.sondahum.mamas.manager.application.port.in.ResendEmail;
import com.sondahum.mamas.manager.application.port.in.VerifyEmail;
import com.sondahum.mamas.manager.domain.Manager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import web.WebAttribute;

@Controller
@RequestMapping("/account/verify")
@RequiredArgsConstructor
public class EmailVerificationController {
    private final ResendEmail resendEmail;
    private final VerifyEmail verifyEmail;

    @GetMapping("/resend")
    public String resend(
            @RequestAttribute(WebAttribute.ACCOUNT_NAME) Manager me,
            Model model
    ) {
        if (!me.emailVerified) {
            resendEmail.send(me);
            model.addAttribute("resendResult", true);
        } else {
            model.addAttribute("resendResult", false);
        }

        return "contents/account/email-resend";
    }

    @GetMapping("/verify")
    public String verify(
            @RequestAttribute(WebAttribute.ACCOUNT_NAME) Manager me,
            @Param("token") String token,
            Model model
    ) {
        try {
            verifyEmail.verify(me, token);
            model.addAttribute("verifyResult", true);
        } catch (Exception e) {
            model.addAttribute("verifyResult", false);
        }

        return "contents/account/email-verification";
    }
}
