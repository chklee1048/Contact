package edu.java.contact06;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import edu.java.contact.model.Contact;
import edu.java.contact05.ContactDaoImpl;

public class ContactMain06 {
	
	private static final String[] COLUMN_NAMES= {"이름","전화번호"};

	private JFrame frame;
	private JPanel btnPanel;
	private JButton btnInsert;
	private JButton btnUpdate;
	private JButton btnDelete;
	private JButton btnSearch;
	private JScrollPane scrollPane;
	private JTable table;
	
	// 테이블의 데이터 컬럼 이름 관리
	private DefaultTableModel model;
	
	// 생성 싱글톤, DB대신 파일 처리, 파일 읽기 쓰기 처리
	private final ContactDaoImpl dao = ContactDaoImpl.getContactDaoImpl();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ContactMain06 window = new ContactMain06();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Create the application.
	 */
	public ContactMain06() {
		initialize();
		loadContactData();
	}
	/**
	 * 기본 테이블 데이터 셋팅
	 * dao에서 데이터 가져옴(그쪽 생성자에서 파일 파이프 처리 다 해서 옴)
	 */
	private void loadContactData() {
		List<Contact> contacts = dao.read();
		for(Contact c : contacts) {
			Object[] data = {c.getName(), c.getPhone()};
			model.addRow(data);
		}
	}
	
	/*
	 * ContactCreateFrame에서 새 연락처 저장을 성공했을 때 호출할 메서드
	 * 새로운 모델에다가 새로운 데이터 처음 테이블 만드는 것처럼 하기 
	 */
	public void notifyContactCreated() {
		
		resetTableModel();
		
		JOptionPane.showMessageDialog(frame, "새 연락처 저장 성공");
		
	}
	
	// 중뷁 제거
	private void resetTableModel() {
		model = new DefaultTableModel(null, COLUMN_NAMES);
		
		loadContactData();
		
		table.setModel(model);
	}
	
	public void notifyContactUpdate() {
		
		resetTableModel();
		
		JOptionPane.showMessageDialog(frame, "연락처 갱신 성공");
		
	}
	
	private void updateContact() {
		int row = table.getSelectedRow();
		
		if (row == -1) {
			JOptionPane.showMessageDialog(frame, "수정 할 행을 선택해 주세요", "알림 메시지", JOptionPane.WARNING_MESSAGE);
			
			return;
		}
		
		 /*
		  * showContactUpdateFrame 호출을 위하여
		  * 	(1) 서브 창 띄우기 위하여 엄마 위치 알려주기 위한 frame 넘기기
		  * 	(2) 창 띄웠을 때, default 정보 띄워 주기 위해서 인덱스 넘겨줌
		  * 	(3) 업데이트 성공 후 세레머니로 엄마 테이블도 교체하기 위해서 엄마 객체 주소 넘겨줌  
		  */
		
		// 
		// 
		// 수정 완료 후 엄마 테이블 업데이트를 위하여 엄마 객체 넘겨줌
		
		ContactUpdateFrame.showContactUpdateFrame(frame, row, ContactMain06.this);
		
	}

	private void deleteContact() {
		int row = table.getSelectedRow(); 
		
		if ( row != -1 ) {
			int confirm = JOptionPane.showConfirmDialog(frame, "\'"+(table.getSelectedRow()+1)+"\' 번째 행을 정말 지우겠습니까","삭제 확인", JOptionPane.YES_NO_OPTION);
			if (confirm == 0 ) {
				
				// 파일 삭제
				dao.delete(row);
				
				// view table 삭제
				model.removeRow(row);
				
				JOptionPane.showMessageDialog(frame, "새 연락처 삭제 성공");
				
			}
		} else {
			JOptionPane.showMessageDialog(frame, "삭제할 행을 선택해 주세요", "알림 메시지", JOptionPane.WARNING_MESSAGE);
		}
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 552, 470);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("연락처 Ver 0.6");
		
		btnPanel = new JPanel();
		frame.getContentPane().add(btnPanel, BorderLayout.NORTH);
		
		btnInsert = new JButton("새 연락처");
		btnInsert.setFont(new Font("D2Coding", Font.BOLD, 23));
		btnInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// this : 익명 클래스의 this
				// ContactMain06.this : 클래스 주인의 주소
				ContactCreateFrame.showContactCreateFrame(frame, ContactMain06.this);
			}
		});
		btnPanel.add(btnInsert);
		
		btnUpdate = new JButton("수정");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateContact();
			}
		});
		btnUpdate.setFont(new Font("D2Coding", Font.BOLD, 23));
		btnPanel.add(btnUpdate);
		
		btnDelete = new JButton("삭제");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteContact();
			}
		});
		btnDelete.setFont(new Font("D2Coding", Font.BOLD, 23));
		btnPanel.add(btnDelete);
		
		btnSearch = new JButton("검색");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnSearch.setFont(new Font("D2Coding", Font.BOLD, 23));
		btnPanel.add(btnSearch);
		
		scrollPane = new JScrollPane();
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		
		// 제일 날것의 테이블(컬럼만 채워진)
		model = new DefaultTableModel(null, COLUMN_NAMES);
		// TODO
		// 행 컬럼 색깔 이닛에서 바꿔라
		table.setBackground(Color.cyan);
		table.setFont(new Font("D2Coding", Font.BOLD, 20));		
		
		table.setRowHeight(23);
		table.setModel(model);
		scrollPane.setViewportView(table);
	}

}
