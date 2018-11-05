package com.example.demo.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.example.demo.model.AppUser;

public class AppUserMapper implements RowMapper<AppUser> {

	@Override
	public AppUser mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		Long userId = rs.getLong("User_Id");
		String userName = rs.getString("User_Name");
		String encrytedPassword = rs.getString("Encryted_Password");
		
		return new AppUser(userId,userName,encrytedPassword);
	}

}
