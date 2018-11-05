package com.example.demo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.stereotype.Repository;

import com.example.demo.mapper.AppUserMapper;

import com.example.demo.model.AppUser;

@Repository
public class AppUserDAO {
	
	
	@Autowired
	JdbcTemplate jdbcTemplate ;
	
	public AppUser findUserAccount(String userName) {
		return jdbcTemplate.queryForObject("Select u.User_Id, u.User_Name, u.Encryted_Password From App_User u where u.User_Name = ?", new Object[] {userName}, new AppUserMapper());
	}
	
	

}

