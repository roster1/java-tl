package com.db.sys.service.realm;

import com.db.common.exception.ServiceException;
import com.db.sys.dao.SysMenuDao;
import com.db.sys.dao.SysRoleMenuDao;
import com.db.sys.dao.SysUserDao;
import com.db.sys.dao.SysUserRoleDao;
import com.db.sys.entity.SysUser;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ShiroUserRealm extends AuthorizingRealm {
    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private SysUserRoleDao sysUserRoleDao;

    @Autowired
    private SysRoleMenuDao sysRoleMenuDao;

    @Autowired
    private SysMenuDao sysMenuDao;

    /**
     * 设置凭证匹配器(与用户添加操作使用相同的加密算法)
     */
    @Override
    public void setCredentialsMatcher(
            CredentialsMatcher credentialsMatcher) {
        //构建凭证匹配对象
        HashedCredentialsMatcher cMatcher=
                new HashedCredentialsMatcher();
        //设置加密算法
        cMatcher.setHashAlgorithmName("MD5");
        //设置加密次数
        cMatcher.setHashIterations(1);
        super.setCredentialsMatcher(cMatcher);
    }
    /**
     * Realm对象从数据库查询信息,
     * 将查询信息交给认证管理器对象,
     * 配置好的安全管理器对象会调用它,实现用户信息的认证
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //1.获取用户名(页面输入)
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String username = upToken.getUsername();
        //2.通过用户名到数据库查询
        SysUser user = sysUserDao.findUserByUserName(username);
        if(user == null)
            throw new UnknownAccountException();
        if(user.getValid() == 0)
            throw new LockedAccountException();

        //3.封装用户信息
        ByteSource credentialsSalt = ByteSource.Util.bytes(user.getSalt().getBytes());
        SimpleAuthenticationInfo info =
                new SimpleAuthenticationInfo(user,
                user.getPassword(),//hashedCredentials
                credentialsSalt, //credentialsSalt
                getName());//realName
        //4.封装信息返回
        return info;
    }

    /**
     * 实现用户的授权信息获取和封装
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //1.获取登录用户的信息,如用户的id
        SysUser user = (SysUser)principals.getPrimaryPrincipal();
        Integer userId = user.getId();
        //2.通过用户id查询角色的id
        List<Integer> roleIds = sysUserRoleDao.findRoleIdsByUserId(userId);
        if(roleIds.size()==0 || roleIds == null)
            throw new AuthorizationException();
        //3.通过角色id查询菜单id
        Integer[] array = {};
        List<Integer> menuIds = sysRoleMenuDao.findMenuIdsByRoleIds(roleIds.toArray(array));
        if(menuIds == null || menuIds.size() == 0)
            throw new AuthorizationException();
        //4.通过菜单id查询权限标识
        List<String> permissions = sysMenuDao.findPermissions(menuIds.toArray(array));
        Set<String> set = new HashSet<>();
        for(String per:permissions){
            if(!StringUtils.isEmpty(per)){
                set.add(per);
            }
        }
        //5.封装授权信息并返回
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(set);
        return info;
    }
}
