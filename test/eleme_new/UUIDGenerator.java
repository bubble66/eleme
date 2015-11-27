package eleme_new;

import java.io.Serializable;
import java.util.UUID;

import org.eleme.qianggou.biz.bo.QueryOrdersBo;
import org.eleme.qianggou.common.util.SendJson;
import org.json.JSONObject;
  
public class UUIDGenerator {   
    public UUIDGenerator() {   
    }   
  
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
  public class a  implements Serializable{
	private static final long serialVersionUID = 1L;
	private String code;
	  private String Message;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return Message;
	}
	public void setMessage(String message) {
		Message = message;
	}
  }
    public static void main(String[] args) {   
        String[] ss = getUUID(10);
        UUIDGenerator uu = new UUIDGenerator();
        a b = uu.new a();
        b.setCode("123");
        b.setMessage("123");
        JSONObject jsonString = (JSONObject) JSONObject.wrap(ErrorEnum.USER_AUTH_FAIL); 
        System.out.println(jsonString);  
        for (int i = 0; i < ss.length; i++) {   
            //System.out.println("ss["+i+"]====="+ss[i]);   
        } 
        
    	QueryOrdersBo queryOrdersBo = new QueryOrdersBo();
    	queryOrdersBo.setId("123qwe");
    	queryOrdersBo.setTotal(11);
    	queryOrdersBo.setItems(123, 11);
		String jsonStr = JSONObject.wrap(queryOrdersBo).toString(); 
        System.out.println(jsonStr);

        try {
            JSONObject json = new JSONObject("");
            System.out.println(json.get("hahah"));
        } catch(Exception e) {
        	System.out.println(e);
        }

    }   
} 