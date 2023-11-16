package sample.model;

public class CartItem //Το CartItem χρησιμοποιείται για να διαχειρίζεται τα δεδομένα του αντικειμένου στο καλάθι
{
    private Item item;
    private int quantity;

    public CartItem(Item item) {
        this.item = item;
        this.quantity = 1;
    }
    public CartItem(Item item, int quantity){
        this.item = item;
        this.quantity = quantity;
    }
    public void changeQuantity(int diff) //Του δίνεται η διαφορά και αλλάζει την ποσότητα του αντικειμένου
    {
        if((quantity+diff)>0){
            quantity+=diff;
        }
    }

    public Item getItem() //Επιστρέφει το αντικείμενο
    {
        return item;
    }

    public int getQuantity() //Επιστρέφει την ποσότητα
    {
        return quantity;
    }
}
