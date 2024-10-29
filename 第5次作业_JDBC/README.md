### **实现的功能：**

1. **实现了作业要求的增删改查和批量插入以及可滚动结果集查看倒数第二条记录的功能**
2. **除此之外，实现了Severlet和JDBC的结合，将查询结果响应到浏览器的功能**

### 说明：运行前先刷新依赖，再在maven中执行清理，构建工件，配置tomcat服务器运行

### **各个类的解释：**

- **CRUD_demo.class 主要实现增删改查和批量插入以及可滚动结果集的实现，直接运行main函数即可**
- **CRUDException.class 用于定义自定义异常类型，方便内部try模块朝外部try模块抛出异常，**
   **使得最外层try模块能够捕获到内层try模块产生的异常**
- **Teacher.class 用于定义teacher对象结构**
- **ErrorObject.class 用于定义错误信息，方便在用户调用SelectTeacher进行查询发生错误时，**
   **回复错误类型和信息**
- **SelectTeacher.class 实现查询Teacher表内容并且响应到浏览器，结合severlet实现，**
  **资源路径为"/Teachers"**

