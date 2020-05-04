package sumaru.persistence.repository;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import sumaru.persistence.domain.User;
import sumaru.web.domain.UserDetails;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDao {

	@Autowired
	private SessionFactory sessionFactory;

	public void insert(User user) {
		Session session = sessionFactory.getCurrentSession();		
		session.save(user);
	}

	@SuppressWarnings("unchecked")
	public User findByUserName(String username) {

		List<User> users = new ArrayList<User>();

		users = sessionFactory.getCurrentSession()
				.createQuery("from User where name=?")
				.setParameter(0, username).list();

		if (users.size() > 0) {
			return users.get(0);
		} else {
			return null;
		}
	}
	
		
	@SuppressWarnings("unchecked")
	public User findById(long id) {
		List<User> users = new ArrayList<User>();
		users = sessionFactory.getCurrentSession()
				.createQuery("from User where id=?")
				.setParameter(0, id).list();

		if (users.size() > 0) {
			return users.get(0);
		} else {
			return null;
		}
	}	

	@SuppressWarnings("unchecked")
	public User findByUserId(long id) {

		List<User> users = new ArrayList<User>();

		users = sessionFactory.getCurrentSession()
				.createQuery("from User where id=:id")
				.setParameter("id", id).list();

		if (users.size() > 0) {
			return users.get(0);
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<User> allUsers() {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(User.class);
		criteria.addOrder(Order.asc("name"));
		List<User> users = (List<User>) criteria.list();
		
		return users;
	}

	public void deleteUser(long id) {
		Session session = sessionFactory.getCurrentSession();
		Query q = session.createQuery("from User where id = :id ");
		q.setParameter("id", id);
		User user = (User) q.list().get(0);
		session.delete(user);
	}

	public void update(UserDetails userDetails) {
		long id = userDetails.getId();
		Session session = sessionFactory.getCurrentSession();
		Query q = session.createQuery("from User where id = :id");
		q.setParameter("id", id);
		User user = (User) q.list().get(0);
		user.setEmail(userDetails.getEmail());
		user.setTelephone(userDetails.getTelephone());
		session.save(user);
	}
}
