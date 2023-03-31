package edu.java.contact05;

import java.io.File;
import java.util.List;

import edu.java.contact.fileutil.FileUtil;
import edu.java.contact.model.Contact;

/*
 * MVC(Model-View-Contorller) 아키텍쳐에서 컨트롤러 부분.
 * singletone 디자인 패턴을 적용.
 * 연락처 배열을 관리할 꺼기 때문에 저장소 접근을 함부로 못하게 하겠다.
 */
public class ContactDaoImpl implements ContactDao{
	/*
	 * 싱글톤 만들기
	 * 	1. private static 자기 클래스 타입의 변수 하나!
	 *  2. 생성자를 private로 만들기
	 *  3. 자기 클래스 변수 리턴하는 메소드 만들기 
	 */
	private static ContactDaoImpl instance = null;		// (1)
	
	private ContactDaoImpl() {							// (2)
		dataDir = FileUtil.initDataDir();// 저장할 폴더 생성
		dataFile = new File(FileUtil.DATA_DIR, FileUtil.DATA_FILE);// 저장할 파일 생성
		contacts = FileUtil.initData();// 리스트가 만들어짐
	}
	public static ContactDaoImpl getContactDaoImpl() {	// (3)
		
		if (instance == null) {//
			instance = new ContactDaoImpl();
		}
		return instance;
	}
	
	//============================================================
	
	
	// override 메소드를 위한 필드 들

	private List<Contact> contacts;
	private File dataDir;// 연락처 데이터 파일을 저장할 폴더
	private File dataFile;// 연락처 데이터 파일을 저장할 파일
	
	// 편의성 메소드
	/**
	 * 배열에 새로운 객체를 저장할 수 있는지 판별
	 * @return 배열에 저장할 수 있음(true) 저장할 수 없음(false)
	 */
//	public boolean isMemoryAvailable() {
//		return contacts.size() < 2147483647;
//	}
	/**
	 * 인덱스를 필요한 검색, 수정, 삭제 할 때, 유효한 인덱스 인지 판별
	 * @param index 판별할 인덱스 
	 * @return 유효한 인덱스(true), 무효한 인덱스(false)
	 */
	public boolean isValidIndex(int index) {
		return (index >=0) && (index < contacts.size());
	}
	
	// CRUD 메서드
	@Override
	public int create(Contact contact) {
		contacts.add(contact);
		FileUtil.writeDataToFile(contacts, dataFile);
		return 1;
	}

	@Override
	public List<Contact> read() {

		return contacts;
	}

	@Override
	public Contact read(int index) {
		if (!isValidIndex(index)) {
			return null;
		} else {
			return contacts.get(index);
		}
	}

	@Override
	public int update(int index, Contact contact) {
		if (!isValidIndex(index)) {
			return 0;
		} else {
			// 수정하려고 했던 객체 리턴
			contacts.set(index, contact);
			FileUtil.writeDataToFile(contacts, dataFile);
			return 1;
		}
	}
	@Override
	public int delete(int index) {
		if (!isValidIndex(index)) {
            return 0;
        } else {
        	// 삭제하려고 했던 객체 리턴
        	contacts.remove(index);
        	FileUtil.writeDataToFile(contacts, dataFile);
        }
        return 1;
	}
}
