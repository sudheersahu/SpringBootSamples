package com.example.demo.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AppRoleDAO {
	
	@Autowired
	JdbcTemplate jdbcTemplate ;
	
	
	public List<String> getRoleNames(Long userId){
		
		   String sql = "Select r.Role_Name " //
	                + " from User_Role ur, App_Role r " //
	                + " where ur.Role_Id = r.Role_Id and ur.User_Id = ? ";
		return jdbcTemplate.queryForList(sql , new Object[] {userId} , 	String.class);
		
	}

}
