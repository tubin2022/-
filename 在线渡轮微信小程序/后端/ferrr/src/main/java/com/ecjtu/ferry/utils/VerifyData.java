package com.ecjtu.ferry.utils;

public class VerifyData {
    private static String phonePattern = "^1[0-9]{10}+$";
    private static String emailPattern = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";

    /**
     * 验证手机号码格式工具
     * @param phone
     * @return
     */
    public static boolean verify(String phone){
        if(phone == null){
            return false;
        }else{
            return phone.matches(phonePattern);
        }
    }

    /**
     * 验证邮箱格式工具
     * @param email
     * @return
     */
    public static boolean verifyEmail(String email){
        if(!(email == null)){
            //不为空代表用户提供了邮箱，则需要确保邮箱格式正确
            return email.matches(emailPattern);
        }else{
            //如果为空代表该属性用户可能没有提供
            return true;
        }
    }
}
