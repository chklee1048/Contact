package edu.java.contact.controller;

import java.util.List;

import edu.java.contact.model.Contact;

/* 
 * MVC 아키텍쳐에서 컨트롤러 역할의 인터페이스
 */
public interface ContactDao {
	
	
	/**
	 * 연락처 정보를 db에 삽입(tbl - contacts). 
	 * 
	 * @param contact 이름, 전화번호, 이메일.
	 * @return 삽입 성공한 횟수
	 */
	int create(Contact contact);
	
	
	/**
	 * 연락처 전체 검색.
	 * DB에 저장된 연락처 정보 검색(tbl - contacts).
	 * 
	 * @return Contact 타입의 리스트.
	 */
	List<Contact> read();
	
	
	/**
	 * 조건으로 검색( 조건 : cid(pk) )
	 * 
	 * @param cid 검색 조건
	 * @return cid가 존재하면 Contact 타입 객체를 리턴. 
	 * 		   cid가 존재하지 않으면 null을 리턴.
	 */
	Contact read(int cid);
	
	
	/**
	 * 주어진 검색어가 이름 또는 전화번호 또는 이메일에 포함된 연락처들의 리스트를 반환
	 * 검색어는 대소문자 구분이 없다.
	 * 
	 * @param keyWord 검색어.
	 * @return
	 */
	List<Contact> read(String keyWord);
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 조건으로 연락처 정보 수정( 조건 : cid(pk) )
	 * 
	 * @param contact 이름, 전화번호, 이메일 수정.
	 * @return 수정 성공한 횟수 
	 */
	int update(Contact contact);
	
	
	/**
	 * 조건으로 삭제( 조건 : cid(pk) )
	 * 
	 * @param cid 삭제 조건
	 * @return 삭제 성공한 횟수
	 */
	int delete(int cid);

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
