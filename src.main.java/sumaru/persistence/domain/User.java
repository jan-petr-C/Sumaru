package sumaru.persistence.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import java.util.Set;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.beans.BeanUtils;

import sumaru.web.domain.UserDetails;

@Entity
@Table(name = "user2")
//uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name = "id")
	private long id;

	@Column(unique = true)
	private String name;
	private String password;
	private String telephone;
	
	//@Column(unique = true)	
	private String email;
	private String role;

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	// @OneToMany
	@OneToMany(mappedBy = "user", orphanRemoval=true)
	@Cascade({CascadeType.ALL})
	private Set<Ad> ads;	

	public Set<Ad> getAds() {
		return ads;
	}

	public void setAds(Set<Ad> ads) {
		this.ads = ads;
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

	public User() {

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

	public static User fromUserDetails(UserDetails userDetails) {
		User user = new User();
		BeanUtils.copyProperties(userDetails, user);
		return user;
	}

}
