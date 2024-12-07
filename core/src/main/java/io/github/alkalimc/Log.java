import java.time.LocalDateTime;

//日志
public class Log {

    //日志分类器
    public static String logCategory(int category) {
        switch(category) {
            case 0:
                return "FATAL";
            case 1:
                return "ERROR";
            case 2:
                return "WARNING";
            case 3:
                return "INFO";
            case 4:
                return "DEBUG";
            default:
                return "日志分类器出现非法的信息等级掩码";
        }
    }

    //日志输出
    public static String logSuccessful(int category, int id, String account, String situation) {
        return LocalDateTime.now() +"[" + logCategory(category) + "] 用户: " + id + " 用户名: " + account + " 成功" + situation;
    }
    public static String illegal(int category, int id, String account, String situation, String reason) {
        return LocalDateTime.now() +"[" + logCategory(category) + "] 用户: " + id + " 用户名: " + account + " 在" + situation + "时被拒绝访问, 原因: " + reason;
    }
    public static String loginProhibited(int id, String account, String banReason, LocalDateTime banTime) {
        return LocalDateTime.now() +"[Info] 用户: " + id + " 用户名: " + account + " 由于: " + banReason + " 于: " + banTime + "被封禁，拒绝登入";
    }
}
