package dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import database.DBCCon;
import entity.Customer;

public class CustomerDAO {
	
	public List<Customer>  selectAllCus() {
		List <Customer> list = new ArrayList<>(); 
		try(
				Connection con = DBCCon.getCon();
				CallableStatement cs = con.prepareCall("{call getAllCus}"); 
				ResultSet rs = cs.executeQuery();	
		){
			while(rs.next()) {
				Customer cus = new Customer();
				
					cus.setId(rs.getInt("id"));
					cus.setFullname(rs.getString("fullname"));
					cus.setGender(rs.getBoolean("gender"));
					cus.setPicture(rs.getString("picture"));
					cus.setDob(rs.getDate("dob").toLocalDate());
					list.add(cus);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}

//cài cho update Customer
	public void update(Customer cus) {
		try (
				var con = DBCCon.getCon();
				var cs = con.prepareCall("{call updateCus(?,?,?,?,?)}");
			){
			
			cs.setString(1, cus.getFullname());
			cs.setBoolean(2, cus.isGender());
			cs.setString(3, cus.getPicture());
			cs.setDate(4, Date.valueOf(cus.getDob()));
			cs.setInt(5, cus.getId());
			if(cs.executeUpdate()>0) {
				JOptionPane.showMessageDialog(null, "Update Success");
			}
			//hoặc có thể if
//			JOptionPane.showMessageDialog(null, cs.executeUpdate()>0? "Update success": "nothing to Update");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
//cài cho insert Customer
	public void insert(Customer cus) {
		try (
				var con = DBCCon.getCon();
				var cs = con.prepareCall("{call insertCus(?,?,?,?)}");
			){
			cs.setString(1, cus.getFullname());
			cs.setBoolean(2, cus.isGender());
			cs.setString(3, cus.getPicture());
			cs.setDate(4, java.sql.Date.valueOf(cus.getDob()));
			if(cs.executeUpdate()>0) {
				JOptionPane.showMessageDialog(null, "Insert Success");
			}
//			JOptionPane.showMessageDialog(null, cs.executeUpdate()>0 ? "insert success": "nothing to insert");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
//cài cho delete Customer
	public void delete(int customerId) {
		 try (
		            var con = DBCCon.getCon();
		            var cs = con.prepareCall("{call deleteCus(?)}");
		        ) {
		            cs.setInt(1, customerId);

		            int rowsAffected = cs.executeUpdate();
		            if (rowsAffected > 0) {
		                JOptionPane.showMessageDialog(null, "Delete Success");
		            } else {
		                JOptionPane.showMessageDialog(null, "Delete Failed or Customer not found");
		            }
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		    }	
// cài cho phương thức selectCus
	public List<Customer>  selectCus(int pageNumber, int rowOfPage) {
		List <Customer> list = new ArrayList<>(); 
		try(
				Connection con = DBCCon.getCon();
				CallableStatement cs = con.prepareCall("{call getCus(?,?)}"); 
				
		){
			cs.setInt(1, pageNumber);
			cs.setInt(2, rowOfPage);
			ResultSet rs = cs.executeQuery();	// nghĩ cách đóng sau khi làm xong ?????? về nhà làm
			
			while(rs.next()) {
				Customer cus = new Customer();
				
					cus.setId(rs.getInt("id"));
					cus.setFullname(rs.getString("fullname"));
					cus.setGender(rs.getBoolean("gender"));
					cus.setPicture(rs.getString("picture"));
					cus.setDob(rs.getDate("dob").toLocalDate());
					list.add(cus);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
// cài cho phương thức countCus
public int countCus() {
	int count = 0;
	try(
			var con = DBCCon.getCon();
			var cs = con.prepareCall("{call countCus}"); 
			var rs = cs.executeQuery();	
	){
		while(rs.next()) {
			count = rs.getInt("total");
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	return count;
	}
}










