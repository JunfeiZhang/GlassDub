package edu.washington.glassdub.glassdub;

/**
 * Created by yizhaoyang on 3/7/17.
 */

public class currentUser {

    private String userName;
    private static currentUser user;

    public currentUser(){

    }

    public static void setCurrentUser(String givenName){
        if(user == null){
            user = new currentUser();
            user.userName = givenName;
        }

    }

    public static String getCurrentUserName(){
        return user.userName;
    }

}
