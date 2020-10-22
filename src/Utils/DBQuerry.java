/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import Model.Country;
import Model.Customer;
import Model.Region;
import Model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author corte
 */
public class DBQuerry {
    
    private static PreparedStatement statement;
    static ObservableList<Country> countryList = FXCollections.observableArrayList();
    static ObservableList<Region> regionList = FXCollections.observableArrayList();
    static ObservableList<Customer> customerList = FXCollections.observableArrayList();
    
    //Create Statement Object
    public static void setPreparedStatement(Connection conn, String SQLStatement) throws SQLException
    {
        statement = conn.prepareStatement(SQLStatement);
        
    }
    
    //Return Statment Object
    public static PreparedStatement getPreparedStatement()
    {
        return statement;
    }
    
    //Checks username and password for logging in
    public static boolean login(String username, String password) {
        
            try{
                Connection conn = DBConnection.getConnection();
                String loginStatement = "SELECT * FROM users WHERE User_Name=? AND Password=?";
                DBQuerry.setPreparedStatement(conn, loginStatement);
                PreparedStatement ps = DBQuerry.getPreparedStatement();
                ps.setString(1, username);
                ps.setString(2, password);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    
                    User currentUser = new User(rs.getString("User_Name"), rs.getString("User_ID"), true);
                    return true;
                }
                else {
                    DBConnection.closeConnection();
                    return false;
                }
            } catch (SQLException e) {
                System.out.println("Error: " + e.getMessage());
                return false;
            }
        
    }
    
    
    //Query used for Filling in Country Combo Box in Add Customer and Modify Customer menus
    public static ObservableList<Country> getCountries(){
        
        try{
            //Removed all previously stored countries to prevent duplicate entries
            countryList.removeAll(countryList);
            Connection conn = DBConnection.getConnection();
            String queryStatement = "SELECT Country, Country_ID  FROM countries";
            DBQuerry.setPreparedStatement(conn, queryStatement);
            PreparedStatement ps = DBQuerry.getPreparedStatement();
            ResultSet tempList = ps.executeQuery();
            while(tempList.next())
            {
                Country tempCountry = new Country(tempList.getInt("Country_ID"), tempList.getString("Country"));
                countryList.add(tempCountry);
            }
            
        }catch(SQLException e){
            System.out.println("Error: " + e.getMessage());
        }
        
        return countryList;
    }
    
    //Query used for Filling in Country Combo Box in Add Customer and Modify Customer menus
    public static ObservableList<Region> getRegions(Country selCountry){
        
        try{
            //Removed all previously stored Regions to prevent duplicate entries
            regionList.removeAll(regionList);
            Connection conn = DBConnection.getConnection();
            String queryStatement = "SELECT Division_ID, Division FROM first_level_divisions where COUNTRY_ID = ?";
            DBQuerry.setPreparedStatement(conn, queryStatement);
            PreparedStatement ps = DBQuerry.getPreparedStatement();
            
            //Uses Country ID from User selection of Combo Box options
            ps.setString(1, String.valueOf(selCountry.getId()));
            ResultSet tempList = ps.executeQuery();
            while(tempList.next())
            {
                Region tempRegion = new Region(tempList.getInt("Division_ID"), tempList.getString("Division"));
                regionList.add(tempRegion);
            }
            
        }catch(SQLException e){
            System.out.println("Error: " + e.getMessage());
        }
        
        return regionList;
    }
    
    //Query used for Filling in All Customers
    public static ObservableList<Customer> getAllCustomers() throws SQLException
    {
        try
        {
            //Removed all previously stored Regions to prevent duplicate entries
            customerList.removeAll(customerList);
            Connection conn = DBConnection.getConnection();
            String queryStatement = "select c.Customer_ID, c.Customer_Name, c.Address, c.Postal_Code, "
                                    + "c.Phone, r.Division, Country from customers as c inner join  first_level_divisions as r "
                                    + " on r.Division_ID = c.Division_ID inner join countries as co on r.Country_ID = co.Country_ID;";
            DBQuerry.setPreparedStatement(conn, queryStatement);
            PreparedStatement ps = DBQuerry.getPreparedStatement();
            ResultSet tempList = ps.executeQuery();
            while(tempList.next())
            {
                Customer tempCustomer = new Customer(
                        tempList.getInt("Customer_ID"), 
                        tempList.getString("Customer_Name"),
                        tempList.getString("Address"),
                        tempList.getString("Postal_Code"),
                        tempList.getString("Phone"),
                        tempList.getString("Division"),
                        tempList.getString("Country")
                        );
                customerList.add(tempCustomer);
            }
            
        }catch(SQLException e){
            System.out.println("Error: " + e.getMessage());
        }
        
        return customerList;
    }
    
    public static ObservableList<Appointment>
    
   
    public static void addCustomer(Customer newCust, int regionID) throws SQLException
    {
        try{
        
            //Used to get time of user log in
            LocalDateTime now = LocalDateTime.now();
            //Formatting LDT object
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String newTimeNow = dtf.format(now);
            Connection conn = DBConnection.getConnection();
            String queryStatement = "INSERT into customers(Customer_Name, Address, Postal_Code, Phone, "
                                + "Create_Date, Created_By, Last_Updated_By, Division_ID)" +
                                    " Values( ?, ?, ?, ?, ?, ?, ?, ?)";
            DBQuerry.setPreparedStatement(conn, queryStatement);
            PreparedStatement ps = DBQuerry.getPreparedStatement();
        
            //Key-value mapping
            ps.setString(1, newCust.getName());
            ps.setString(2, newCust.getAddress());
            ps.setString(3, newCust.getPostalCode());
            ps.setString(4, newCust.getPhone());
            ps.setString(5, newTimeNow);
            ps.setString(6, User.getUserName());
            ps.setString(7, User.getUserName());
            ps.setString(8, String.valueOf(regionID));
            
            Integer rs = ps.executeUpdate();
            System.out.println(rs);
        
        }catch(SQLException e){
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    public static int getNewID() throws SQLException
    {
        Integer newID = 0;
        Connection conn = DBConnection.getConnection();
        String queryStatement = "SELECT Customer_id FROM customers WHERE Customer_id=(SELECT MAX(Customer_id) FROM customers)";
        DBQuerry.setPreparedStatement(conn, queryStatement);
        PreparedStatement ps = DBQuerry.getPreparedStatement();
        ResultSet rs = ps.executeQuery();
        
        //Returns greatest ID value and assigns it to local variable
        if(rs.next())
            newID = rs.getInt("Customer_ID");
        
        //Increments newID value by 1
        newID++;
        return newID;
    }
}
