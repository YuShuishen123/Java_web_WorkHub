package jdbc.jdbc_homework;

// 自定义一个异常类，用于辨别外层try模块捕获的异常来自哪个部分的sql操作
public class CRUDException extends Exception{
    public CRUDException(String message, Throwable cause){
        super("异常来自于 " + message + " 操作", cause);
    }
}
