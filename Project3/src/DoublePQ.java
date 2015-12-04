import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Stack;
import java.util.Scanner;
import java.util.HashMap;

/**
 * Created by Chris on 10/28/15.
 */
public class DoublePQ {
    static PriorityQueue miles = new PriorityQueue();
    static PriorityQueue price = new PriorityQueue();
    static ArrayList< Car> cars = new ArrayList<>();
    static int size;
    public DoublePQ(){
        int size =0;
    }
    public void add(Car c){
        cars.add(c);
        System.out.println("----");
        System.out.println("Added a " +c + " to the list");
        System.out.println("----");
        PQCar carMiles = new PQCar(size, c.getMileage());
        miles.add(carMiles);
        PQCar carPrice = new PQCar(size, c.getPrice());
        price.add(carPrice);
        size++;
    }
    public Car getCheapest(){
        if(cars.size() == 0) return null;
        int num = ((PQCar) price.peek()).pos;
        return cars.get(num);
    }
    public Car getLeastMiles(){
        if(cars.size() == 0) return null;
        int num = ((PQCar) miles.peek()).pos;
        return cars.get(num);
    }
    public static void printList(){
        for(int i =0; i<cars.size(); i++){
            if(cars.get(i) !=null)
                System.out.println(cars.get(i));
        }
    }
    public static void removeCar(String vin){
        if(cars.size() == 0) return;
        //printList();
        Stack<PQCar> tempPrice = new Stack<>();
        Stack<PQCar> tempMileage = new Stack<>();
        int num = -1;
        for(int i =0; i<cars.size(); i++){
            if(vin.equalsIgnoreCase(cars.get(i).getVin())){
                System.out.println("----");
                System.out.println("Removed a " + cars.get(i) + "from the list.");
                System.out.println("----");
                //cars.remove(i);
                cars.set(i,null);
                size--;
                num = i;
            }
        }
        if(num == -1){
            System.out.println("----");
            System.out.println("Vin Not Found");
            System.out.println("----");
            return;
        }
        while(!price.isEmpty()){
            PQCar tempCar = (PQCar)price.remove();
            if(tempCar.pos == num){

                break;
            }
            tempPrice.add(tempCar);
        }
        while(!tempPrice.isEmpty()){
            price.add(tempPrice.pop());
        }
        while(!miles.isEmpty()){
            PQCar tempCar = (PQCar)miles.remove();
            if(tempCar.pos == num){
                break;
            }
            tempMileage.add(tempCar);
        }
        while(!tempMileage.isEmpty()){
            miles.add(tempMileage.pop());
        }
        //printList();

    }
    public void updateCar(String vin, String updateValue, int updateField){
        if(cars.size() == 0) return;
        int found = -1;
        for(int i =0; i<cars.size(); i++){
            if(cars.get(i).getVin().equalsIgnoreCase(vin)){
                found = i;
                break;

            }
        }
        if(found < 0){
            System.out.println("----");
          System.out.println("Car not found");
            System.out.println("----");
            return;
        }
        switch (updateField){

            case 2:
                //TODO
                int num;
                Scanner scan = new Scanner(System.in);
                while(true) {
                    try {
                        num = Integer.parseInt(updateValue);
                        break;
                    }catch (NumberFormatException e){
                        System.out.println("Enter a number for the price: ");
                        updateValue = scan.nextLine();
                    }
                }
                cars.get(found).setPrice(num);
                System.out.println("----");
                System.out.println("Updated Car to: "+cars.get(found));
                System.out.println("----");
                Stack<PQCar> tempPrice = new Stack<>();
                while(!price.isEmpty()){
                    PQCar tempCar = (PQCar)price.remove();
                    if(tempCar.pos == found){
                        tempCar.value = num;
                        tempPrice.add(tempCar);
                        break;
                    }
                    tempPrice.add(tempCar);
                }
                while(!tempPrice.isEmpty()){
                    price.add(tempPrice.pop());
                }

                break;
            case 3:
                //TODO
                int num2;
                Scanner scan2 = new Scanner(System.in);
                while(true) {
                    try {
                        num2 = Integer.parseInt(updateValue);
                        break;
                    }catch (NumberFormatException e){
                        System.out.println("Enter a number for the Mileage: ");
                        updateValue = scan2.nextLine();
                    }
                }
                cars.get(found).setMileage(num2);
                System.out.println("----");
                System.out.println("Updated Car to: "+cars.get(found));
                System.out.println("----");
                Stack<PQCar> tempMiles = new Stack<>();
                while(!miles.isEmpty()){
                    PQCar tempCar = (PQCar)miles.remove();
                    if(tempCar.pos == found){
                        tempCar.value = num2;
                        tempMiles.add(tempCar);
                        break;
                    }
                    tempMiles.add(tempCar);
                }
                while(!tempMiles.isEmpty()){
                    miles.add(tempMiles.pop());
                }
                break;
            case 6:
                cars.get(found).setColor(updateValue);
                System.out.println("----");
                System.out.println("Updated Car to: "+cars.get(found));
                System.out.println("----");
                break;
        }

    }
    public Car getLowPriceByMakeAndModel(String make, String model){
        if(cars.size() == 0) return null;
        Car found = new Car();
        Stack<PQCar> tempPrice = new Stack<>();
        boolean notFound = true;
        while(!price.isEmpty()){
            PQCar tempCar = (PQCar)price.remove();
            if(cars.get(tempCar.pos).getMake().equalsIgnoreCase(make) && cars.get(tempCar.pos).getModel().equalsIgnoreCase(model) ){
                found = cars.get(tempCar.pos);
                tempPrice.add(tempCar);
                notFound = false;
                break;
            }
            tempPrice.add(tempCar);
        }

        while(!tempPrice.isEmpty()){
            price.add(tempPrice.pop());
        }
        if(notFound){
            return null;
        }
        return found;
    }
    public Car getLowMileageByMakeAndModel(String make, String model){
        //if(cars.size() == 0) return null;
        Car found = new Car();
        Stack<PQCar> tempMiles = new Stack<>();
        boolean notFound = true;
        while(!miles.isEmpty()){
            PQCar tempCar = (PQCar)miles.remove();
            if(cars.get(tempCar.pos).getMake().equalsIgnoreCase(make) && cars.get(tempCar.pos).getModel().equalsIgnoreCase(model) ){
                found = cars.get(tempCar.pos);
                notFound = false;
                tempMiles.add(tempCar);
                break;
            }
            tempMiles.add(tempCar);
        }
        while(!tempMiles.isEmpty()){
            miles.add(tempMiles.pop());
        }
        if(notFound){
            return null;
        }
        return found;
    }
    public boolean searchForVin(String vin){
        for(int i =0; i<size; i++){
            if(vin.equalsIgnoreCase(cars.get(i).getVin())) return true;
        }
        return false;
    }


}
