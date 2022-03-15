package com.jjang051.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ReplyBoardDao implements ReplyBoardService {

	@Autowired
	SqlSessionFactory sqlSessionFactory;
	
	
	@Override
	public int insertBoard(ReplyBoardDto replyBoardDto) {
		int result = 0;
		SqlSession sqlSession = sqlSessionFactory.openSession();
		result = sqlSession.insert("insertBoard",replyBoardDto);
		sqlSession.commit();
		sqlSession.close();
		return result;
	}

	@Override
	public int insertReplyBoard(ReplyBoardDto replyBoardDto) {
		int result = 0;
		SqlSession sqlSession = sqlSessionFactory.openSession();
		result = sqlSession.update("insertReplyBoard",replyBoardDto);
		sqlSession.commit();
		sqlSession.close();
		return result;
	}

	@Override
	public int updateBoard(ReplyBoardDto replyBoardDto) {
		int result = 0;
		SqlSession sqlSession = sqlSessionFactory.openSession();
		result = sqlSession.update("updateBoard", replyBoardDto);
		sqlSession.commit();
		sqlSession.close();
		return result;
	}

	@Override
	public int updateReLevel(ReplyBoardDto replyBoardDto) {
		int result = 0;
		SqlSession sqlSession = sqlSessionFactory.openSession();
		result = sqlSession.insert("updateReLevel",replyBoardDto);
		sqlSession.commit();
		sqlSession.close();
		return result;
	}

	@Override
	public int getMaxRegroup() {
		int result = 0;
		SqlSession sqlSession = sqlSessionFactory.openSession();
		result = sqlSession.selectOne("getMaxReGroup");
		return result;
	}

	@Override
	public List<ReplyBoardDto> getAllList(int start, int end, String search_select, String search_word) {
		HashMap<String,Object> pageMap = new HashMap<>();
		pageMap.put("start",start);
		pageMap.put("end",end);
		pageMap.put("searchSelect",search_select);
		pageMap.put("searchWord",search_word);
		
		List<ReplyBoardDto> replyBoardList = null;
		SqlSession sqlSession = sqlSessionFactory.openSession();
		replyBoardList = sqlSession.selectList("getAllList",pageMap);
		sqlSession.close();
		return replyBoardList;
	}

	@Override
	public List<ReplyBoardDto> getSearchAllList(String search_select, String search_word) {
		return null;
	}

	@Override
	public ReplyBoardDto getSelectOne(int no) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		ReplyBoardDto replyBoardDto = sqlSession.selectOne("getSelectOne",no);
		sqlSession.close();
		return replyBoardDto;
	}

	@Override
	public ReplyBoardDto getPrevSelect(int num) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		ReplyBoardDto replyBoardDto = sqlSession.selectOne("getPrevSelect",num);
		sqlSession.close();
		return replyBoardDto;
	}

	@Override
	public ReplyBoardDto getNextSelect(int num) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		ReplyBoardDto replyBoardDto = sqlSession.selectOne("getNextSelect",num);
		sqlSession.close();
		return replyBoardDto;
	}

	@Override
	public int updateHit(int no) {
		int result = 0;
		SqlSession sqlSession = sqlSessionFactory.openSession();
		result = sqlSession.update("updateHit",no);
		sqlSession.commit();
		sqlSession.close();
		
		return result;
	}

	@Override
	public int deleteBoard(ReplyBoardDto replyBoardDto) {
		int result = 0;
		SqlSession sqlSession = sqlSessionFactory.openSession();
		result = sqlSession.delete("deleteBoard", replyBoardDto);
		sqlSession.commit();
		sqlSession.close();
		return result;
	}

	@Override
	public int getTotal(String search_select, String search_word) {
		HashMap<String,Object> searchMap = new HashMap<>();
		searchMap.put("searchSelect",search_select);
		searchMap.put("searchWord",search_word);
		SqlSession sqlSession = sqlSessionFactory.openSession();
		int total = sqlSession.selectOne("getTotal",searchMap);
		sqlSession.close();
		return total;
	}
	
}
