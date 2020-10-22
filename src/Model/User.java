/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author corte
 */
public class User {
    
    //Priavte class variables
    private static String userName;
    private static Integer userID;
    private static boolean activeState;

    public User(String userName, Integer userID, boolean activeState) {
        this.userName = userName;
        this.userID = userID;
        this.activeState = activeState;
    }
    
    /**
     * @return the userName
     */
    public static String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public static void setUserName(String userName) {
        User.userName = userName;
    }

    /**
     * @return the userPassword
     */
    public static Integer getUserID() {
        return userID;
    }

    /**
     * @param userID the userPassword to set
     */
    public static void setUserID(Integer userID) {
        User.userID = userID;
    }

    /**
     * @return the activeState
     */
    public static boolean isActiveState() {
        return activeState;
    }

    /**
     * @param activeState the activeState to set
     */
    public static void setActiveState(boolean activeState) {
        User.activeState = activeState;
    }
    
    
}
