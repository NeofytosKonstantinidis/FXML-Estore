package sample.model;

public class Item ////Το Item χρησιμοποιείται για να διαχειρίζεται τα δεδομένα του αντικειμένου στην σελίδα Shop
{
    private String name;
    private String imgSrc;
    private double price;
    private int id;


    public Item(String name, String imgSrc, double price, int id) {
        this.name = name;
        this.imgSrc = imgSrc;
        this.price = price;
        this.id = id;
    }

    public String getName() //Επιστρέφει το όνομα του προϊόντος
    {
        return name;
    }

    public void setName(String name) //Ορίζει το όνομα του προϊόντος
    {
        this.name = name;
    }

    public String getImgSrc() //Επιστρέφει την διαδρομή της εικόνας του προϊόντος
    {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) //Ορίζει την διαδρομή της εικόνας
    {
        this.imgSrc = imgSrc;
    }

    public double getPrice() //Επιστρέφει την τιμή του προϊόντος
    {
        return price;
    }

    public void setPrice(double price) //Ορίζει την τιμή του προϊόντος
    {
        this.price = price;
    }

    public int getId() //Επιστρέφει το id του προϊόντος
    {
        return id;
    }

    public void setId(int id) //Ορίζει το id του προϊόντος
    {
        this.id = id;
    }
}
