package com.sondahum.mamas.manager.adaptor.in;

import com.google.common.collect.Lists;
import com.sondahum.mamas.manager.application.port.in.CheckDuplicatedManager;
import com.sondahum.mamas.manager.application.port.in.CreateManager;
import com.sondahum.mamas.manager.domain.Manager;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static com.sondahum.mamas.security.SpringSecurities.authenticated;


@Controller
@RequestMapping("/account")
@RequiredArgsConstructor
public class SignUpController {
    private final CreateManager createManager;
    private final CheckDuplicatedManager checkDuplicatedManager;
    private final PasswordEncoder passwordEncoder;

    @InitBinder("signUpForm")
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(new SignUpForm.Validator(checkDuplicatedManager));
    }

    @GetMapping("/sign-up")
    public String view() {
        return authenticated() ? "redirect:/" : "contents/account/sign-up";
    }

    @PostMapping("/sign-up")
    public ModelAndView signUp(@Valid SignUpForm form, Errors errors, Model model) {
        if (errors.hasErrors()) {
            ModelAndView mv = new ModelAndView("contents/manager/sign-up");
            errors.getFieldErrors().forEach(
                    error -> mv.addObject("error_"+error.getField(), error.getDefaultMessage()));
        }

        Manager manager = createManager.create(
                new CreateManager.CreateForm(
                        form.name,
                        passwordEncoder.encode(form.email),
                        form.password)
        );

        SecurityContext ctx = SecurityContextHolder.createEmptyContext();
        ctx.setAuthentication(new UsernamePasswordAuthenticationToken(
                manager.name, manager.password, Lists.newArrayList(new SimpleGrantedAuthority("USER"))
        ));

        SecurityContextHolder.setContext(ctx);

        return new ModelAndView("redirect:/");
    }

    private static final class SignUpForm {
        @NotNull
        @NotBlank
        String name;

        @NotNull
        @NotBlank
        @Size(min = 6)
        String password;

        @NotBlank
        @Size(min = 7)
        @Pattern(regexp = "^[a-zA-Z0-9@._-]*")
        String email;

        @RequiredArgsConstructor
        private static final class Validator implements org.springframework.validation.Validator {
            private final CheckDuplicatedManager checkDuplicateAccount;

            @Override
            public boolean supports(Class<?> clazz) {
                return clazz.isAssignableFrom(SignUpForm.class);
            }

            @Override
            public void validate(Object target, Errors errors) {
                SignUpForm form = (SignUpForm) target;

                if (checkDuplicateAccount.byName(form.name))
                    errors.rejectValue("email", "email.reject", "이미 등록된 이메일 입니다.");

                else if (checkDuplicateAccount.byEmail(form.email))
                    errors.rejectValue("nickname", "nickname.reject", "이미 등록된 닉네임입니다.");
            }
        }
    }
}
