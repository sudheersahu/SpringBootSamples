package com.example.demo.dao;


import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Account;

@Repository
@Transactional
public class AccountDAO {
	
	@Autowired
    private SessionFactory sessionFactory;
 
	  public Account findAccount(String userName) {
	        Session session = this.sessionFactory.getCurrentSession();
	        return session.find(Account.class, userName);
	    }
}
