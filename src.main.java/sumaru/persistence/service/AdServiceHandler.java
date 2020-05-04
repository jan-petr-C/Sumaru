package sumaru.persistence.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sumaru.persistence.domain.Ad;
import sumaru.persistence.domain.Category;
import sumaru.persistence.domain.User;
import sumaru.persistence.repository.AdDao;
import sumaru.persistence.repository.UserDao;
import sumaru.web.domain.AdDetails;
import sumaru.web.domain.ExtAd;

@Service
public class AdServiceHandler implements Adservice {

	@Autowired
	BCryptPasswordEncoder encoder;

	@Autowired
	private AdDao adDao;

	@Autowired
	private UserDao userDao;

	@Transactional
	public void saveAd(AdDetails adDetails, int category_id) {
		Ad ad = Ad.fromAdDetails(adDetails);

		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();

		String username = auth.getName();
		User user = userDao.findByUserName(username);

		Category category = adDao.getCategoryById(category_id);
		category.setCount((category.getCount()) + 1);

		ad.setCategory(category);
		ad.setUser(user);
		ad.setDate(new Date());
		adDao.insert(ad);
	}

	@Transactional
	public List<Ad> all() {

		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();

		String username = auth.getName();
		User user = userDao.findByUserName(username);
		long user_id = user.getId();

		List<Ad> ads = adDao.selectAll(user_id);
		return ads;
	}

	@Transactional
	public List<Ad> adminAll(long user_id) {

		List<Ad> ads = adDao.selectAll(user_id);
		
		return ads;
	}

	@Transactional
	public void removeAd(long id) {

		Category category = adDao.getAdbyId(id).getCategory();
		// Category category = adDao.getCategoryById(category_id);
		category.setCount((category.getCount()) - 1);
		adDao.removeAd(id);
	}

	@Transactional
	public void removeCategory(int id) {
		adDao.removeCategory(id);
	}

	@Transactional
	public void saveCategory(int id, String element) {
		Category category = adDao.getCategoryById(id);
		category.setElement(element);
		adDao.saveCategory(category);
	}

	@Transactional
	public List<ExtAd> getRecentAds() {

		List<Ad> ads = adDao.recentAds();

		return extendAds(ads);

	}

	@Transactional
	public List<Category> allCategories() {

		List<Category> category = adDao.allCategories();

		return category;
	}

	@Transactional
	public void adCategory(String element) {
		Category category = new Category();
		category.setElement(element);
		category.setCount(0);
		adDao.adCategory(category);
	}

	@Transactional
	public Category getCategory(int id) {
		
		return adDao.getCategory(id);
		
	}

	@Transactional
	public List<ExtAd> getAdsInCategory(int cate_id) {

		List<Ad> ads = adDao.getAdsInCategory(cate_id);

		return extendAds(ads);
	}

	private List<ExtAd> extendAds(List<Ad> ads) {

		List<ExtAd> extAds = new ArrayList<ExtAd>();

		for (Ad ad : ads) {
			ExtAd extAd = new ExtAd();

			extAd.setCategory(ad.getCategory());
			extAd.setDate(ad.getDate());
			extAd.setHead(ad.getHead());
			extAd.setText(ad.getText());
			extAd.setId(ad.getId());

			User user = (ad.getUser());

			extAd.setName(user.getName());
			extAd.setEmail(user.getEmail());
			extAd.setTelephone(user.getTelephone());

			extAds.add(extAd);
		}
		return extAds;
	}
}
