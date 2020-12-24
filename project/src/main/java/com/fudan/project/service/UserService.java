package com.fudan.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fudan.project.dao.UserDao;
import com.fudan.project.model.Moment;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;
	
	public void addMoment(Moment moment) {
		userDao.addMoment(moment);
	}
	
	public List<Moment> getMoments(int pageSize){
		return userDao.getMoments(pageSize);
	}
	
	public String getMaxPage() {
		return userDao.getMaxPage();
	}
}
