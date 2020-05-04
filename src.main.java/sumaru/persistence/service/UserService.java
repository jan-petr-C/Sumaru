package sumaru.persistence.service;

import java.util.List;

import sumaru.persistence.domain.User;
import sumaru.web.domain.UserDetails;




public interface UserService {
	
	public void saveNewUser(UserDetails userDetails);
	public void saveUser(UserDetails userDetails);
	public void authorize();
	public List<User> getAllUsers();
	public void deleteUser(long id);
	public UserDetails loadUserByUsername(String name);
	public UserDetails loadUserById(long id);
	
	
	
	

		
		
	

}
