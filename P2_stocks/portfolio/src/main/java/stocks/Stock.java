package main.java.stocks;


public class Stock {
    private int quantity;
    private String label;
    
    public int getQuantity(){
        return quantity;
    }

    public int getLabel(){
        return label;
    }

    public void setLabel(String label){
        this.label = label;
    }

    public void setQuantity(int quantity){
        this.quantity = quantity;
    }
}
