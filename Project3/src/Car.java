/**
 * Created by Chris on 10/26/15.
 */
public class Car {
    String vin;
    String make;
    String model;
    int price;
    int mileage;
    String color;
    public Car(){}
    public void setVin(String v){
        vin = v;
    }
    public void setMake(String v){
        make = v;
    }
    public void setModel(String v){
        model = v;
    }
    public void setPrice(int v){
        price = v;
    }
    public void setMileage(int v){
        mileage = v;
    }
    public void setColor(String v){
        color = v;
    }

    public String getVin() {
        return vin;
    }

    public int getMileage() {
        return mileage;
    }

    public int getPrice() {
        return price;
    }

    public String getColor() {
        return color;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public String toString() {
        return "A " + color + " "  + make + " " + model + " priced at $" + price + " with " + mileage + " miles with vin #" + vin;
    }
}
