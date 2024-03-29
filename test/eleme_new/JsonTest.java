package eleme_new;

import java.util.UUID;

import org.eleme.qianggou.biz.bo.QueryOrdersBo;
import org.json.JSONObject;

public class JsonTest { 
    public static String getUUID() {   
        UUID uuid = UUID.randomUUID();   
        String str = uuid.toString();   
        // 去掉"-"符号   
        String temp = str.substring(0, 8) + str.substring(9, 13) + str.substring(14, 18) + str.substring(19, 23) + str.substring(24);   
        return str+","+temp;   
    }   
    //获得指定数量的UUID   
    public static String[] getUUID(int number) {   
        if (number < 1) {   
            return null;   
        }   
        String[] ss = new String[number];   
        for (int i = 0; i < number; i++) {   
            ss[i] = getUUID();   
        }   
        return ss;   
    }   
  
    public static void main(String[] args) {   
		//String jsonString = JSONObject.valueToString(ErrorEnum.USER_AUTH_FAIL); 
        //System.out.println(jsonString);    
    	QueryOrdersBo queryOrdersBo = new QueryOrdersBo();
    	queryOrdersBo.setId("123qwe");
    	queryOrdersBo.setTotal(11);
    	queryOrdersBo.setItems(123, 11);
		String jsonString = JSONObject.valueToString(queryOrdersBo); 
        System.out.println(jsonString);  
    }  
}
