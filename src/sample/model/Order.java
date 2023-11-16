package sample.model;

import sample.Connect;

import javax.swing.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class Order //Το Order χρησιμοποιείται για να διαχειρίζεται τα δεδομένα της παραγγελίας
{
    private String firstname;
    private String lastname;
    private String address;
    private String city;
    private String country;
    private CartItems items;

    public Order(String firstname, String lastname, String address, String city, String country, CartItems items) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.address = address;
        this.city = city;
        this.country = country;
        this.items = items;
    }

    public String getFirstname() //Επιστρέφει το όνομα του ατόμου της παραγγελίας
    {
        return firstname;
    }

    public String getLastname() //Επιστρέφει το επώνυμο του ατόμου της παραγγελίας
    {
        return lastname;
    }

    public String getAddress() //Επιστρέφει την διεύθυνση του ατόμου της παραγγελίας
    {
        return address;
    }

    public String getCity() //Επιστρέφει την πόλη του ατόμου της παραγγελίας
    {
        return city;
    }

    public String getCountry() //Επιστρέφει την χώρα του ατόμου της παραγγελίας
    {
        return country;
    }

    public CartItems getItems() //Επιστρέφει την λίστα απο αντικείμενα της παραγγελίας
    {
        return items;
    }
    public String finishOrder() //Ολοκληρώνει την παραγγελία και ενημερώνει την βάση.
    {
        try{
            Connect connect = new Connect();
            connect.connectDB();
            String trackingnumber="";
            int id = connect.insertInto("INSERT INTO shoporder (status,order_time) VALUES (0,now())");
            int res = 10;
            for(int i=0;i<items.getItems().size();i++){
                if(res>0)
                {
                    res = connect.insertInto("INSERT INTO orderitems (orderid,itemid,quantity) VALUES ("+id+","+items.getItems().get(i).getItem().getId()+","+items.getItems().get(i).getQuantity()+")");
                }
            }
            if(res>0)
            {
                res = connect.insertInto("INSERT INTO shipingdetails (orderid,firstname,lastname,address,city,country) VALUES ("+id+",'"+firstname+"','"+lastname+"','"+address+"','"+city+"','"+country+"')");
            }
            if(res>0)
            {
                Date d = new Date();
                LocalDate localDate = d.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                trackingnumber=""+localDate.getYear()+localDate.getMonthValue()+localDate.getDayOfMonth()+id;
                res = connect.insertInto("INSERT INTO tracking (orderid,tracking_number) VALUES ("+id+",'"+trackingnumber+"')");
            }
            connect.connectClose();
            if(res==-1)
            {
                return null;
            }else{
                return trackingnumber;
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
