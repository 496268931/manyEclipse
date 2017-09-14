package com.mucfc;
import java.sql.*;

public class JdbcTest {
	//定义数据库驱动程序
	private static final String DBDRIVER="com.mysql.jdbc.Driver";
	//数据库连接地址
	private static final String DBURL="jdbc:mysql://localhost:3306/school";//school表示数据库
	//数据库用户名
	private static final String DBUSER="root";
	//电脑上的数据库密码
	private static final String DBPASS="christmas258@";
    public void testDDL(){
    	try {
        //1.注册驱动
        Class.forName(DBDRIVER);
        //2.获取连接
        Connection conn = DriverManager.getConnection(DBURL,DBUSER,DBPASS);  
        //3.创建Statement对象
        Statement stmt = conn.createStatement();      
        //4.准备sql语句
        String sql  ="CREATE TABLE student(sid INT PRIMARY KEY,sname VARCHAR(20),age INT)";     
        //5.通过statement对象发送sql语句,返回执行结果
        int count = stmt.executeUpdate(sql); 
        System.out.println("CREATE TABLE student......");
        //6.打印执行结果
        System.out.println("影响了"+count+"条记录");
        
        //执行插入操作
        System.out.println("Inserting records into the table...");
        sql = "INSERT INTO student " +
                     "VALUES (100, '小文', 18)";
        stmt.executeUpdate(sql);
        sql = "INSERT INTO student " +
                     "VALUES (101, '大林', 25)";
        stmt.executeUpdate(sql);
        sql = "INSERT INTO student " +
                     "VALUES (102, '阿白',  30)";
        stmt.executeUpdate(sql);
        sql = "INSERT INTO student " +
                     "VALUES(103, '小小', 28)";
        stmt.executeUpdate(sql);
        System.out.println("Inserted records into the table...");
        
        //执行查找操作
        sql = "SELECT* FROM student";
        System.out.println("SELECT records FROM the table...");
        ResultSet rs = stmt.executeQuery(sql);
       //输出查找结果
        while(rs.next()){
           //先获取数据
           int sid  = rs.getInt("sid");       
           String sname = rs.getString("sname");
           int age = rs.getInt("age");
           //打印结果
           System.out.print("sid: " + sid);
           System.out.print("  sname: " +sname);
           System.out.println("  age: " + age);
        }
        rs.close();
        //7.关闭资源
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
		sqlDB.InsertData(309, "小红",12);
		sqlDB.InsertData(33, "小灰",34);
		sqlDB.InsertData(23, "阿大",145);
	    sqlDB.SelectDataWithId(33);

	}
	public void CreateTableTest(){
		//获取连接
		Connection  cnn2=SqlDB.getconnection();
        Statement statement = null;
		try {
			statement = cnn2.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}     
        //准备SQL语句
		 String sql  ="CREATE TABLE student(sid INT PRIMARY KEY,sname VARCHAR(20),age INT)";
		 System.out.println("CREATE TABLE student......");
        //调用executeQuery执行查询语 
		try {
			statement.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
        SqlDB.close(statement,cnn2);		
	}
	
	public void InsertTest(){
		//获取连接
		Connection  cnn2=SqlDB.getconnection();
        Statement statement=null;
		try {
			statement = cnn2.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}  
	     //执行插入操作
        System.out.println("Inserting records into the table...");
        String sql = "INSERT INTO student " +
                     "VALUES (100, '小文', 18)";
        try {
			statement.executeUpdate(sql);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
        sql = "INSERT INTO student " +
                     "VALUES (101, '大林', 25)";
        try {
			statement.executeUpdate(sql);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
        sql = "INSERT INTO student " +
                     "VALUES (102, '阿白',  30)";
        try {
			statement.executeUpdate(sql);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
        sql = "INSERT INTO student " +
                     "VALUES(103, '小小', 28)";
        try {
			statement.executeUpdate(sql);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
        System.out.println("Inserted records into the table...");    
        SqlDB.close(statement, cnn2);	
		
	}
		
	

	public void SelectTest(){
		//获取连接
		Connection  cnn2=SqlDB.getconnection();
        Statement statement=null;
		try {
			statement = cnn2.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
        //准备SQL语句
        String sql = "select * from student"; 
        //调用executeQuery执行查询语句
        ResultSet res = null;
		try {
			res = statement.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
        //查询结束后res会指向表头，想要获取数据必须不断地指向查询结果的下一行，当没有下一行数据时，返回0.
	 System.out.println("select  records from the table...");    
        try {
			while(res.next())
			{
			       //先获取数据
		           int sid  = res.getInt("sid");       
		           String sname = res.getString("sname");
		           int age = res.getInt("age");
		           //打印结果
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
