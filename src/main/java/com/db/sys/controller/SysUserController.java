package com.db.sys.controller;

import com.db.sys.entity.SysUser;
import com.db.sys.service.SysUserService;
import com.db.sys.vo.JsonResult;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("/user/")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

    @RequestMapping("doUserListUI")
    public String doUserListUI(){
        return  "sys/user_list";
    }

    /**
     * 实现用户页面数据的展现
     * @param username
     * @param pageCurrent
     * @return
     */
    @RequestMapping("doFindPageObjects")
    @ResponseBody
    public JsonResult doFindPageObjects(String username,Integer pageCurrent){
        return  new JsonResult(sysUserService.findPageObjects(username,pageCurrent));
    }

    /**
     * 实现用户的禁用启用
     * ...这里的修改的用户暂时设置admin,将来是登录的用户
     * @param id
     * @param valid
     * @param modifiedUser
     * @return
     */
    @RequestMapping("doValidById")
    @ResponseBody
    public JsonResult doValidById(Integer id,Integer valid,String modifiedUser){
        sysUserService.validById(id,valid,"admin");
        return new JsonResult("update ok");
    }

    /**
     * 用户添加页面的展现
     * @return
     */
    @RequestMapping("doUserEditUI")
    public String doUserEditUI(){
        return "sys/user_edit";
    }

    /**
     * 返回用户的修改页面
     * @return
     */
    @RequestMapping("doSaveObject")
    @ResponseBody
    public JsonResult doSaveObject(SysUser sysUser,Integer[] roleIds){
        sysUserService.saveObject(sysUser,roleIds);
        return new JsonResult("save ok");
    }

    /**
     * 实现用户的修改
     * 1.根据传过来的用户id,回显该用户数据
     * 2.将修改的用户数据保存
     * @param id
     * @return
     */
    @RequestMapping("doFindObjectById")
    @ResponseBody
    public JsonResult doFindObjectById(Integer id){
        Map<String, Object> map = sysUserService.findObjectById(id);
        return  new JsonResult(map);
    }
    /**将修改的用户数据保存*/
    @RequestMapping("doUpdateObject")
    @ResponseBody
    public JsonResult doUpdateObject(SysUser entity,Integer[] roleIds){
        try {
            sysUserService.updateObject(entity,roleIds);
        } catch (Exception e) {
            e.printStackTrace();
            return  new JsonResult(e.getMessage());
        }
        return  new JsonResult("update ok");
    }

    /**用户登录时,将用户信息进行封装,并交给安全管理器,安全管理器
     * 调用认证管理器时,会把token传递过去*/
    @RequestMapping("doLogin")
    @ResponseBody
    public JsonResult doLogin(boolean isRememberMe,String username,String password){
        try {
            //1.获取Subject对象
            Subject subject = SecurityUtils.getSubject();
            //1.1将用户的信息封装
            UsernamePasswordToken upToken = new UsernamePasswordToken(username,password);
            //实现记住我
            if(isRememberMe)
                upToken.setRememberMe(true);
            //1.2将封装的用户信息交给安全管理器
            subject.login(upToken);
            //分析:
            //1)token会传给shiro的SecurityManager
            //2)SecurityManager将token传递给认证管理器
            //3)认证管理器会将token传递给realm
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return new JsonResult("用户名或密码错误");
        }
        return  new JsonResult("login ok");
    }

    /**用户密码修改页面实现*/
}
