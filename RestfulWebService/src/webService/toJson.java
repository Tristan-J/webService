package webService;

import java.util.ArrayList;

import org.json.JSONException;  
import org.json.JSONObject;

import model.SecurityManager;
import pojo.UserVO;

public class toJson {
	
	public static JSONObject convert (String s) {
		JSONObject j = null;
		// try {
		// 	JSONObject temp = new JSONObject();
		// 	j = temp;
		// }
		// catch(Exception e) {
		// 	System.out.println("error in json");
		// }
		return j;
	}

	public static String getAllUsersList(String username,String password){
		String userListData = null;
		int temp = 0;
		String tempS = "";
		try {
			ArrayList<UserVO> userList = null;
			SecurityManager securityManager= new SecurityManager();
			userList = securityManager.getAllUsersList();

			return tempS;
			
			// for (UserVO userVO : userList) {
			// 	if(userVO.getUsername().equals(username)){
			// 		if(userVO.getPassword().equals(password)){
			// 			return "Logged in User:"+username;
			// 		}
			// 	}
			// 	temp++;
			// }
			// tempS = "" + temp;

		} 
		catch (Exception e){
			System.out.println("error");
		}
		return "You are not a Valid User";
	}
}