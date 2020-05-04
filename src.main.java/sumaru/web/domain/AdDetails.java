package sumaru.web.domain;

import java.util.Date;

import org.springframework.beans.BeanUtils;

import sumaru.persistence.domain.Ad;

public class AdDetails {

	private int id;

	private String head;
	private String text;
	private Date date;	

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public static AdDetails toAdDetails(Ad ad) {
		AdDetails adDetails = new AdDetails();
		BeanUtils.copyProperties(ad, adDetails);
		return adDetails;
	}

}
