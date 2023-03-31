package edu.java.contact.fileutil;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import edu.java.contact.model.Contact;

/*
 * 도우미 클래스 - 파일 관련(read, write, 폴더 생성) 기능을 제공하기 위한 클래스.
 * 모든 필드와 메서드는 스태틱으로 선언하면서 객체 생성은 하지 못하도록 만듦
 */

public class FileUtil {

	// 상수 정의
	public static final String DATA_DIR 	= "data";
	public static final String DATA_FILE 	= "contacts.dat";
	// 파일에 연락처 리스트를 저장하기 위해서
	
	private FileUtil() {
	}
	
	/**
	 * 연락처 데이터 파일을 저장하는 폴더가 존재하지 않으면 생성하고, File 객체를 리턴
	 * 폴더가 이미 존재 하면 생성된 폴더에 파일
	 * 
	 * @return 데이터 파일ㅇ르 저장할 폴더의 File 객체.
	 */
	
	/**
	 * 폴더가 존재 체크, 없으면 폴더 만듦.
	 * 
	 * @return File 디렉토리 리턴
	 */
	public static File initDataDir() {
		File newFolder = new File(DATA_DIR);
		
		if (newFolder.exists()) {
			System.out.println("폴더가 이미 있습니다.");
		} else {
			newFolder.mkdir();
			System.out.println("폴더 생성 성공.");
		}
		return newFolder;
	}
	
	/**
	 * readDataFromFile.
	 * argument로 전달된 file객체를 사용해서 파일에 저장된 연락처 정보를 읽고 
	 * 그 결괄  List<Contact> 타입의 객체로 리턴.
	 * 
	 * @param file 연락처 정보가 저장된 파일 경로를 가지고 있는 File 타입 객체.
	 * @return List<Contact>
	 */
	public static List<Contact> readDataFromFile(File file){
		List<Contact> result = new ArrayList<>();
		try (
				FileInputStream in = new FileInputStream(file);
				BufferedInputStream bIn = new BufferedInputStream(in);
				ObjectInputStream oIn = new ObjectInputStream(bIn);
			
			){
			result = (ArrayList<Contact>) oIn.readObject();
//			for (int i = 0;  i < result.size() ; i++) {
//				System.out.print("readDataFromFile 데이터 확인 : "+result.get(i)+"\t");
//			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * writeDataToFile
	 * 아규먼트 전달된 data를 질렬화해서 file 씀.
	 * 쓰이는 곳 데이터가 변경 되는 곳.
	 * 
	 * @param data 파일에 쓸 데이터. Contact 타입을 저장하는 리스트(List<Contact>)
	 * @param file 데이터 파일 객체
	 * 
	 */
	public static void writeDataToFile(List<Contact> contactList, File file) {
		// OOS 쓰라고!!!
		
		try(
				FileOutputStream out = new FileOutputStream(file);
				BufferedOutputStream bOut = new BufferedOutputStream(out);
				ObjectOutputStream oOut = new ObjectOutputStream(bOut);
			) {
			oOut.writeObject(contactList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * initData
	 * 연락처 데이터 파일이 있으면, 파일의 내용을 읽어서 리스트를 생성하고 리턴
	 * 연락처 데이터 파일 없으면 빈 리스트를 리턴
	 */
	public static List<Contact> initData(){
		
		List<Contact> contactList = new ArrayList<>();
		
		// 새 폴더 생성
		File newFile = new File(DATA_DIR, DATA_FILE);// 현재 폴더 밑에 파일 만들겠다.
		if (newFile.exists()) {
			System.out.println("파일에 있는 내용을 읽습니다.");
			// 파일에 저장된 데이터를 읽고, 리스트에 저장하자(de-serial)
			contactList = readDataFromFile(newFile);
		} else {
			try {
				newFile.createNewFile();
		        System.out.println("파일 생성 성공.");
		    } catch (Exception e) {
		        System.out.println("파일 생성 실패.");
		        e.printStackTrace();
		    }
		}
		return contactList;
	}

}
