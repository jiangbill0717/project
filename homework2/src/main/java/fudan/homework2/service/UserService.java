package fudan.homework2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fudan.homework2.dao.UserDao;
import fudan.homework2.pojo.User;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;
	
	public List<User> getAllUsers() {
		return userDao.getAllUsers();
	}
}
