package shop.mtcoding.blog.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import shop.mtcoding.blog._core.errors.exception.Exception401;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserRepository userRepository;
    private final HttpSession session;

    @PostMapping("/join")
    public String join(UserRequest.SaveDTO reqDTO){
        User sessionUser = userRepository.save(reqDTO.toEntity());
        session.setAttribute("sessionUser",sessionUser);
        return "redirect:/login-form";
    }
    @GetMapping("/join-form")
    public String joinForm() {
        return "user/join-form";
    }

    @PostMapping("/login")
    public String login(UserRequest.LoginDTO reqDTO){
        User sessionUser = userRepository.findByUsernameAndPassword(reqDTO);
        session.setAttribute("sessionUser",sessionUser);
        return "redirect:/";
    }
    @GetMapping("/login-form")
    public String loginForm() {
        return "user/login-form";
    }

    @PostMapping("/user/update")
    public String update(UserRequest.UpdateDTO reqDTO){
        User sessionUser = (User) session.getAttribute("sessionUser");
        User newSessionUser = userRepository.updateById(sessionUser.getId(),reqDTO.getPassword(), reqDTO.getEmail());
        session.setAttribute("sessionUser",newSessionUser);
        System.out.println(sessionUser);
        return "redirect:/";
    }

    @GetMapping("/user/update-form")
    public String updateForm(HttpServletRequest request) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser ==null){
            throw new Exception401("인증되지 않았습니다. 로그인이 필요합니다.");
        }
        User user = userRepository.findById(sessionUser.getId());
        request.setAttribute("user",user);
        return "user/update-form";
    }

    @GetMapping("/logout")
    public String logout() {
        session.invalidate();
        return "redirect:/";
    }
}
