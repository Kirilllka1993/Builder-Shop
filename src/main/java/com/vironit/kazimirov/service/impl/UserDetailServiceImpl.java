package com.vironit.kazimirov.service.impl;

import com.vironit.kazimirov.dto.UserDto;
import com.vironit.kazimirov.exception.ClientNotFoundException;
import com.vironit.kazimirov.service.AdminService;
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
        UserDto user = null;
        try {
            user = adminService.findClientByLogin(login);
        } catch (ClientNotFoundException e) {
            e.printStackTrace();
        }
        Set<GrantedAuthority> roles = new HashSet();
            roles.add(new SimpleGrantedAuthority(user.getUserRoleEnum()));
//            userDetails = new org.springframework.security.core.userdetails.(user.getLogin(),
//                    user.getPassword(),
//                    roles);
        userDetails=new UserDetailImpl(user.getLogin(),user.getPassword(),roles,user.getId());
        return userDetails;
    }
}
