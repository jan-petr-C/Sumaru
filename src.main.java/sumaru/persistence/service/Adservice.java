package sumaru.persistence.service;


import java.util.List;

import sumaru.persistence.domain.Ad;
import sumaru.persistence.domain.Category;
import sumaru.web.domain.AdDetails;
import sumaru.web.domain.ExtAd;

public interface Adservice {
	
	
	public void saveAd (AdDetails adDetails, int category_id);
	public void  removeAd(long id);	
	public List<Ad> all();
	public List<ExtAd> getRecentAds();
	public List<Ad> adminAll(long id);
	public List<Category>allCategories();
	public void removeCategory(int id);
	public void saveCategory(int id, String element);
	public void adCategory(String element);
	public Category getCategory(int id);
	public List<ExtAd> getAdsInCategory(int id);


		
		
	

}
