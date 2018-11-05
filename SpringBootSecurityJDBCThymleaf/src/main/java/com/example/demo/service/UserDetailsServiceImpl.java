package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.dao.AppRoleDAO;
import com.example.demo.dao.AppUserDAO;
import com.example.demo.model.AppUser;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired
	private AppUserDAO appUserDAO;
	
	@Autowired
	private AppRoleDAO appRoleDAO; 

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		
		AppUser appuser = this.appUserDAO.findUserAccount(username);
		
		if(appuser == null) {
			System.out.println("User not found!"+ username);
			throw new UsernameNotFoundException("User" +username + "was not found in the database");
		}
		
		System.out.println("Found user :" +appuser);
		
		List<String> roleNames = this.appRoleDAO.getRoleNames(appuser.getUserId());
		
		List<GrantedAuthority> grantedList = new ArrayList<GrantedAuthority>();
		if(roleNames != null) {
			for (String role :roleNames) {
				
				GrantedAuthority authority = new SimpleGrantedAuthority(role);
				grantedList.add(authority);
			}
		}
		
		UserDetails userDetails = (UserDetails) new User(appuser.getUserName(), //
				appuser.getEncrytedPassword(), grantedList);
		
		return userDetails;
	}

}
