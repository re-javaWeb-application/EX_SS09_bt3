package com.re.bt3;

import java.time.Duration;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ThemeController {
    private static final String THEME_COOKIE = "theme";
    private static final String LIGHT_THEME = "light";
    private static final String DARK_THEME = "dark";
    private static final Duration THEME_TTL = Duration.ofDays(30);

    @GetMapping("/")
    public String home(
            @CookieValue(name = THEME_COOKIE, defaultValue = LIGHT_THEME) String themeCookie,
            Model model
    ) {
        String theme = normalizeTheme(themeCookie);
        model.addAttribute("theme", theme);
        model.addAttribute("themeName", DARK_THEME.equals(theme) ? "Dark Mode" : "Light Mode");
        model.addAttribute("nextTheme", DARK_THEME.equals(theme) ? LIGHT_THEME : DARK_THEME);
        model.addAttribute("buttonText", DARK_THEME.equals(theme)
                ? "Chuyển sang Light Mode"
                : "Chuyển sang Dark Mode");
        return "index";
    }

    @PostMapping("/change-theme")
    public String changeTheme(
            @RequestParam(name = "theme", defaultValue = LIGHT_THEME) String selectedTheme,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        String theme = normalizeTheme(selectedTheme);
        ResponseCookie cookie = ResponseCookie.from(THEME_COOKIE, theme)
                .maxAge(THEME_TTL)
                .path("/")
                .httpOnly(true)
                .secure(request.isSecure())
                .sameSite("Lax")
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        return "redirect:/";
    }

    private String normalizeTheme(String theme) {
        return DARK_THEME.equalsIgnoreCase(theme) ? DARK_THEME : LIGHT_THEME;
    }
}
