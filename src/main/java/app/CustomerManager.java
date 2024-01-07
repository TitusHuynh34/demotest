package app;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

import database.DBCCon;
import entity.Customer;

import javax.swing.DefaultRowSorter;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import dao.CustomerDAO;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import javax.swing.JCheckBox;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import com.toedter.calendar.JDateChooser;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import java.awt.Rectangle;

public class CustomerManager extends JFrame {

	private static final long serialVersionUID = 1L;
	private JScrollPane scrollPane;
	private JTable table;
	private JTextField txtid;
	private JTextField txtfullname;
	private JCheckBox chkgender;
	private JTextField txtpicture;
	private JTextField txtsearch;
	private JButton btnupdate;
	private JButton btnInsertCustomer;
	private JDateChooser dateChooser;
	private JButton btndelete;
	private JButton btnFirst;
	private JButton btnPrevious;
	private JButton btnNext;
	private JButton btnLast;
	private JComboBox comboBox;
	private JTextField textField;
	private JLabel lblTotal;
	private JLabel lblStatus;
	
	Integer pageNumber = 1; //int page = 1;
	Integer rowOfPage = 10;
	Integer totalCount = 0;
	Double totalPage = 0.0;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CustomerManager frame = new CustomerManager();
					frame.setVisible(true);


				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public CustomerManager() {
		setTitle("Customer");
		setBounds(100, 100, 795, 708);
		getContentPane().setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 87, 631, 332);
		getContentPane().add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				tableMouseClicked(e);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				tableMousePressed(e);
			}
		});
		table.setAutoCreateRowSorter(true);
		loadCustomer();
		scrollPane.setViewportView(table);
		
		txtid = new JTextField();
		txtid.setEditable(false);
		txtid.setBorder(new TitledBorder(null, "id:", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		txtid.setBounds(10, 430, 117, 45);
		getContentPane().add(txtid);
		txtid.setColumns(10);
		
		txtfullname = new JTextField();
		txtfullname.setColumns(10);
		txtfullname.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "fullname:", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		txtfullname.setBounds(137, 430, 145, 48);
		getContentPane().add(txtfullname);
		
		chkgender = new JCheckBox("Gender");
		chkgender.setBounds(288, 433, 86, 41);
		getContentPane().add(chkgender);
		
		txtpicture = new JTextField();
		txtpicture.setColumns(10);
		txtpicture.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "picture:", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		txtpicture.setBounds(380, 430, 121, 45);
		getContentPane().add(txtpicture);
		
		txtsearch = new JTextField();
		txtsearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtsreachActionPerformed(e);
			}
		});
		txtsearch.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Search: ", TitledBorder.LEADING, TitledBorder.ABOVE_TOP, null, new Color(255, 128, 192)));
		txtsearch.setBounds(10, 34, 124, 42);
		getContentPane().add(txtsearch);
		txtsearch.setColumns(10);
		
		btnupdate = new JButton("Update Customer");
		btnupdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnupdateActionPerformed(e);
			}
		});
		btnupdate.setBounds(651, 84, 118, 23);
		getContentPane().add(btnupdate);
		
		btnInsertCustomer = new JButton("Insert Customer");
		btnInsertCustomer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnInsertCustomerActionPerformed(e);
			}
		});
		btnInsertCustomer.setBounds(651, 118, 118, 23);
		getContentPane().add(btnInsertCustomer);
		
		dateChooser = new JDateChooser();
		dateChooser.setDateFormatString("dd-MM-yyyy");
		dateChooser.setDate(new Date());
		dateChooser.setBorder(new TitledBorder(null, "dob:", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		dateChooser.setBounds(511, 430, 130, 41);
		getContentPane().add(dateChooser);
		
		btndelete = new JButton("Delete Customer");
		btndelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btndeleteActionPerformed(e);
			}
		});
		btndelete.setBounds(651, 152, 118, 23);
		getContentPane().add(btndelete);
		
		btnFirst = new JButton("First");
		btnFirst.setBounds(20, 496, 89, 23);
		getContentPane().add(btnFirst);
		
		btnPrevious = new JButton("Previous");
		btnPrevious.setBounds(137, 496, 89, 23);
		getContentPane().add(btnPrevious);
		
		btnNext = new JButton("Next");
		btnNext.setBounds(412, 496, 89, 23);
		getContentPane().add(btnNext);
		
		btnLast = new JButton("Last");
		btnLast.setBounds(525, 496, 89, 23);
		getContentPane().add(btnLast);
		
		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"10", "20", "30", "40", "50"}));
		comboBox.setBounds(252, 496, 122, 22);
		getContentPane().add(comboBox);
		
		textField = new JTextField();
		textField.setBounds(new Rectangle(0, 1, 1, 0));
		textField.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 128, 192)));
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setText("1");
		textField.setBounds(252, 529, 122, 20);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		lblTotal = new JLabel("total customer:  0");
		lblTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTotal.setBounds(525, 532, 89, 33);
		getContentPane().add(lblTotal);
		
		lblStatus = new JLabel("page 1 of 0");
		lblStatus.setHorizontalAlignment(SwingConstants.LEFT);
		lblStatus.setBounds(20, 530, 89, 33);
		getContentPane().add(lblStatus);

	}
//lấy dữ liệu từ bảng customer bên dưới csdl
// đổ vào jtable
	public void loadCustomer() {
		
		DefaultTableModel model = new DefaultTableModel();
		
		model.addColumn("id");
		model.addColumn("fullname");
		model.addColumn("gender");
		model.addColumn("picture");
		model.addColumn("dob");
		
		CustomerDAO dao = new CustomerDAO();
		dao.selectAllCus()
			.stream()
			.forEach(
			cus -> model.addRow(new Object[] {cus.getId(), cus.getFullname(), cus.isGender(), cus.getPicture(), cus.getDob()})
			);
		table.setModel(model);
	}
//dùng để sreach
	protected void txtsreachActionPerformed(ActionEvent e) {
		String find = txtsearch.getText();
		DefaultRowSorter<?, ?> sorter = (DefaultRowSorter<?, ?>)table.getRowSorter();
		sorter.setRowFilter(RowFilter.regexFilter(find));
		sorter.setSortKeys(null);
		
	}
//dùng để click khi chọn để đỗ ra dử liệu
	protected void tableMouseClicked(MouseEvent e) {
		int rowindex = table.getSelectedRow();
		txtid.setText(table.getValueAt(rowindex, 0).toString());
		txtfullname.setText(table.getValueAt(rowindex, 1).toString());
		chkgender.setSelected(table.getValueAt(rowindex, 2).toString().equals("true")?true:false);
		txtpicture.setText(table.getValueAt(rowindex, 3).toString());
		
//này là code của khúc có dob
		try {
			dateChooser.setDate(
					
				new SimpleDateFormat("yyyy-MM-dd").parse(table.getValueAt(rowindex, 4).toString()));
		
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}
	
//set nút này thành Update Customer
	protected void btnupdateActionPerformed(ActionEvent e) {
		var cus = new Customer();
		cus.setId(Integer.parseInt(txtid.getText()));
		cus.setFullname(txtfullname.getText());
		cus.setGender(chkgender.isSelected());
		cus.setPicture(txtpicture.getText());
		cus.setDob(
				LocalDate.ofInstant(dateChooser.getDate().toInstant(), ZoneId.systemDefault())
				);
		CustomerDAO dao = new CustomerDAO();
		dao.update(cus);

//load lại dữ liệu sau khi update
		refresh();
	}
	
	private void refresh() {
	DefaultTableModel model = (DefaultTableModel)table.getModel();
		model.setRowCount(0);//xóa dử liệu bảng đổ lại cái mới
		
		CustomerDAO dao = new CustomerDAO();
		
		dao.selectAllCus()
			.stream()
			.forEach(
			cus -> model.addRow(new Object[] {cus.getId(), cus.getFullname(), cus.isGender(), cus.getPicture(), cus.getDob()})
			);
	}
	
//set nút insert Customer
	protected void btnInsertCustomerActionPerformed(ActionEvent e) {
		var cus = new Customer();
		cus.setFullname(txtfullname.getText());
		cus.setGender(chkgender.isSelected());
		cus.setPicture(txtpicture.getText());
		cus.setDob(
				LocalDate.ofInstant(dateChooser.getDate().toInstant(), ZoneId.systemDefault())
				);
		CustomerDAO dao = new CustomerDAO();
		dao.insert(cus);

//load lại dữ liệu sau khi update
		refresh();
	}

// set nút delete dữ liệu
	protected void btndeleteActionPerformed(ActionEvent e) {
//		var cus = new Customer();
//		cus.setId(Integer.parseInt(txtid.getText()));
//		CustomerDAO dao = new CustomerDAO();
//		dao.delete(cus);
//		//load lại dữ liệu sau khi update
//				refresh();
				
		int selectedRowIndex = table.getSelectedRow();
	    if (selectedRowIndex != -1) { // Check if a row is selected
	        int customerId = (int) table.getValueAt(selectedRowIndex, 0); // Assuming the ID is in the first column
	        CustomerDAO dao = new CustomerDAO();
	        dao.delete(customerId);
	        
	        refresh(); // Load lại dữ liệu sau khi xóa
	    } else {
	        // Handle case where no row is selected, display an error message or take appropriate action
	    }
	}
	protected void tableMousePressed(MouseEvent e) {
		
//		code dưới là chưa phát sinh sự kiện, ở trên là phát sinh để nó có thể xóa
		JPopupMenu menu = new JPopupMenu("delete...");
		JMenuItem item = new JMenuItem("delete row",'d');
		
		//phát sinh sự kiện của action khi click chuột phải delete	
		menu.add(item);
		item.addActionListener(this::deleteRow);
		
		if(e.getButton() == MouseEvent.BUTTON3) {
			int r = table.rowAtPoint(e.getPoint());
			//khúc này là cho con trỏ chuột nó nổi đúng cái hàng
			table.setRowSelectionInterval(r, r);
			menu.show(table, e.getX(),e.getY());
		}
	}

//khúc này là sinh code cho dòng 287
	private void deleteRow(ActionEvent actionevent1) {
		var cus = new Customer();
		int rowindex = table.getSelectedRow();
		
		cus.setId(Integer.parseInt(table.getValueAt(rowindex, 0).toString()));
		CustomerDAO dao = new CustomerDAO();
		
		dao.delete(cus); // không biết sao sai?
		//load lại dữ liệu sau khi update
				refresh(); 
	}
}







//	protected void btnNewButtonActionPerformed(ActionEvent e) {
//		
//		try(Connection con = DBCCon.getCon();){
//			
//		JOptionPane.showConfirmDialog(this, "connection success");
//		
//		} catch (Exception e2) {
//			e2.printStackTrace();
			
//		}finally{
//			try {
//				con.close();
//			} catch (SQLException e1) {
//				e1.printStackTrace();
//			}




























