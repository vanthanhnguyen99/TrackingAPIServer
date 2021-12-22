package com.vanth.jwt.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.vanth.entity.Account;
import com.vanth.repository.AccountRepository;


@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	AccountRepository repo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Account account = repo.getAccountFromUsername(username);
		if(account == null) {
			throw new UsernameNotFoundException(username);
		}
		return new CustomUserDetail(account);
	}

}
