package kuit.springbasic.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class loginUtil {

    public static boolean  isLogin(HttpSession userSession){
        if(userSession == null || userSession.getAttribute("user") == null ){
            return false;
        }
        return true;
    }

    public static boolean  isLogin(HttpServletRequest req){
        return isLogin(req.getSession());
    }
}
