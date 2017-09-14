package weibo4j.examples.friendships;

import weibo4j.Friendships;
import weibo4j.examples.oauth2.Log;
import weibo4j.model.User;
import weibo4j.model.UserWapper;
import weibo4j.model.WeiboException;

public class GetFollowersById {

	public static void main(String[] args) {
		/*
		  
		 rlp390660@sina.cn
		 4B1ivdq92b30
		*/
		String access_token = "2.00il7qHG03_O899b39e41ac16PP2TD";
		String uid = "5612724884";
		Friendships fm = new Friendships(access_token);
		try {
			UserWapper users = fm.getFollowersById(uid);
			for(User u : users.getUsers()){
				Log.logInfo(u.toString());
			}
			System.out.println(users.getNextCursor());
			System.out.println(users.getPreviousCursor());
			System.out.println(users.getTotalNumber());
		} catch (WeiboException e) {
			e.printStackTrace();
		}

	}

}
