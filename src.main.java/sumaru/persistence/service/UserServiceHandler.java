package sumaru.persistence.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sumaru.persistence.domain.Ad;
import sumaru.persistence.domain.Category;
import sumaru.persistence.domain.User;
import sumaru.persistence.repository.AdDao;
import sumaru.persistence.repository.UserDao;
import sumaru.web.domain.UserDetails;

@Service
public class UserServiceHandler implements UserService {

	@Autowired
	BCryptPasswordEncoder encoder;

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private AdDao adDao;

	@Autowired
	UserDetailsService userDetailsService;
	
	@Autowired
	Adservice adService;

	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) {

		User user = userDao.findByUserName(username);

		UserDetails userDetails = UserDetails.toUserDetails(user);
		
		return userDetails;
	}
	
	@Transactional(readOnly = true)
	public UserDetails loadUserById(long id) {

		User user = userDao.findById(id);

		UserDetails userDetails = UserDetails.toUserDetails(user);
		
		return userDetails;
	}

	@Transactional
	public void saveNewUser(UserDetails userDetails) {

		userDetails.setRole("ROLE_PRE");

		userDao.insert(User.fromUserDetails(userDetails));
	}

	@Transactional
	public void saveUser(UserDetails userDetails) {

		userDao.update(userDetails);
	}

	@Transactional
	public List<User> getAllUsers() {

		List<User> users = userDao.allUsers();
		
		return users;
	}

	@Transactional
	public void authorize() {

		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();

		User user = userDao.findByUserName(auth.getName());

		user.setRole("ROLE_USER");
		userDao.insert(user);

		autoLogin(auth);
	}

	@Transactional
	public void autoLogin(Authentication auth) {

		org.springframework.security.core.userdetails.UserDetails userDetails = userDetailsService
				.loadUserByUsername(auth.getName());

		Authentication authentication = new UsernamePasswordAuthenticationToken(
				userDetails, userDetails.getPassword(),
				userDetails.getAuthorities());

		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	@Transactional
	public void deleteUser(long id) {
		
		User user = userDao.findByUserId(id);
		Set<Ad> ads = user.getAds();
		
		for (Ad ad : ads) {
			
			Category category = ad.getCategory();
			// Category category = adDao.getCategoryById(category_id);
			category.setCount((category.getCount()) - 1);

						
		}
		
		userDao.deleteUser(id);
	}
}
