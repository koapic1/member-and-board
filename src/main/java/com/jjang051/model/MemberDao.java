package com.jjang051.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//Spring에서 저동 주입 받는 방법...
//1. ComponentScan을 통해 받는 방법...

@Component
public class MemberDao implements MemberService  {
	
	@Autowired
	public SqlSessionFactory sqlSessionFactory;
	
	
	@Override
	public List<MemberDto> getAllMemberList(int start, int end, String search_select, String search_word) {
		HashMap<String,Object> pageMap = new HashMap<>();
		pageMap.put("start",start);
		pageMap.put("end",end);
		pageMap.put("searchSelect",search_select);
		pageMap.put("searchWord",search_word);
		
		List<MemberDto> memberList = null;
		SqlSession sqlSession = sqlSessionFactory.openSession();
		memberList = sqlSession.selectList("getAllMemberList",pageMap);
		sqlSession.close();
		return memberList;
	}

	@Override
	public MemberDto getSelectOne(String id) {
		MemberDto memberDto = null;
		SqlSession sqlSession = sqlSessionFactory.openSession();
		memberDto = sqlSession.selectOne("getMemberSelectOne",id);
		sqlSession.close();
		return memberDto;
	}

	@Override
	public MemberDto getLoggedMember(MemberDto pMemberDto) {
		MemberDto memberDto = null;
		SqlSession sqlSession = sqlSessionFactory.openSession();
		memberDto = sqlSession.selectOne("getLoggedMember",pMemberDto);
		sqlSession.close();
		return memberDto;
	}

	@Override
	public int deleteMember(MemberDto memberDto) {
		int result = 0;
		SqlSession sqlSession = sqlSessionFactory.openSession();
		result = sqlSession.delete("deleteMember",memberDto);
		sqlSession.commit();
		sqlSession.close();
		return result;
	}

	@Override
	public int insertMember(MemberDto memberDto) {
		int result = 0;
		SqlSession sqlSession = sqlSessionFactory.openSession();
		result = sqlSession.insert("insertMember",memberDto);
		sqlSession.commit();
		sqlSession.close();
		return result;
	}

	@Override
	public int updateMember(MemberDto memberDto) {
		int result = 0;
		SqlSession sqlSession = sqlSessionFactory.openSession();
		result = sqlSession.update("updateMember",memberDto);
		sqlSession.commit();
		sqlSession.close();
		return result;
	}

	@Override
	public int idCheck(String user_id) {
		int result = 0;
		SqlSession sqlSession = sqlSessionFactory.openSession();
		result = sqlSession.selectOne("idCheck",user_id);
		sqlSession.close();
		return result;
	}

	@Override
	public int getTotal(String search_select, String search_word) {
		return 0;
	}
	
}









