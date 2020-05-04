package sumaru.persistence.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.beans.BeanUtils;

import sumaru.web.domain.AdDetails;

@Entity
@Table(name = "ad")
public class Ad {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	// @Column(name = "id", unique = true)
	@Column(name = "id")
	private long id;
    
	@Column(length = 1000)
	private String text;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;	
	private String head;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	
	
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public Ad() {

	}

	public long getId() {
		return id;
	}

	public String getText() {
		return text;
	}

	public User getUser() {
		return user;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public static Ad fromAdDetails(AdDetails adDetails) {
		Ad ad = new Ad();
		BeanUtils.copyProperties(adDetails, ad);
		return ad;
	}
	
	
	
	
}
