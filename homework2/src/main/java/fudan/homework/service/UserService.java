package fudan.homework.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fudan.homework.dao.UserDao;
import fudan.homework.pojo.User;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;
	
	public List<User> getAllUsers() {
		return userDao.getAllUsers();
	}
}
