package com.example.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.example.common.Result;
import com.example.common.enums.ResultCodeEnum;
import com.example.common.enums.RoleEnum;
import com.example.entity.Account;
import com.example.service.AdminService;
import com.example.utils.SHA256Encryption;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 基础前端接口
 */
@RestController
public class WebController {

    @Resource
    private AdminService adminService;

    @GetMapping("/")
    public Result hello() {
        return Result.success("访问成功");
    }

    /**
     * 登录接口：接收明文密码，后端加密后进行账号验证
     */
    @PostMapping("/login")
    public Result login(@RequestBody Account account) {
        if (ObjectUtil.isEmpty(account.getUsername()) || ObjectUtil.isEmpty(account.getPassword())
                || ObjectUtil.isEmpty(account.getRole())) {
            return Result.error(ResultCodeEnum.PARAM_LOST_ERROR);
        }

        // 后端统一对明文密码进行 SHA-256 加密
        String encryptedPassword = SHA256Encryption.encrypt(account.getPassword());
        account.setPassword(encryptedPassword);

        if (RoleEnum.ADMIN.name().equals(account.getRole())) {
            account = adminService.login(account);
        }

        return Result.success(account);
    }

    /**
     * 注册接口：注册时对密码进行加密后存入数据库
     */
    @PostMapping("/register")
    public Result register(@RequestBody Account account) {
        if (StrUtil.isBlank(account.getUsername()) || StrUtil.isBlank(account.getPassword())
                || ObjectUtil.isEmpty(account.getRole())) {
            return Result.error(ResultCodeEnum.PARAM_LOST_ERROR);
        }

        // 加密用户输入的明文密码
        String encryptedPassword = SHA256Encryption.encrypt(account.getPassword());
        account.setPassword(encryptedPassword);

        if (RoleEnum.ADMIN.name().equals(account.getRole())) {
            adminService.register(account);
        }

        return Result.success();
    }

    /**
     * 修改密码接口：验证原密码，加密新密码后更新数据库
     */
    @PutMapping("/updatePassword")
    public Result updatePassword(@RequestBody Account account) {
        if (StrUtil.isBlank(account.getUsername()) || StrUtil.isBlank(account.getPassword())
                || StrUtil.isBlank(account.getNewPassword()) || ObjectUtil.isEmpty(account.getRole())) {
            return Result.error(ResultCodeEnum.PARAM_LOST_ERROR);
        }

        // 加密旧密码和新密码
        account.setPassword(SHA256Encryption.encrypt(account.getPassword()));
        account.setNewPassword(SHA256Encryption.encrypt(account.getNewPassword()));

        if (RoleEnum.ADMIN.name().equals(account.getRole())) {
            adminService.updatePassword(account);
        }

        return Result.success();
    }

}
