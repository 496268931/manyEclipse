package com.mucfc;
import java.sql.*;

public class JdbcTest {
	//�������ݿ���������
	private static final String DBDRIVER="com.mysql.jdbc.Driver";
	//���ݿ����ӵ�ַ
	private static final String DBURL="jdbc:mysql://localhost:3306/school";//school��ʾ���ݿ�
	//���ݿ��û���
	private static final String DBUSER="root";
	//�����ϵ����ݿ�����
	private static final String DBPASS="christmas258@";
    public void testDDL(){
    	try {
        //1.ע������
        Class.forName(DBDRIVER);
        //2.��ȡ����
        Connection conn = DriverManager.getConnection(DBURL,DBUSER,DBPASS);  
        //3.����Statement����
        Statement stmt = conn.createStatement();      
        //4.׼��sql���
        String sql  ="CREATE TABLE student(sid INT PRIMARY KEY,sname VARCHAR(20),age INT)";     
        //5.ͨ��statement������sql���,����ִ�н��
        int count = stmt.executeUpdate(sql); 
        System.out.println("CREATE TABLE student......");
        //6.��ӡִ�н��
        System.out.println("Ӱ����"+count+"����¼");
        
        //ִ�в������
        System.out.println("Inserting records into the table...");
        sql = "INSERT INTO student " +
                     "VALUES (100, 'С��', 18)";
        stmt.executeUpdate(sql);
        sql = "INSERT INTO student " +
                     "VALUES (101, '����', 25)";
        stmt.executeUpdate(sql);
        sql = "INSERT INTO student " +
                     "VALUES (102, '����',  30)";
        stmt.executeUpdate(sql);
        sql = "INSERT INTO student " +
                     "VALUES(103, 'СС', 28)";
        stmt.executeUpdate(sql);
        System.out.println("Inserted records into the table...");
        
        //ִ�в��Ҳ���
        sql = "SELECT* FROM student";
        System.out.println("SELECT records FROM the table...");
        ResultSet rs = stmt.executeQuery(sql);
       //������ҽ��
        while(rs.next()){
           //�Ȼ�ȡ����
           int sid  = rs.getInt("sid");       
           String sname = rs.getString("sname");
           int age = rs.getInt("age");
           //��ӡ���
           System.out.print("sid: " + sid);
           System.out.print("  sname: " +sname);
           System.out.println("  age: " + age);
        }
        rs.close();
        //7.�ر���Դ
        try {	
        if(stmt!=null)
        {
        	stmt.close();
        }
        } catch (Exception e) {
		   e.printStackTrace();
		}
        try {		
        if(conn!=null)
        {    
        	conn.close();
        }
        } catch (Exception e) {
        	
		}
    	} catch (Exception e) {
    		 e.printStackTrace();
		}
        
}
	public static void main(String[] args) {
	/*	JdbcTest jdbcTest=new JdbcTest();
		jdbcTest.CreateTableTest();
		jdbcTest.InsertTest();
		jdbcTest.SelectTest();*/
		SqlDB sqlDB=new SqlDB();
		sqlDB.CreatTable();
		sqlDB.InsertData(309, "С��",12);
		sqlDB.InsertData(33, "С��",34);
		sqlDB.InsertData(23, "����",145);
	    sqlDB.SelectDataWithId(33);

	}
	public void CreateTableTest(){
		//��ȡ����
		Connection  cnn2=SqlDB.getconnection();
        Statement statement = null;
		try {
			statement = cnn2.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}     
        //׼��SQL���
		 String sql  ="CREATE TABLE student(sid INT PRIMARY KEY,sname VARCHAR(20),age INT)";
		 System.out.println("CREATE TABLE student......");
        //����executeQueryִ�в�ѯ�� 
		try {
			statement.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
        SqlDB.close(statement,cnn2);		
	}
	
	public void InsertTest(){
		//��ȡ����
		Connection  cnn2=SqlDB.getconnection();
        Statement statement=null;
		try {
			statement = cnn2.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}  
	     //ִ�в������
        System.out.println("Inserting records into the table...");
        String sql = "INSERT INTO student " +
                     "VALUES (100, 'С��', 18)";
        try {
			statement.executeUpdate(sql);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
        sql = "INSERT INTO student " +
                     "VALUES (101, '����', 25)";
        try {
			statement.executeUpdate(sql);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
        sql = "INSERT INTO student " +
                     "VALUES (102, '����',  30)";
        try {
			statement.executeUpdate(sql);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
        sql = "INSERT INTO student " +
                     "VALUES(103, 'СС', 28)";
        try {
			statement.executeUpdate(sql);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
        System.out.println("Inserted records into the table...");    
        SqlDB.close(statement, cnn2);	
		
	}
		
	

	public void SelectTest(){
		//��ȡ����
		Connection  cnn2=SqlDB.getconnection();
        Statement statement=null;
		try {
			statement = cnn2.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
        //׼��SQL���
        String sql = "select * from student"; 
        //����executeQueryִ�в�ѯ���
        ResultSet res = null;
		try {
			res = statement.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
        //��ѯ������res��ָ���ͷ����Ҫ��ȡ���ݱ��벻�ϵ�ָ���ѯ�������һ�У���û����һ������ʱ������0.
	 System.out.println("select  records from the table...");    
        try {
			while(res.next())
			{
			       //�Ȼ�ȡ����
		           int sid  = res.getInt("sid");       
		           String sname = res.getString("sname");
		           int age = res.getInt("age");
		           //��ӡ���
		           System.out.print("sid: " + sid);
		           System.out.print("  sname: " +sname);
		           System.out.println("  age: " + age);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
        try {
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
        SqlDB.close(statement, cnn2);	
		
	}

}
