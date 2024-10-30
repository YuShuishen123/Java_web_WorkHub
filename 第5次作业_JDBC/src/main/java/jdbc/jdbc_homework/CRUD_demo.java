package jdbc.jdbc_homework;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;  // 用于日期自增功能

//        作业要求：
//        1. 完成teacher的CRUD练习，提供CRUD的代码。
//        2. 完成teacher表的批量插入练习，插入500个教师，每插入100条数据提交一次。
//        3. 完成可滚动的结果集练习，只查看结果集中倒数第2条数据。
//        4. 提交代码即可，但是代码中不要包含target目录。

//          建表语句：CREATE TABLE `teacher` (
//                  `id` int NOT NULL COMMENT 'id',
//                  `name` varchar(255) DEFAULT NULL COMMENT '姓名',
//                  `course` varchar(255) DEFAULT NULL COMMENT '课程',
//                  `birthday` date DEFAULT NULL COMMENT '生日',
//                   PRIMARY KEY (`id`)
//                    );

public class CRUD_demo {
    private static final String URL = "jdbc:mysql://localhost:3306/jdbc";
    private static final String USER = "root";
    private static final String PASSWORD = "mzyudada";

    static Connection conn = null;  // 创建一个连接，后续所有操作都采用这个连接进行
    static PreparedStatement ps = null;
    static ResultSet rs = null;

    @SuppressWarnings("CallToPrintStackTrace")
    public static void main(String[] args) throws SQLException {

        conn = DriverManager.getConnection(URL,USER,PASSWORD);
        System.out.println("数据库连接成功");

        try{
            // 查询功能的实现
            try{
                System.out.println("-------------------------------------------------------------------");
                System.out.println("开始执行查询实例");
                // 建立查询
                ps = conn.prepareStatement("select * from teacher where id < 10");
                // 启动查询，创建结果储存变量
                rs = ps.executeQuery();
                // 遍历输出查询结果
                while(rs.next()){
                    System.out.println("输出查询结果：");
                    System.out.println(rs.getObject(1)+" " + rs.getObject(2)+" "+rs.getObject(3)+" "+rs.getObject(4));
                }
            }catch(SQLException e){
                System.out.println("输出查询功能部分sql错误信息");  // 验证异常来源
                throw new CRUDException("查询(sql)",e);
            }catch (Exception e){
                System.out.println("输出查询功能部分java错误信息");  // 验证异常来源
                throw new CRUDException("查询(java)",e);
            }

            // 插入功能的实现
            try{
                System.out.println("-------------------------------------------------------------------");
                System.out.println("开始执行插入功能");
                conn.setAutoCommit(false);
                System.out.println("关闭事务自动提交");
                ps = conn.prepareStatement("insert into teacher(name,course,birthday) values(?,?,?)");
                // 设置插入数据
                ps.setString(1,"张三");
                ps.setString(2,"化学");
                ps.setDate(3, Date.valueOf("2024-10-28"));
                // 执行插入
                ps.executeUpdate();
                // 手动提交事务
                conn.commit();
                System.out.println("插入事务提交");
            }catch(SQLException e){
                conn.rollback();
                System.out.println("捕获到插入sql异常，回滚数据");
                throw new CRUDException("插入(sql)",e);
            }catch (Exception e){
                System.out.println("插入部分java错误信息");  // 验证异常来源
                throw new CRUDException("插入(java)",e);
            }finally {
                System.out.println("恢复事务自动提交");
                conn.setAutoCommit(true);
            }

            // 更新功能的实现
            try{
                System.out.println("-------------------------------------------------------------------");
                System.out.println("开始执行更新功能");
                conn.setAutoCommit(false);  // 关闭自动提交
                System.out.println("关闭自动事务提交");
                // 定义sql语句
                ps = conn.prepareStatement("update teacher set name=? where id=?");
                // 设置参数
                ps.setString(1,"小王");
                ps.setInt(2,1);
                // 执行更新
                ps.executeUpdate();
                // 手动提交更新
                conn.commit();
                System.out.println("更新事务提交");
            }catch(SQLException e){
                conn.rollback();
                System.out.println("捕获到更新sql异常，回滚数据");
                throw new CRUDException("更新(sql)",e);
            }catch (Exception e){
                System.out.println("更新部分java错误信息");  // 验证异常来源
                throw new CRUDException("更新(java)",e);
            }finally {
                System.out.println("恢复事务自动提交");
                conn.setAutoCommit(true);
            }

//            // 删除功能的实现
//            try{
//                System.out.println("------------------------------------------------------------------");
//                System.out.println("开始执行删除功能");
//                conn.setAutoCommit(false); //关闭自动提交
//                System.out.println("关闭自动事务提交");
//                ps = conn.prepareStatement("delete from teacher where id > ?");
//                ps.setInt(1,3);   //设置信息
//                ps.executeUpdate();  // 执行删除
//                conn.commit(); //提交事务
//                System.out.println("进行删除事务提交");
//            }catch(SQLException e){
//                conn.rollback();
//                System.out.println("捕获到删除sql异常，回滚数据");
//                throw new CRUDException("删除(sql)",e);
//
//            }catch (Exception e){
//                System.out.println("删除部分java错误信息");  // 验证异常来源
//                throw new CRUDException("删除(java)",e);
//            }finally {
//                System.out.println("恢复事务自动提交");
//                conn.setAutoCommit(true);
//            }

            // 批量插入功能的实现
            try{
                System.out.println("-----------------------------------------------------------------");
                System.out.println("开始批量插入功能");
                conn.setAutoCommit(false);
                System.out.println("关闭事务自动提交");
                ps = conn.prepareStatement("insert into teacher(name,course,birthday) values(?,?,?)");
                // 通过循环设置参数
                // 循环开始前设置起始日期
                LocalDate startDate = LocalDate.of(2024, 10, 28);
                for (int i = 1; i < 1000; i++) {
                    ps.setString(1,"教师"+i + "号"); // 名字+编号
                    ps.setString(2,"课程"+i);
                    // 设置第4个参数：日期，使用LocalDate加上i来实现增长
                    LocalDate currentDate = startDate.plusDays(i); // 当前日期加上i天
                    ps.setDate(3, Date.valueOf(currentDate)); // 转换为java.sql.Date并设置
                    // 添加到批处理
                    ps.addBatch();
                    if (i % 100 == 0) { // 每5条记录执行一次批处理
                        ps.executeBatch();
                        ps.clearBatch(); // 清空批处理缓存，防止重复插入
                    }
                }
                // 执行剩余的批处理操作
                ps.executeBatch();
                // 提交事务
                conn.commit();
                System.out.println("完成批量插入数据事务提交");
            }catch(SQLException e){
                conn.rollback();
                System.out.println("捕获到批量插入sql异常，回滚数据");
                throw new CRUDException("插入(sql)",e);
            }catch (Exception e){
                System.out.println("批量插入部分java错误信息");
                throw new CRUDException("批量插入(java)",e);
            }finally {
                System.out.println("恢复事务自动提交");
                conn.setAutoCommit(true);
            }

            //完成可滚动的结果集练习，只查看结果集中倒数第2条数据
            try{
                System.out.println("-----------------------------------------------------------------");
                System.out.println("开始执行可滚动结果集练习功能，查看结果集中的倒数第二条数据");
                String sql = "select * from teacher where id > ?";
                conn.setAutoCommit(false);
                System.out.println("关闭自动事务提交");
                // 指定结果集类型 ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY
                ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ps.setInt(1,0); // 设置参数
                // 使用ResultSet的方法来移动光标。执行查询
                rs = ps.executeQuery();
                // 移动到倒数第二行
                rs.absolute(-2);
                System.out.println("输出结果集查询结果");
                System.out.println(rs.getObject(1)+" " + rs.getObject(2)+" "+rs.getObject(3)+" "+rs.getObject(4));
            }catch(SQLException e){
                conn.rollback();
                System.out.println("捕获到可滚动结果集练习sql异常，回滚数据");
                throw new CRUDException("可滚动结果集(sql)",e);
            }catch (Exception e){
                System.out.println("捕获到可滚动结果集java错误信息");
                throw new CRUDException("可滚动结果集插入(java)",e);
            }finally {
                System.out.println("恢复事务自动提交");
                conn.setAutoCommit(true);
            }



        } catch (Exception e){  // 由于自定义函数中将sqlexception异常也归类到exception异常，所以只需要捕获exception异常即可
            System.out.println("输出异常错误信息：");
            e.printStackTrace();
        }finally {
            System.out.println("-------------------------------------------------------------------");
            // 所有操作完毕，释放资源
            if (rs != null) {
                try {
                    rs.close();
                    System.out.println("所有操作完毕，关闭rs");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                    System.out.println("所有操作完毕，关闭ps");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                    System.out.println("所有操作完毕，关闭conn");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}


