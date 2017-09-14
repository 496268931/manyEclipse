package com.mucfc;
import java.sql.*;
/**
 * ���ݿ�������һ���򵥷�װ
 * @author �ֱ���
 * @time 2015.4.30
 */
public class SqlDB {

	// �������ݿ���������
	private static final String DBDRIVER = "com.mysql.jdbc.Driver";
	// ���ݿ����ӵ�ַ
	private static final String DBURL = "jdbc:mysql://localhost:3306/school";// school��ʾ���ݿ�
	// ���ݿ��û���
	private static final String DBUSER = "root";
	// �����ϵ����ݿ�����
	private static final String DBPASS = "christmas258@";

	/**
	 * �������ݿ�����һ��ʼ����
	 * 
	 * @return ���ݿ����ӵľ��
	 */
	public static Connection getconnection() {
		Connection conn = null;
		try {
			// 1.ע������
			Class.forName(DBDRIVER);
			// 2.��ȡ����
			conn = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
			// 3.����Statement����
			Statement stmt = conn.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;

	}

	/**
	 * �ر���Դ
	 * @param statementΪִ�������ʵ����connectionΪ���Ӷ���         
	 */
	public static void close(Statement statement, Connection connection) {
		{
			try {
				if (statement != null)
					statement.close();
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * ����һ�����ݿ��ע������ı�Ҫ���ȴ��ڵ�   
	 */
	public void CreatTable() {
		// 1.��ȡ����
		Connection connection = SqlDB.getconnection();
		// 2.׼��sql���
		String sql = "CREATE TABLE student(sid INT PRIMARY KEY,sname VARCHAR(20),age INT)";
		PreparedStatement preparedStatement = null;
		// 3.��ö���
		try {
			preparedStatement = connection.prepareStatement(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// 4.�������ݿ�ִ��SQL
		int num = 0;
		try {
			num = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("��" + num + "����¼�ܵ���Ӱ��");
		SqlDB.close(preparedStatement, connection);
	}
	/**
	 * ִ�в������
	 * @param ��Ӧ�Ĳ���    
	 */
	public void InsertData(int id, String name, int age) {
		// 1.��ȡ����
		Connection connection = SqlDB.getconnection();
		// 2.׼��sql���
		String sql = "INSERT INTO student VALUES(?,?,?)";
		PreparedStatement preparedStatement = null;
		// 3.��ö���
		try {
			preparedStatement = connection.prepareStatement(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// 4.����SQL���� ��Ҫ�����ǵڼ���������֪���������� �����һ���ʾ��SQL����һ��������int���ͣ�����ֵ����Ϊid���������
		try {
			preparedStatement.setInt(1, id);
			preparedStatement.setString(2, name);
			preparedStatement.setInt(3, age);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		// 5.�������ݿ�ִ��SQL
		int num = 0;
		try {
			num = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("��" + num + "����¼�ܵ���Ӱ��");
		SqlDB.close(preparedStatement, connection);

	}
	/**
	 * ִ�в��Ҳ���
	 * @param id ѧ�����    
	 */
	public void SelectDataWithId(int id) {
		// 1.��ȡ����
		Connection connection = SqlDB.getconnection();
		// 2.׼��sql���
		String sql = "SELECT* FROM student where sid=?";
		PreparedStatement preparedStatement = null;
		ResultSet res = null;
		// 3.��ö���
		try {
			preparedStatement = connection.prepareStatement(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		/*
		 * 4.����SQL���� ��Ҫ�����ǵڼ���������֪���������� �����һ���ʾ��SQL����һ��������int���ͣ�����ֵ����Ϊid���������
		 */
		try {
			preparedStatement.setInt(1, id);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		// 5.�������ݿ�ִ��SQL
		try {
			res = preparedStatement.executeQuery();
			while (res.next()) {
				// �Ȼ�ȡ����
				int sid = res.getInt("sid");
				String sname = res.getString("sname");
				int age = res.getInt("age");
				// ��ӡ���
				System.out.print("sid: " + sid);
				System.out.print("  sname: " + sname);
				System.out.println("  age: " + age);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		SqlDB.close(preparedStatement, connection);
	}
}
