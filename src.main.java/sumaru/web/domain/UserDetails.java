package sumaru.web.domain;

import org.springframework.beans.BeanUtils;

import sumaru.persistence.domain.User;

public class UserDetails {

	private long id;
	private String name;
	private String password;
	private String telephone;
	private String email;
	private String role;

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public UserDetails() {

	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
/*
	public static User fromUserDetails(UserDetails userDetails) {
		User user = new User();
		BeanUtils.copyProperties(user, userDetails);
		return user;
	}
*/	
	public static UserDetails toUserDetails(User user) {
		UserDetails userDetails = new UserDetails();
		BeanUtils.copyProperties(user, userDetails);
		return userDetails;
	}

}
