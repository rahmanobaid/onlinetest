package com.onlinetest.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import com.onlinetest.models.Question;
import com.onlinetest.models.Test;
import com.onlinetest.models.User;

public class JdbcUtil {

	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://localhost/onlinetest";
	private static Connection conn;
	private static final String SEPARATER = ", '";
	private static final String QUOTE = "'";
	// Database credentials
	private static final String USER = "root";
	private static final String PASS = "root";

	public static Connection getConnection() throws SQLException, ClassNotFoundException {
		Class.forName(JDBC_DRIVER);
		Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
		return connection;
	}

	public static void save(User user) throws MySQLIntegrityConstraintViolationException {
		Statement stmt = null;
		try{
			conn = getConnection();
			System.out.println("Inserting records into the table...");
			stmt = conn.createStatement();
			StringBuilder sql = new StringBuilder("INSERT INTO user (name, password, role, status, created_on, dob, father_name, "
					+ "mother_name, address, branch, course, session, email, roll_no, gender, contact) VALUES ");
			sql.append("(").append(QUOTE).append(user.getName()).append(QUOTE);
			sql.append(SEPARATER).append(user.getPassword()).append(QUOTE);
			sql.append(SEPARATER).append(user.getRole()).append(QUOTE);
			sql.append(SEPARATER).append(user.getStatus()).append(QUOTE);
			sql.append(SEPARATER).append(user.getCreatedOn()).append(QUOTE);
			sql.append(SEPARATER).append(user.getDob()).append(QUOTE);
			sql.append(SEPARATER).append(user.getFatherName()).append(QUOTE);
			sql.append(SEPARATER).append(user.getMotherName()).append(QUOTE);
			sql.append(SEPARATER).append(user.getAddress()).append(QUOTE);
			sql.append(SEPARATER).append(user.getBranch()).append(QUOTE);
			sql.append(SEPARATER).append(user.getCourse()).append(QUOTE);
			sql.append(SEPARATER).append(user.getSession()).append(QUOTE);
			sql.append(SEPARATER).append(user.getEmail()).append(QUOTE);
			sql.append(SEPARATER).append(user.getRoll()).append(QUOTE);
			sql.append(SEPARATER).append(user.getGender()).append(QUOTE);
			sql.append(SEPARATER).append(user.getContact()).append(QUOTE).append(")");
			
			System.out.println(sql);
			stmt.executeUpdate(sql.toString());
			System.out.println("Inserted records into the table...");

		}catch(MySQLIntegrityConstraintViolationException e) {
			throw e;
		}catch(SQLException se){
			se.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(stmt!=null && conn != null && !conn.isClosed())
					conn.close();
			}catch(SQLException se){
			}
			try{
				if(conn!=null) conn.close();
			}catch(SQLException se){
				se.printStackTrace();
			}
		}
		System.out.println("Goodbye!");
	}

	public static User findByNameAndPassword(String username, String password) {
		Statement stmt = null;
		try{
			conn = getConnection();
			System.out.println("Getting records into the table...");
			stmt = conn.createStatement();
			String sql = "select * from onlinetest.user where name = '" + username + "' and password = '" + password + "' limit 1";
			System.out.println(sql);
			ResultSet rs = stmt.executeQuery(sql);
			System.out.println("Inserted records into the table...");
			if(rs.next()){
		         //Retrieve by column name
		         Long userid  = rs.getLong("userid");
		         String role = rs.getString("role");
		         String status = rs.getString("status");
		         User user = new User();
		         user.setUserId(userid);
		         user.setName(username);
		         user.setPassword(password);
		         user.setStatus(status);
		         user.setRole(role);
		         return user;
		      }
		}catch(SQLException se){
			se.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(stmt!=null && conn != null && !conn.isClosed())
					conn.close();
			}catch(SQLException se){
			}
			try{
				if(conn!=null) conn.close();
			}catch(SQLException se){
				se.printStackTrace();
			}
		}
		System.out.println("Goodbye!");
		return null;
	}

	public static List<Question> findQuestionPaperBySemester(Test test) {
		Statement stmt = null;
		try{
			conn = getConnection();
			System.out.println("Getting records from the table...");
			stmt = conn.createStatement();
			String sql = "select * from question where semester = " + test.getSemester() + " and branch = '" + test.getBranch() + "' and subject = '"  + test.getSubject() + "'";
			System.out.println(sql); 
			ResultSet rs = stmt.executeQuery(sql);
			System.out.println("Got records into the table...");
			List<Question> questions = new ArrayList<>();
			while(rs.next()) {
				Question question = new Question();
				question.setQuestion(rs.getString("question"));
				question.setOption1(rs.getString("option1"));
				question.setOption2(rs.getString("option2"));
				question.setOption3(rs.getString("option3"));
				question.setOption4(rs.getString("option4"));
				question.setId(rs.getInt("id"));
				questions.add(question);
			}
			return questions;
		}catch(SQLException se){
			se.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(stmt!=null && conn != null && !conn.isClosed())
					conn.close();
			}catch(SQLException se){
			}
			try{
				if(conn!=null) conn.close();
			}catch(SQLException se){
				se.printStackTrace();
			}
		}
		System.out.println("Goodbye!");
		return null;
	}
}
