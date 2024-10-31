package jdbc.sgg_servelet_demo;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;


@WebServlet(name = "helloServlet2", value = "/hello-servlet2")
public class MyServlet2 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 设置响应内容类型
        response.setContentType("text/html");

        // 获取PrintWriter对象，用于向客户端发送响应
        PrintWriter out = response.getWriter();

        // 发送响应内容
        out.println("<h1>Hello, World!</h1>");

        StringBuffer url = request.getRequestURL(); // 获取url
        System.out.println("url:"+url);

        String uri = request.getRequestURI();
        System.out.println("uri:"+uri);
    }
}
     