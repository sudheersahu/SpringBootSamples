package com.example.demo.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.example.demo.model.BankAccountInfo;


public class BankAccountMapper implements RowMapper<BankAccountInfo>{

	@Override
	public BankAccountInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
		 Long id = rs.getLong("ID");
		 String fullName = rs.getString("FULL_NAME");
		 double balance = rs.getDouble("BALANCE");
		
		return new BankAccountInfo(id, fullName, balance);
	}

}
