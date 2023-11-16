package sample.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import sample.Main;

public class ResultOrderController //Ο ResultOrderController διαχειρίζεται το FXML resultOrder
{
    @FXML
    private Label processingText;
    @FXML
    private  Label packingText;
    @FXML
    private Label shippingText;
    @FXML
    private Label arrivedText;
    @FXML
    private ImageView processingImage;
    @FXML
    private ImageView packingImage;
    @FXML
    private ImageView shippingImage;
    @FXML
    private ImageView arrivedImage;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Label dateLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label addressLabel;

    public void setData(int status, String date, String name, String address)//Ορίζει τα δεδομένα του resultOrder
    {
        dateLabel.setText(date);
        nameLabel.setText(name);
        addressLabel.setText(address);
        Image image=null;
        if(status>0)
        {
            image = new Image(Main.class.getResourceAsStream("Images/checked_checkbox.png"));
        }
        switch (status){
            case 0:
                processingText.setStyle("-fx-text-fill: #000000");
                break;
            case 1:
                packingText.setStyle("-fx-text-fill: #000000");
                processingImage.setImage(image);
                progressBar.setProgress(0.28);
                break;
            case 2:
                shippingText.setStyle("-fx-text-fill: #000000");
                processingImage.setImage(image);
                packingImage.setImage(image);
                progressBar.setProgress(0.5);
                break;
            case 3:
                arrivedText.setStyle("-fx-text-fill: #000000");
                processingImage.setImage(image);
                packingImage.setImage(image);
                shippingImage.setImage(image);
                progressBar.setProgress(0.75);
                break;
            case 4:
                arrivedText.setStyle("-fx-text-fill: #099203");
                arrivedText.setText("Arrived");
                processingImage.setImage(image);
                packingImage.setImage(image);
                shippingImage.setImage(image);
                arrivedImage.setImage(image);
                progressBar.setProgress(1);
                break;
        }

    }

}
