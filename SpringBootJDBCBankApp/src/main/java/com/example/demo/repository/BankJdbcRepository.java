package com.example.demo.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.exception.BankTransactionException;
import com.example.demo.mapper.BankAccountMapper;
import com.example.demo.model.BankAccountInfo;

@Repository
public class BankJdbcRepository {
	
	@Autowired
	JdbcTemplate jdbcTemplate ;
	
	public List<BankAccountInfo> getBankAccounts()
	{
		return jdbcTemplate.query("select * from bank_account", new BankAccountMapper());
		
	}
	
	public BankAccountInfo findBankAccount(Long id)
	{
		return jdbcTemplate.queryForObject("select * from bank_account where id = ?", new Object[] {id }, new BankAccountMapper());
	}
	
    @Transactional(propagation = Propagation.MANDATORY)
	public int addAmount(Long id, double amount) throws BankTransactionException {
		
    	BankAccountInfo accountInfo = findBankAccount(id);
    	
    	if(accountInfo == null) {
    		throw new BankTransactionException("Account Not found"+ id);
    	}
    	
    	double newBalance = accountInfo.getBalance() + amount;
    	
    	if(newBalance <0) {
    		throw new BankTransactionException(  "The money in the account '" + id + "' is not enough (" + accountInfo.getBalance() + ")");
    		
    	}
    	accountInfo.setBalance(newBalance);
    	
    	return jdbcTemplate.update("update bank_account set balance = ? where id = ?",accountInfo.getBalance(), accountInfo.getId());
		
	}
    
    @Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = BankTransactionException.class)
    public void sendMoney(Long fromAccountId, Long toAccountId, double amount) throws BankTransactionException{
    	
    	try {
    		addAmount(toAccountId,amount);
        	addAmount(fromAccountId,-amount);
    	}catch(BankTransactionException e) {
    		throw e;
    	}
    	
    }
}
