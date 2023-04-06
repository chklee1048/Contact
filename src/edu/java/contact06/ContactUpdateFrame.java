package edu.java.contact06;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import edu.java.contact.model.Contact;
import edu.java.contact05.ContactDaoImpl;

public class ContactUpdateFrame extends JFrame {

	private JPanel contentPane;
	private JPanel panel;
	private JLabel lblName;
	private JTextField textName;
	private JLabel lblPhone;
	private JTextField textPhone;
	private JLabel lblEmail;
	private JTextField textEmail;
	private JButton btnCreate;
	private JButton btnCancel;
	
	private Component parent;
	
	private int row;
	
	private ContactMain06 app;
	
	private ContactDaoImpl dao = ContactDaoImpl.getContactDaoImpl();

	/**
	 * Launch the application.
	 */
	// main에서 불름
	public static void showContactUpdateFrame(Component parent, int row, ContactMain06 app) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ContactUpdateFrame frame = new ContactUpdateFrame(parent, row, app);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
//	public ContactCreateFrame() {
//		initialize();// GUI 컴퍼넌트 생성 초기화
//	}
	
	public ContactUpdateFrame(Component parent, int row, ContactMain06 app) {
		this.parent = parent;
		this.row = row;
		this.app = app;
		
		initialize();
		
		readContact();
	}

	private void readContact() {
		
		List<Contact> list = dao.read();
		Contact contact = list.get(row);
		
		textName.setText(contact.getName());
		textPhone.setText(contact.getPhone());
		textEmail.setText(contact.getEmail());
		
	}
	
	private void updateContact() {
		String name =textName.getText();
		String phone =textPhone.getText();
		String email =textEmail.getText();
		
		Contact contact = new Contact(0, name, phone, email);
		
		
		int confirm = JOptionPane.showConfirmDialog(ContactUpdateFrame.this, "정말로 업데이트 할까요","알림 메시지", JOptionPane.YES_NO_OPTION);
		if (confirm == JOptionPane.YES_NO_OPTION) {
			dao.update(row, contact);
			app.notifyContactUpdate();
			dispose();
			
		}
		
		
		
		
	}

	public void initialize() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		// 부모 프레임 찾기
		int paX = 100;
		int paY = 100;
		int paW = 365;
		int paH = 300;
		
		if (parent != null) {
			paW = parent.getWidth();
			paX = parent.getX() + paW - 15;
			paY = parent.getY()+(parent.getHeight()-paH);
		}
		
		setBounds(paX, paY, 365, 300);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setTitle("새 연락처 수정하기");

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		lblName = new JLabel("이   름");
		lblName.setFont(new Font("D2Coding", Font.BOLD, 20));
		lblName.setHorizontalAlignment(SwingConstants.CENTER);
		lblName.setBounds(23, 21, 89, 49);
		panel.add(lblName);
		
		textName = new JTextField();
		textName.setFont(new Font("굴림체", Font.BOLD, 20));
		textName.setBounds(124, 21, 185, 43);
		panel.add(textName);
		textName.setColumns(10);
		
		lblPhone = new JLabel("전화번호");
		lblPhone.setHorizontalAlignment(SwingConstants.CENTER);
		lblPhone.setFont(new Font("D2Coding", Font.BOLD, 20));
		lblPhone.setBounds(23, 80, 89, 49);
		panel.add(lblPhone);
		
		textPhone = new JTextField();
		textPhone.setFont(new Font("D2Coding", Font.BOLD, 20));
		textPhone.setColumns(10);
		textPhone.setBounds(124, 80, 185, 43);
		panel.add(textPhone);
		
		lblEmail = new JLabel("이 메 일");
		lblEmail.setHorizontalAlignment(SwingConstants.CENTER);
		lblEmail.setFont(new Font("D2Coding", Font.BOLD, 20));
		lblEmail.setBounds(23, 139, 89, 49);
		panel.add(lblEmail);
		
		textEmail = new JTextField();
		textEmail.setFont(new Font("D2Coding", Font.BOLD, 20));
		textEmail.setColumns(10);
		textEmail.setBounds(124, 139, 185, 43);
		panel.add(textEmail);
		
		JPanel btnPanel = new JPanel();
		contentPane.add(btnPanel, BorderLayout.SOUTH);
		
		btnCreate = new JButton("수정하기");
		btnCreate.setFont(new Font("D2Coding", Font.BOLD, 20));
		btnCreate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateContact();
			}
		});
		
		btnPanel.add(btnCreate);
		
		btnCancel = new JButton("취소");
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();// 현재 창 닫기
			}
		});
		btnCancel.setFont(new Font("D2Coding", Font.BOLD, 20));
		btnPanel.add(btnCancel);
	}//init


}
