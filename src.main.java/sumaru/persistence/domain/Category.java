package sumaru.persistence.domain;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name = "category")
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name = "id")
	private int id;
    
	@OneToMany(mappedBy = "category", orphanRemoval=true)
	@Cascade({CascadeType.ALL})
	private Set<Ad> ads;	
	
	@Column(length = 30)
	private String element;
	
	private int count;

	public int getCount() {
		return count;
	}


	public void setCount(int count) {
		this.count = count;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public Set<Ad> getAds() {
		return ads;
	}


	public void setAds(Set<Ad> ads) {
		this.ads = ads;
	}


	public String getElement() {
		return element;
	}


	public void setElement(String element) {
		this.element = element;
	}
	
	
	

}
