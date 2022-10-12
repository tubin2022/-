package com.ecjtu.ferry.com.ecjtu;

import com.ecjtu.ferry.utils.JwtUtil;
import org.junit.Test;

public class tokenTest {
    @Test
    public void test(){
        String token = JwtUtil.getToken("15180204814");
        System.out.println(token);
    }
    @Test
    public void test2(){
        String phone = JwtUtil.parseToken("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxNTE4MDIwNDgxNCIsImV4cCI6MTY1NDQ4MjA5NX0.syHlCyQ9z45QUBsO6MtmTF3jPprNOgTx71MFPdh09ECAIJBQI4p67lWCU-Wu4RVCxotJnA0_vxYvys2aL7G8cg");
        System.out.println(phone);
    }


}
