package sample.model;

import java.util.ArrayList;
import java.util.List;

public class CartItems //Το CartItem χρησιμοποιείται για να διαχειρίζεται την λίστα με τα αντικείμενα στο καλάθι
{
    private List <CartItem> items;
    public CartItems(){
        items =new ArrayList<>();
    }
    public CartItems(List <CartItem> items){this.items = items;}
    public void removeItem(Item item){
        for(int i=0;i<items.size();i++)
        {
            if(items.get(i).getItem()==item){
                items.remove(i);
            }
        }
    }

    public CartItem getItem(Item item) //Αναζητά και επιστρέφει το αντικείμενο που ζητήθηκε με την ποσότητά του
    {
        for (CartItem cartItem:items) {
            if(cartItem.getItem()==item){
                return cartItem;
            }
        }
        return null;
    }

    public void filterItem(Item item) //Αναζητά το αντικείμενο. Αν βρεθεί του προσθέτει ποσότητα. Αν όχι το προσθέτει στην λίστα με τα αντικείμενα
    {
        boolean found=false;
        for (CartItem cartItem:items) {
            if(cartItem.getItem()==item){
                cartItem.changeQuantity(1);
                found=true;
            }
        }
        if(!found){
            items.add(new CartItem(item));
        }
    }
    public void addItem(Item item,int quantity) //Προσθέτει ένα αντικείμενο και την ποσότητα του στα αντικείμενα
    {
        items.add(new CartItem(item,quantity));
    }

    public List<CartItem> getItems() //Επιστρέφει την λίστα με τα αντικείμενα
    {
        return items;
    }
    @Override
    public String toString(){
        String tempString="";
        for (CartItem cartItem:items) {
            tempString+="Name: "+cartItem.getItem().getName()+", Quantity: "+cartItem.getQuantity()+"\n";
        }
        return tempString;
    }
    public double getTotalCost() //Υπολογίζει και επιστρέφει το συνολικό κόστος της λίστας
    {
        double total=0f;
        for (CartItem cartItem:items) {
            total+=(cartItem.getQuantity()*cartItem.getItem().getPrice());
        }
        return total;
    }
}
