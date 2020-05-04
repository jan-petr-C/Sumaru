package sumaru.persistence.repository;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import sumaru.persistence.domain.Ad;
import sumaru.persistence.domain.Category;
import sumaru.persistence.domain.User;

import java.util.List;

@Repository
public class AdDao {

	@Autowired
	private SessionFactory sessionFactory;

	public void update(User user, Ad ad) {
		Session session = sessionFactory.getCurrentSession();
		ad.setUser(user);
		user.getAds().add(ad);
		session.update(user);
	}

	public void insert(Ad ad) {
		Session session = sessionFactory.getCurrentSession();
		session.save(ad);
	}
	
	@SuppressWarnings("unchecked")
	public Ad getAdbyId(long id){
		
		Session session = sessionFactory.getCurrentSession();
		List<Ad> ads = session.createQuery("from Ad where id=:id")
				.setParameter("id", id).list();
		Ad ad = ads.get(0);

		return ad;		
	}	

	@SuppressWarnings("unchecked")
	public List<Ad> selectAll(long user_id) {

		Session session = sessionFactory.getCurrentSession();
		List<Ad> ads = session.createQuery("from Ad where user_id=:id")
				.setParameter("id", user_id).list();

		return ads;
	}

	public void removeAd(long id) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("delete Ad where id = :id");
		query.setParameter("id", id);
		query.executeUpdate();
	}
	
	public void removeCategory(int id) {
		Session session = sessionFactory.getCurrentSession();
				
		Query q = session.createQuery("from Category where id = :id ");
		q.setParameter("id", id);
		Category category = (Category) q.list().get(0);
		session.delete(category);
	}
	
	public void saveCategory(Category category) {
		Session session = sessionFactory.getCurrentSession();
		session.save(category);
	}
	
	@SuppressWarnings("unchecked")
	public Category getCategory(int id){
		Session session = sessionFactory.getCurrentSession();
		List<Category> categories = session.createQuery("from Category where id=:id")
				.setParameter("id", id).list();
		Category category = categories.get(0);
		
		return category;
	}
	

	@SuppressWarnings("unchecked")
	public List<Ad> recentAds() {
		Session session = sessionFactory.getCurrentSession();

		Criteria criteria = session.createCriteria(Ad.class);
		criteria.addOrder(Order.desc("date"));
		List<Ad> ads = (List<Ad>) criteria.list();
		
		return ads;

	}
	
	
	@SuppressWarnings("unchecked")
	public List<Category> allCategories(){		
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Category.class);
		criteria.addOrder(Order.asc("element"));
		List<Category> categories = (List<Category>) criteria.list();
		
		return categories;		
	}
	
	@SuppressWarnings("unchecked")
	public Category getCategoryById(int category_id){
	
	Session session = sessionFactory.getCurrentSession();
	List<Category> categories = session.createQuery("from Category where id=:id")
			.setParameter("id", category_id).list();
	Category category = categories.get(0);
	
	return category;
	}
	
	public void adCategory(Category category){
		
		Session session = sessionFactory.getCurrentSession();
		session.save(category);		
	}
	
	@SuppressWarnings("unchecked")
	public List<Ad> getAdsInCategory(int cate_id) {
		
		Session session = sessionFactory.getCurrentSession();
		List<Ad> ads = session.createQuery("from Ad where category_id=:id")
				.setParameter("id", cate_id).list();

		return ads;
	}

}
