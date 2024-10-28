package jdbc.jdbc_homework;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.dbutils.DbUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "selectTeacher",value = "/Teachers")
public class SelectTeacher extends HttpServlet {
    // 数据库数据
    // 替换为实际数据库名
    // JDBC 驱动名及数据库 URL
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/jdbc";
    private static final String USER = "root";
    private static final String PASSWORD = "mzyudada";
    private final ObjectMapper objectMapper = new ObjectMapper();

    @SuppressWarnings("CallToPrintStackTrace")
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    PrintWriter out = response.getWriter();

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        //查询部分
        try{
            Class.forName(JDBC_DRIVER); // 注册驱动程序
            //创建连接
            conn = DriverManager.getConnection(URL,USER,PASSWORD);
            //执行查询
            String sql = "select * from Teacher"; // 查询语句
            pstmt = conn.prepareStatement(sql); // 执行查询
            rs = pstmt.executeQuery(); // 查询结果
            List<Teacher> Teachers = new ArrayList<>(); // 列表储存查询结果

            while (rs.next()) {
                Teacher teacher = new Teacher();
                teacher.setId(rs.getInt("id")); // 确保数据库字段名和实体类的属性名一致
                teacher.setName(rs.getString("name"));
                teacher.setCourse(rs.getString("course"));
                teacher.setBirthday(rs.getString("birthday"));
                Teachers.add(teacher);
            }
            response.getWriter().write(objectMapper.writeValueAsString(Teachers));
        } catch (SQLException e) {
            //创建一个错误类用于返回
            ErrorObject errorObject = new ErrorObject();
            errorObject.setErrorCode(666);
            errorObject.setErrorMessage("查询失败");
            response.getWriter().write(objectMapper.writeValueAsString(errorObject));
            e.printStackTrace();
        }catch (Exception e){
                ErrorObject errorObject = new ErrorObject();
                errorObject.setErrorCode(555);
                errorObject.setErrorMessage("服务器问题");
                out.print(objectMapper.writeValueAsString(errorObject));
                e.printStackTrace();
        }finally {
            DbUtils.closeQuietly(rs);
            DbUtils.closeQuietly(pstmt);
            DbUtils.closeQuietly(conn);
            System.out.println("关闭连接");
        }
    }
}
