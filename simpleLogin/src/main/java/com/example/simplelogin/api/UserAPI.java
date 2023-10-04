package com.example.simplelogin.api;


import com.example.simplelogin.entity.User;
import com.example.simplelogin.model.Result;
import com.example.simplelogin.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * 編寫相關功能之API，允許前端發送Request請求調用後端的Service
 * @RestController 代表API為Restful
 * @RequestMapping 設定Controller的API接口路徑為/api/user
 * 例如：若需調用register(新增用戶功能)，接口實際路徑為/api/user/register
 */
@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserAPI {

    // Session的資料
    public static final String SESSION_NAME = "userInfo";

    @Autowired
    private UserService userService;

    /**
     * 用戶註冊功能
     *
     * @param user 註冊用戶之相關訊息 (Username，Password)
     * @param errors  Spring Validation的驗證錯誤訊息
     * @return 註冊結果
     */
    @PostMapping("/register")
    public Result<User> register(@RequestBody @Valid User user, BindingResult errors) {
        log.info("Start register");
        Result<User> result;
        // 若Spring Validation 校驗出錯，Return註冊失敗和相關錯誤訊息
        if (errors.hasErrors()) {
            result = new Result<>();
            result.setResultFailed(errors.getFieldError().getDefaultMessage());
            log.info("Failed to register For " + user.getUsername());
            return result;
        }
        // 使用註冊服務
        result = userService.register(user);
        log.info("Register success For " + user.getUsername());
        log.info("Password is " + user.getPassword());
        return result;
    }

    /**
     * 用戶登入
     *
     * @param user 用戶登入之相關訊息 (Username，Password)
     * @param errors Spring Validation的驗證錯誤訊息
     * @param request
     * @return 登入結果
     */
    @PostMapping("/login")
    public Result<User> login(@RequestBody @Valid User user, BindingResult errors, HttpServletRequest request) {
        log.info("Start login");
        Result<User> result;
        if (errors.hasErrors()) {
            result = new Result<>();
            result.setResultFailed(errors.getFieldError().getDefaultMessage());
            log.info("Failed to login");
            return result;
        }
        // Start Login
        result = userService.login(user);
        // Login success, setup session
        log.info("Login Success For " + user.getUsername());
        if (result.isSuccess()) {
            request.getSession().setAttribute(SESSION_NAME, result.getData());
        }
        return result;
    }

    /**
     * 判斷用戶目前是否已經登入狀態
     *
     * @param request 用戶發的Request，從Request的Session的用戶訊息來判斷該用戶是否已經登錄
     * @return 若用戶為已登入，Return 用戶訊息，否則Return null
     */
    @GetMapping("/is-login")
    public Result<User> isLogin(HttpServletRequest request) {
        return userService.isLogin(request.getSession());
    }

    /**
     * 更新用戶資料（
     *
     * @param user 用戶提出的修改User Object
     * @param request 用戶發的Request，操作Session用
     * @return 修改結果(是否成功）
     * @throws Exception
     */
    @PatchMapping("/update")
    public Result<User> update(@RequestBody User user, HttpServletRequest request) throws Exception {
        log.info("Start update the information");
        Result<User> result = new Result<>();
        HttpSession session = request.getSession();
        // Check session
        // 透過檢查Session檢查用戶是否一致
        User sessionUser = (User) session.getAttribute(SESSION_NAME);
        if (sessionUser.getId() != user.getId().intValue()) {
            log.info("Failed to update for " + user.getUsername());
            result.setResultFailed("User not the same, terminate");
            return result;
        }
        result = userService.update(user);
        // If update success, update the session message.
        // 更新成功，更新相關的Session資料
        if (result.isSuccess()) {
            session.setAttribute(SESSION_NAME, result.getData());
        }
        log.info("Update successfully for " + user.getUsername());
        return result;
    }

    /**
     * 用戶登出，主要是null session
     *
     * @param request 用戶發的Request，操作Session用
     * @return 登出結果
     */
    @GetMapping("/logout")
    public Result<Void> logout(HttpServletRequest request) {
        Result<Void> result = new Result<>();
        request.getSession().setAttribute(SESSION_NAME, null);
        result.setResultSuccess("Logout Success");
        log.info("Logout success!");
        return result;
    }
}
