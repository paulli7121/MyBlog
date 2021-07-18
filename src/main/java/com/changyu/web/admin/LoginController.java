package com.changyu.web.admin;

import com.changyu.po.User;
import com.changyu.service.UserService;
import com.changyu.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisUtil redisUtil;

    @GetMapping
    public String loginPage() {
        return "admin/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String userName,
                        @RequestParam String password,
                        HttpSession session,
                        HttpServletRequest request,
                        HttpServletResponse response,
                        RedirectAttributes attributes) {
        User user = userService.checkUser(userName, password);
        if (user != null) {
            // 生成cookie
            Cookie cookie = new Cookie("cookie_username", user.getUserName());
            cookie.setMaxAge(10 * 24 * 60 * 60);
            cookie.setPath("/");
            response.addCookie(cookie);
            redisUtil.set(user.getUserName(), user, 10 * 24 * 60 * 60);

            user.setPassword(null);
            session.setAttribute("user", user);
            return "admin/index";
        }
        attributes.addFlashAttribute("message", "用户名和密码错误");
        return "redirect:/admin";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session,
                         HttpServletRequest request,
                         HttpServletResponse response) {
        User user = (User) session.getAttribute("user");
        session.removeAttribute("user");

        Cookie cookie = new Cookie("cookie_username", "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        redisUtil.del(user.getUserName());

        return "redirect:/admin";
    }
}
