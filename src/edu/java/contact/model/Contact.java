package edu.java.contact.model;

import java.io.Serializable;

/*
 * C(모델 뷰 컨트롤러) 아키텍쳐 모델에 해당하는 클래스
 * 특별한 기능은 없는, 순수하게 데이터만 설계하는 클래스
 * 
 *  VO(Value Object) : 값을 표현하는 객체.
 *  DTO(Data Transfer Object) : 데이터를 전달(메서드 아규먼트, 리턴 값)할 때 사용되는 객체
 *  
 */

public class Contact implements Serializable{// 파일에 write 하기위한
	
	// 오라클 DB 테이블 이름과 컬럼 이름들을 상수로 정의
	public interface Entity{
		String TBL_NAME = "CONTACTS";
		String COL_CID = "CID";
		String COL_NAME = "NAME";
		String COL_PHONE = "PHONE";
		String COL_EMAIL = "EMAIL";
	}
	
	private int cid;
	private String name;
	private String phone;
	private String email;
	
	public Contact() {}
	
	public Contact(int cid, String name, String phone, String email) {
		super();
		this.cid = cid;
		this.name = name;
		this.phone = phone;
		this.email = email;
	}
	
	public int getCid() {
		return cid;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Override
	public String toString() {
		return "Contact [cid=" + cid + ", name=" + name + ", phone=" + phone + ", email=" + email + "]";
	}
	
}
