package edu.java.contact.ojdbc;

public interface OracleConnect {
	/*
	 * 인터페이스의 필드는 public static final만 가능 .그래서 생략 함.
	 * 
	 */
	// 우리가 sql developer 접속 할 때 접속 세부정보에 나오는 text
	// server ip : scott 
	// port 	 : 1521
	// sid  	 : xe
	String URL  ="jdbc:oracle:thin:@localhost:1521:xe";// 서버 아이피 주소
	// 오라클 디비 서버 계정
	String USER ="scott";
	// 오라클 디비 서버 계정 비밀번호
	String PASSWORD ="tiger";
	
	
}
