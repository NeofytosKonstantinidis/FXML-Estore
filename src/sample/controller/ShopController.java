package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import sample.Connect;
import sample.Main;
import sample.listeners.CheckoutListener;
import sample.listeners.MyListener;
import sample.listeners.CartListener;
import sample.listeners.ReceiptListener;
import sample.model.CartItems;
import sample.model.Item;
import sample.model.Order;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ShopController implements Initializable //Ο ShopController διαχειρίζεται το FXML shop
{

    @FXML
    private Label checkoutItems;

    @FXML
    private ScrollPane scroll;

    @FXML
    private GridPane grid;

    @FXML
    private GridPane cartGrid;

    @FXML
    private HBox cartbox;
    @FXML
    private TextField searchfield;

    private List<Item> items = new ArrayList<>();
    CheckoutController checkoutController;
    private MyListener myListener;
    private CartListener cListener;
    private CheckoutListener checkoutListener;
    private ReceiptListener receiptListener;
    private CartItems cartItems = new CartItems();
    private int checkoutnum = 0;
    private boolean checkoutActive = false;
    private List<Item> getData(String where) throws SQLException //Η getData() φορτώνει από την βάση δεδομένων τα αντικείμενα που υπάρχουν
    {
        List<Item> items = new ArrayList<>();
        Item item;
        String sqlSel = "SELECT id, name, price, imgsrc FROM product"+where;
        Connect connect = new Connect();
        if(connect.connectDB()){
            ResultSet rst = connect.getSelect(sqlSel);
            while (rst.next()){
                item = new Item(rst.getString("name"),"Images/"+rst.getString("imgsrc"),rst.getDouble("price"),rst.getInt("id"));
                items.add(item);
            }
        }
        connect.connectClose();
        return items;
    }

    private void addItem(Item item)//Προσθέτει στην λίστα του καλαθιού το αντικείμενο που επιλέχθηκε το add to cart button
    {
        cartbox.setVisible(false);
        checkoutnum++;
        checkoutItems.setText(String.valueOf((checkoutnum)));
        cartItems.filterItem(item);
        System.out.println(cartItems.toString());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) //Καλείται κατά την δημιουργία του ShopController
    {
        cartbox.setVisible(false);
        fillGrid("");
    }
    public void clearGrid() //Καθαρίζει την λιστα με τα αντικείμενα
    {
        items.clear();
        grid.getChildren().clear();
    }
    public void fillCart() //Γεμίζει το καλάθι με την λίστα του καλαθιού και μέσω του listener απευθύνεται σε μεθόδους του ShopController
    {
        try{
            cartGrid.getChildren().clear();
            if(cartItems.getItems().size()>0){
                cListener = new CartListener() {
                    @Override
                    public void addCartQuantity(Item item, CartItemController cartItemController) {
                        changeQuantity(item,cartItemController,1);
                    }

                    @Override
                    public void reduceCartQuantity(Item item, CartItemController cartItemController) {
                        changeQuantity(item,cartItemController,-1);
                    }

                    @Override
                    public void removeCartItem(Item item) {
                        checkoutnum-=cartItems.getItem(item).getQuantity();
                        cartItems.removeItem(item);
                        if(cartItems.getItems().size()>0)
                        {
                            fillCart();
                        }else{
                            cartbox.setVisible(false);
                        }
                        checkoutItems.setText(String.valueOf((checkoutnum)));
                    }
                };
            }
            for(int i=0; i<cartItems.getItems().size();i++)
            {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(Main.class.getResource("views/cartItem.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                CartItemController cartItemController = fxmlLoader.getController();
                cartItemController.setCartItem(cartItems.getItems().get(i).getItem(),cartItems.getItems().get(i).getQuantity(), cListener);
                cartGrid.add(anchorPane,0,i);
                cartGrid.setMinWidth(Region.USE_COMPUTED_SIZE);
                cartGrid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                cartGrid.setMaxWidth(Region.USE_PREF_SIZE);

                cartGrid.setMinHeight(Region.USE_COMPUTED_SIZE);
                cartGrid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                cartGrid.setMaxHeight(Region.USE_PREF_SIZE);
                GridPane.setMargin(anchorPane,new Insets(4));

            }
        }catch(IOException e){
            e.printStackTrace();
        }

    }
    public void changeQuantity(Item item, CartItemController cartItemController,int diff) //Αλλάζει την ποσότητα του αντικειμένου
    {
        cartItemController.setQuantity(diff);
        checkoutnum+=diff;
        checkoutItems.setText(String.valueOf((checkoutnum)));
    }
    public void fillGrid(String where) //Γεμίζει το grid με τα αντικείμενα
    {
        try{
            items.addAll(getData(where));
            if(items.size()>0){
                myListener = new MyListener() {
                    @Override
                    public void onClickListener(Item item) {
                        addItem(item);
                    }
                };
            }
            int column = 0;
            int row = 0;
            try {
                for(int i=0; i<items.size(); i++){
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(Main.class.getResource("views/item.fxml"));
                    AnchorPane anchorPane = fxmlLoader.load();
                    ItemController itemController = fxmlLoader.getController();
                    itemController.setData(items.get(i),myListener);

                    if(column==4){
                        column = 0;
                        row++;
                    }
                    grid.add(anchorPane, column++, row);
                    grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                    grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                    grid.setMaxWidth(Region.USE_PREF_SIZE);

                    grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                    grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                    grid.setMaxHeight(Region.USE_PREF_SIZE);
                    GridPane.setMargin(anchorPane,new Insets(10));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }catch(Exception e){
            System.out.println("No Results");
            e.printStackTrace();
        }

    }

    public void searchClicked(MouseEvent mouseEvent) //Αναζητάει με την λέξη κλειδί που του δόθηκε για αντικείμενα στην βάση δεδομένων και επιστρέφει τα αποτελέσματα
    {
        if(!checkoutActive){
            String search = searchfield.getText();
            System.out.println(search);
            clearGrid();
            if(search.length()>0 && search!=null)
            {
                fillGrid(" WHERE name LIKE '%"+search+"%'");
            }else{
                fillGrid("");
            }
        }
    }

    public void openCart(MouseEvent mouseEvent) //Γεμίζει και εμφανίζει το καλάθι με τα αντικείμενα.
    {
        if(cartbox.isVisible()){
            cartbox.setVisible(false);
        }else{
            if(cartItems.getItems().size()>0){
                fillCart();
                cartbox.setVisible(true);
            }
        }


    }

    public void finishOrder() //Ολοκληρώνει την παραγγελία και ενημερώνει την βάση δεδομένων με τα δεδομένα της παραγγελίας
    {
        Order order = checkoutController.getOrder();
        JFrame f = new JFrame();
        if(order==null){
            JOptionPane.showMessageDialog(f,"Please fill all inputs.");
        }else{
            String trackingnumber=order.finishOrder();
            if(trackingnumber.length()>0 && trackingnumber!=null){
                clearCart();
                loadReceipt(order, trackingnumber);
            }else{
                JOptionPane.showMessageDialog(f,"Failed to proccess the order.\nPlease try again later.");
            }
        }
    }

    private void clearCart() //Καθαρίζει το καλάθι
    {
        cartItems = new CartItems();
        checkoutnum=0;
        checkoutItems.setText(String.valueOf((checkoutnum)));
    }

    public void loadReceipt(Order order, String trackingnumber) //Φορτώνει την απόδειξη αγοράς
    {
        try{
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(Main.class.getResource("views/orderReceipt.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                OrderReceiptController orderReceiptController = fxmlLoader.getController();
                if(orderReceiptController==null){
                    System.out.println("Null Controller");
                }
                receiptListener = new ReceiptListener() {
                    @Override
                    public void returnToShop() {
                        backToStore();
                    }
                };
            System.out.println("Load Receipt:");
            System.out.println("List Size:"+ order.getItems().getItems().size());
            System.out.println(order.getItems().toString());
                orderReceiptController.setContent(order,trackingnumber,receiptListener);
                scroll.setContent(anchorPane);
        }catch(IOException | NullPointerException e){
            System.out.println("Load Receipt Error");
            e.printStackTrace();
        }
    }

    public void backToStore() //Επιστρέφει στην σελίδα Shop με τα αντικείμενα
    {
        scroll.setContent(grid);
        checkoutActive=false;
    }

    public void gotocheckout(MouseEvent mouseEvent) //Φορτώνει την σελίδα checkout
    {
        checkoutActive=true;
        cartbox.setVisible(false);
        try {
            checkoutListener = new CheckoutListener() {
                @Override
                public void buyClicked() {
                    finishOrder();
                }
            };
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(Main.class.getResource("views/checkout.fxml"));
            AnchorPane anchorPane = fxmlLoader.load();
            checkoutController = fxmlLoader.getController();
            CartItems clonedItems = new CartItems(cartItems.getItems());
            checkoutController.sentItems(clonedItems,checkoutListener);
            scroll.setContent(anchorPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openShop(MouseEvent mouseEvent)  //Φορτώνει την σελίδα Shop
    {
        if(scroll.getContent()!=grid){
            scroll.setContent(grid);
            checkoutActive=false;
        }
    }

    public void openTrackOrder(MouseEvent mouseEvent) //Φορτώνει την σελίδα εντοπισμού παραγγελίας
    {
        try {
            checkoutActive = true;
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(Main.class.getResource("views/trackOrder.fxml"));
            AnchorPane anchorPane = fxmlLoader.load();
            scroll.setContent(anchorPane);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void openMainScene(MouseEvent mouseEvent) //Επιστρέφει στην σελίδα LogIn
    {
        Main.openMainScene();
    }
}
