package edu.java.contact.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import edu.java.contact.model.Contact;
import oracle.jdbc.OracleDriver;

import static edu.java.contact.model.Contact.Entity.*;
import static edu.java.contact.ojdbc.OracleConnect.*;

public class ContactDaoImpl implements ContactDao{

	// 싱글톤
	private static ContactDaoImpl instance = null;
	private ContactDaoImpl() {};
	public static ContactDaoImpl getInstance() {
		if (instance == null) {
			instance = new ContactDaoImpl();
		}
		return instance;
	}
	
	//오라클 DB에 접속한 Connection 객체를 리턴.
	private Connection getConnection() throws SQLException{
		// 오라클 JDBC 드라이버 등록
		DriverManager.registerDriver(new OracleDriver());
		// DB접속
		Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
		
		return conn;
	}
	
	private void closeResource(Connection conn, Statement stmt) throws SQLException {
		stmt.close();
		conn.close();
	}
	private void closeResource(Connection conn, Statement stmt, ResultSet rs) throws SQLException {
		rs.close();
		closeResource(conn, stmt);
	}
	
	
	// insert into contacts(name, phone, email) values('촉촉이','123-1234','123@1234.com');
	private static final String SQL_INSERT =
			"insert into contacts(name, phone, email) values(?,?,?)";
	@Override
	public int create(Contact contact) {
		int result = 0;
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = getConnection();
			System.out.println(SQL_INSERT);
			stmt = conn.prepareStatement(SQL_INSERT);
			stmt.setString(1, contact.getName());
			stmt.setString(2, contact.getPhone());
			stmt.setString(3, contact.getEmail());
			result = stmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				closeResource(conn, stmt);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return result;
	}

	// select * from contacts order by cid
	private static final String SQL_SELECT_ALL=
			"select * from "+TBL_NAME+" order by "+COL_CID;
	@Override
	public List<Contact> read() {
		List<Contact> list = new ArrayList<>();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conn =getConnection();
			System.out.println(SQL_SELECT_ALL);
			stmt = conn.prepareStatement(SQL_SELECT_ALL);
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				int cid = rs.getInt(COL_CID);
				String name = rs.getString(COL_NAME);
				String phone = rs.getString(COL_PHONE);
				String email = rs.getString(COL_EMAIL);
				
				Contact contact = new Contact(cid, name, phone, email);
				
				list.add(contact);
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				closeResource(conn, stmt, rs);
			
				// 마지막의 마지막에서는 모든 에러를 잡겠다.
				// null을 찾자 null을 찾자 찍찍찍 
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return list;
	}// read()

	// select * from contacts where cid =1;
	private static final String SQL_SELECT_BY_ID = 
			"select * from "+TBL_NAME+" where "+COL_CID+" = ?";
	@Override
	public Contact read(int cid) {
		Contact contact = null;
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			System.out.println(SQL_SELECT_BY_ID);
			stmt = conn.prepareStatement(SQL_SELECT_BY_ID);
			stmt.setInt(1, cid);
			rs = stmt.executeQuery();
			
			while(rs.next()) {// 검색된 행 데이터가 있다면
				int id = rs.getInt(COL_CID);
				String name = rs.getString(COL_NAME);
				String phone = rs.getString(COL_PHONE);
				String email = rs.getString(COL_EMAIL);
				
				contact = new Contact(cid, name, phone, email);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				closeResource(conn, stmt, rs);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return contact;
	}

//	update contacts set name = ?, phone=?, email=? where cid = ?;
	private static final String SQL_UPDATE = "update contacts set name = ?, phone=?, email=? where cid = ?"; 
	@Override
	public int update(Contact contact) {
		int result = 0;
		
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = getConnection();
			stmt = conn.prepareStatement(SQL_UPDATE);
			System.out.println(SQL_UPDATE);
			stmt.setString(1, contact.getName());
			stmt.setString(2, contact.getPhone());
			stmt.setString(3, contact.getEmail());
			stmt.setInt(4, contact.getCid());
			
			result = stmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				closeResource(conn, stmt);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}

//	delete from contacts where cid = ?;
	private static final String SQL_DELETE = "delete from contacts where cid = ?";
	@Override
	public int delete(int cid) {
		int result = 0;
		
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = getConnection();
			System.out.println(SQL_DELETE);
			stmt = conn.prepareStatement(SQL_DELETE);
			
			stmt.setInt(cid, result);
			
			result = stmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				closeResource(conn, stmt);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	

	private static final String SQL_SELECT_BY_KEYWORD = 
			"select * from contacts "
			+ "where lower(name) like lower( ? ) "
			+ "or lower(phone) like lower( ? ) "
			+ "or lower(email) like lower( ? ) "
			+ "order by cid";
	
	@Override
	public List<Contact> read(String keyWord) {
		List<Contact> list = new ArrayList<>();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			System.out.println(SQL_SELECT_BY_KEYWORD);
			
			stmt = conn.prepareStatement(SQL_SELECT_BY_KEYWORD);
			String key = "%"+keyWord+"%";
			System.out.println("keyword : "+keyWord+"\tkey : "+key);
			stmt.setString(1, key);
			stmt.setString(2, key);
			stmt.setString(3, key);
			
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				
				int cid = rs.getInt(COL_CID);
				String name = rs.getString(COL_NAME);
				String phone = rs.getString(COL_PHONE);
				String email = rs.getString(COL_EMAIL);
				
				Contact contact = new Contact(cid, name, phone, email);
				
				list.add(contact);
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				closeResource(conn, stmt, rs);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return list;
	}

}
