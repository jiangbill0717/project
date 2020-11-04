package fudan.homework2.service;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fudan.homework2.dao.UserDao;
import fudan.homework2.pojo.User;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;
	
	@PostConstruct
	public void init() {
		userDao.getAllUsers();
	}
	
	public List<User> getAllUsers() {
		return userDao.getAllUsers();
	}
}
