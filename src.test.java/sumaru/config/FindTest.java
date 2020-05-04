package sumaru.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;






import sumaru.persistence.domain.Ad;
import sumaru.persistence.domain.User;
import sumaru.persistence.repository.AdDao;
import sumaru.persistence.repository.UserDao;

import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager="transactionManager")
@ContextConfiguration(classes = {HibernateConfig.class, SecurityConfig.class})
public class FindTest {

	@Autowired
	AdDao adDao;
	
	@Autowired
	UserDao userDao;
	
	@Transactional
	@Test
	public void thatSearchingForAdContainingWorks() throws Exception {

	
		User user = new User();
		user.setId(66);
		
		userDao.insert(user);
		
		Ad ad = new Ad();
		ad.setId(666);
		ad.setText("ahoj");
		ad.setUser(user);
		

		adDao.insert(ad);

		List<Ad> all = adDao.selectAll(66);

		assertNotNull(all);
		

	}
}
