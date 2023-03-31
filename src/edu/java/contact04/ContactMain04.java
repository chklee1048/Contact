package edu.java.contact04;

import java.util.Scanner;

import edu.java.contact.menu.Menu;
import edu.java.contact.model.Contact;

// MVC(Model-View-Controller) 아키텍쳐에서 view에 해당하는 클래스
// UI를 담당하는 클래스

public class ContactMain04 {
	
	private Scanner scanner = new Scanner(System.in);
	ContactDaoImpl dao = ContactDaoImpl.getContactDaoImpl();
	
	public static void main(String[] args) {
		
		System.out.println("***연락처 프로그램 v0.4 ***");
		
		ContactMain04 app = new ContactMain04();
		
		boolean run = true;
		
		// 더미
		for (int i = 0; i < 5; i++) {
			
			Contact contact = new Contact(i, i+"name", i+"phone", i+"email");
			app.dao.create(contact);
		}
//		
		while(run) {
			int n = app.showMainMenu();
			Menu menu = Menu.getValue(n);
			
			switch (menu) {
			case QUIT:
				
				run = false;
				break;
			case CREATE:
				app.insertNewContact();
				break;
			case READ_ALL:
				app.selectAllContacts();
				break;
			case READ_BY_INDEX:
				app.selectContactByIndex();
				break;
			case UPDATE:
				app.updateContact();
				break;
			case DELETE:
				app.deleteContact();
				break;	
			case UNKNOWN:
				System.out.println("메인 메뉴 번호를 확인하세요...");
				break;
			
			}
			
		}//while
		
		System.err.println("프로그램 종료");
		
	}//main
	
	public void deleteContact() {
		System.out.println("------인덱스로 삭제하기------");
		
		System.out.print("삭제할 연락처 인덱스 입력>>");
		int index = inputNumber();
		
		if (!dao.isValidIndex(index)) {
			System.out.println(">>> 해당 인덱스에는 연락처 정보가 없습니다.");
			return ;
		} else {
			int result = dao.delete(index);
			if (result == 1) {
				System.out.println("연락처 삭제 성공");
			} else {
				System.out.println("연락처 삭제 실패");
			}
		}
	}

	public void updateContact() {
		System.out.println("------인덱스로 수정하기------");
		
		System.out.print("수정할 연락처 인덱스 입력>>");
		int index = inputNumber();
		
		if (!dao.isValidIndex(index)) {
			System.out.println(">>> 해당 인덱스에는 연락처 정보가 없습니다.");
			return ;
		} else {
			Contact before = dao.read(index);
			System.out.println(">>수정 전"+before);
			
			System.out.print("이름을 입력하세요.>>");
			String name = scanner.nextLine();
			System.out.print("전화번호 입력하세요.>>");
			String phone = scanner.nextLine();
			System.out.print("이메일 입력하세요.>>");
			String email = scanner.nextLine();
			
			Contact after = new Contact(0,name,phone,email);
			
			int result = dao.update(index, after);
			if (result == 1) {
				System.out.println("연락처 없데이트 성공");
			} else {
				System.out.println("연락처 없데이트 실패");
			}
			
			System.out.println(">>수정 후"+dao.read(index));
		}
	}//updateContact()

	public void selectContactByIndex() {
		System.out.println("------인덱스로 검색하기------");
		
		System.out.print("검색할 연락처 인덱스 입력>>");
		int index = inputNumber();
		
		Contact contact = dao.read(index);
		
		if (contact != null) {
			System.out.println(contact);
		} else {
			System.out.println(">>> 해당 인덱스에는 연락처 정보가 없습니다.");
		}
		
		
		
	}

	public void selectAllContacts() {
		System.out.println("------연락처 전체 목록------");
		
		for(Contact c : dao.read()) {
			System.out.println(c); 
		}
		
	}

	public void insertNewContact() {
		System.out.println("------새 연락처 저장------");
		
		System.out.print("이름을 입력하세요.>>");
		String name = scanner.nextLine();
		System.out.print("전화번호 입력하세요.>>");
		String phone = scanner.nextLine();
		System.out.print("이메일 입력하세요.>>");
		String email = scanner.nextLine();
		
		Contact contact = new Contact(0, name, phone, email);
		
		int result = dao.create(contact);
		
		if (result == 1) {
			System.out.println("새 연락처 저장 성공!!");
		}else {
			System.out.println("새 연락처 저장 실패!!");
		}
		
		
		
	}//insertNewContact

	public int showMainMenu() {
		System.out.println();
		System.out.println("------");
		System.out.println("[0]종료 [1]새 연락처 [2]전체 목록 [3]검색 [4]수정 [5]삭제 ");
		System.out.println("------");
		System.out.print("선택>");
		
		int n = inputNumber();
		return n;
	}// showMainMenu

	public int inputNumber() {
		int n = 0;
		while(true) {
			try {
				n = Integer.parseInt(scanner.nextLine());
				break;
			} catch (Exception e) {
				System.out.print("정수를 입력해 주세요 >>");
			}
		}
		return n;
	}//inputNumber()
	
	
}
