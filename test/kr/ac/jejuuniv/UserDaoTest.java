package kr.ac.jejuuniv;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.util.Assert;

public class UserDaoTest {
	
	private UserDao userDao;

	@Before
	public void setup() {
		ApplicationContext context = new GenericXmlApplicationContext("daoFactory.xml");
		userDao = context.getBean("userDao", UserDao.class);
	}
	
	@Test
	public void get() throws ClassNotFoundException, SQLException {
		String id = "hulk";
		String name = "허윤호";
		String password = "1111";
		User user = userDao.get(id);
		assertEquals(id, user.getId());
		assertEquals(name, user.getName());
		assertEquals(password, user.getPassword());
	}
	
	public void add() throws ClassNotFoundException, SQLException{
		User user = new User();
		String id = String.valueOf(new Random().nextInt());
		String name = "허윤호";
		String password = "1234";
		user.setId(id);
		user.setName(name);
		user.setPassword(password);
		
		userDao.add(user);
		User addedUser = userDao.get(id);
		assertEquals(id, addedUser.getId());
		assertEquals(name, addedUser.getName());
		assertEquals(password, addedUser.getPassword());
	}
}