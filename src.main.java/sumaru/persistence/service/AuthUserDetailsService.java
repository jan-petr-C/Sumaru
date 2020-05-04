package sumaru.persistence.service;

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
import org.springframework.transaction.annotation.Transactional;

import sumaru.persistence.repository.UserDao;

@Service
public class AuthUserDetailsService implements UserDetailsService {

	
	@Autowired
	private UserDao userDao;

	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(final String username)
			throws UsernameNotFoundException {

		sumaru.persistence.domain.User user = userDao.findByUserName(username);
		List<GrantedAuthority> aut = new ArrayList<GrantedAuthority>();
		aut.add(new SimpleGrantedAuthority(user.getRole()));

		return new User(user.getName(), user.getPassword(), aut);

	}
}
