package com.vironit.kazimirov.service.impl;

import com.mysql.cj.xdevapi.Client;
import com.vironit.kazimirov.entity.User;
import com.vironit.kazimirov.entity.UserRoleEnum;
import com.vironit.kazimirov.exception.ClientNotFoundException;
import com.vironit.kazimirov.service.AdminService;
import com.vironit.kazimirov.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private AdminService adminService;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        UserDetails userDetails=null;
        User user = null;
        try {
            user = adminService.findClientByLogin(login);
        } catch (ClientNotFoundException e) {
            e.printStackTrace();
        }
        Set<GrantedAuthority> roles = new HashSet();
            roles.add(new SimpleGrantedAuthority(UserRoleEnum.USER.name()));
            userDetails = new org.springframework.security.core.userdetails.User(user.getLogin(),
                    user.getPassword(),
                    roles);
        return userDetails;
    }
}
