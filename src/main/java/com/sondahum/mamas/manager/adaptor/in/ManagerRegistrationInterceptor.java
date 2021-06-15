package com.sondahum.mamas.manager.adaptor.in;

import com.sondahum.mamas.manager.application.port.in.FindManager;
import com.sondahum.mamas.manager.domain.Manager;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import web.WebAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.sondahum.mamas.security.SpringSecurities.authenticated;

@RequiredArgsConstructor
public class ManagerRegistrationInterceptor implements HandlerInterceptor {
    private final FindManager findManager;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (authenticated()) {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            Manager account = findManager.byName(username)
                    .or(() -> findManager.byEmail(username))
                    .orElseThrow(() -> new IllegalArgumentException("already authenticated but account not found."));

            request.setAttribute(WebAttribute.ACCOUNT_NAME, account);
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        Manager account = (Manager) request.getAttribute(WebAttribute.ACCOUNT_NAME);

        if (modelAndView != null && account != null) {
            modelAndView.addObject(
                    "account",
                    new AuthenticatedAccountModel(account.name, account.email, account.emailVerified)
            );
        }
    }

    @RequiredArgsConstructor
    private static class AuthenticatedAccountModel {
        private final String name;
        private final String email;
        private final Boolean emailVerified;
    }
}
