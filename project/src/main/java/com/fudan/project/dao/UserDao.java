package com.fudan.project.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.fudan.project.model.Moment;

@Repository
public class UserDao {

	private final static int PAGE_SIZE = 4;
	
	private final static String INSERT_MOMENT = "INSERT INTO `python_courses`.`moment`\n" + 
			"(`username`,\n" + 
			"`content`)" + 
			"VALUES\n" + 
			"( :username,\n" + 
			" :content" + ")"; 
	
	private final static String SELECT_MOMENTS_BY_PAGE_SIZE = "select username,content,post_time from python_courses.moment order by post_time desc limit :currentpage, :pageSize ";
	
	private final static String SELECT_MOMENTS_MAXPAGE_BY_PAGE_SIZE = "select CEILING(count(1)/ :pageSize) from python_courses.moment ";

	
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate; 
	
	public void addMoment(Moment moment) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("username", moment.getUsername());
		paramMap.put("content", moment.getContent());
		jdbcTemplate.update(INSERT_MOMENT, paramMap);
	}
	
	public String getMaxPage() {
	   return	jdbcTemplate.queryForObject(SELECT_MOMENTS_MAXPAGE_BY_PAGE_SIZE, Collections.singletonMap("pageSize", PAGE_SIZE), String.class);
	}
	
	public List<Moment> getMoments(int page) {
		List<Moment> users = new ArrayList<>();
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("currentpage", (page-1)*PAGE_SIZE);
		paramMap.put("pageSize", PAGE_SIZE);
		jdbcTemplate.query(SELECT_MOMENTS_BY_PAGE_SIZE,paramMap, (RowCallbackHandler)rs -> {
			users.add(new Moment(rs.getString("username"), rs.getString("content"), rs.getDate("post_time")));
		});
		return users;
	}
	
}
