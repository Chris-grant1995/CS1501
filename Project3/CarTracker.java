import java.util.Scanner;
import java.util.Random;
/**
 * Created by Chris on 10/26/15.
 */
public class CarTracker {
    static DoublePQ queue = new DoublePQ();
    public static void main(String[] args){
        String input;
        int choice =0;
        Scanner scan = new Scanner(System.in);
        while(true){
            System.out.println("Car Tracker Options");
            System.out.println("1. Add a Car");
            System.out.println("2. Update a Car");
            System.out.println("3. Remove a Car");
            System.out.println("4. Get Lowest Priced Car");
            System.out.println("5. Get Lowest Mileage Car");
            System.out.println("6. Get Lowest Priced Car by Make and Model");
            System.out.println("7. Get Lowest Mileage Car by Make and Model");
            System.out.println("8. Print All Cars");
            System.out.println("9. Exit");
            System.out.print("Please enter the number of the option you want: ");
            input = scan.nextLine();
            //System.out.println();
            while (true){
                try {
                    choice= Integer.parseInt(input);
                    break;
                }
                catch (NumberFormatException e){
                    System.out.print("Invalid Input, only enter the number that you want:");
                    input = scan.nextLine();
                    //System.out.println();
                }
            }
            //System.out.println("Input Processed");
            switch (choice){
                case 1:
                    System.out.println("Adding Car");
                    addCar();
                    //System.out.println("Car added successfully");
                    break;
                case 2:
                    if(queue.size ==0){
                        System.out.println("No Cars in Queue");
                        break;
                    }
                    //System.out.println("Updating Car");
                    updateCar();
                    break;
                case 3:
                    if(queue.size ==0){
                        System.out.println("----");
                        System.out.println("No Cars in Queue");
                        System.out.println("----");
                        break;
                    }
                    //System.out.println("Removing Car");
                    System.out.print("Enter The Vin of the car you want to remove:");
                    String vin = scan.nextLine();
                    queue.removeCar(vin);
                    break;
                case 4:
                    if(queue.size ==0){
                        System.out.println("----");
                        System.out.println("No Cars in Queue");
                        System.out.println("----");
                        break;
                    }
                    System.out.println("Getting Lowest Priced Car");
                    System.out.println("----");
                    System.out.println(queue.getCheapest());
                    System.out.println("----");
                    break;
                case 5:
                    if(queue.size ==0){
                        System.out.println("----");
                        System.out.println("No Cars in Queue");
                        System.out.println("----");
                        break;
                    }
                    System.out.println("Getting Lowest Mileage Car");
                    System.out.println("----");
                    System.out.println(queue.getLeastMiles());
                    System.out.println("----");
                    break;
                case 6:
                    if(queue.size ==0){
                        System.out.println("No Cars in Queue");
                        break;
                    }
                    System.out.println("Getting Lowest Priced Car by Make and Model");
                    getLowPriceByMake();
                    break;
                case 7:
                    if(queue.size ==0){
                        System.out.println("----");
                        System.out.println("No Cars in Queue");
                        System.out.println("----");
                        break;
                    }
                    System.out.println("Getting Lowest Mileage Car by Make and Model");
                    getLowMileagebyMake();
                    break;
                case 8:
                    if(queue.size ==0){
                        System.out.println("----");
                        System.out.println("No Cars in Queue");
                        System.out.println("----");
                        break;
                    }
                    queue.printList();
                    break;
                case 9:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Enter a choice within the menu");
            }

        }
    }
    public static void addCar(){
        String input;
        int choice =0;
        Scanner scan = new Scanner(System.in);
        Car c = new Car();
        System.out.print("Enter Car Vin #: ");
        input = scan.nextLine();
        if(queue.searchForVin(input)){
            System.out.println("That VIN is already assigned to a Car in the list. Try again.");
            return;
        }
        c.setVin(input);
        System.out.print("Enter Car Make: ");
        input = scan.nextLine();
        c.setMake(input);
        System.out.print("Enter Car Model: ");
        input = scan.nextLine();
        c.setModel(input);
        System.out.print("Enter Car Color: ");
        input = scan.nextLine();
        c.setColor(input);
        System.out.print("Enter Car Price $ ");
        input = scan.nextLine();
        int price;
        while(true) {
            try {
                price = Integer.parseInt(input);
                break;
            }catch (NumberFormatException e){
                System.out.println("Enter a number for the price: ");
                input = scan.nextLine();
            }
        }
        c.setPrice(price);
        System.out.print("Enter Car Mileage: ");
        input = scan.nextLine();
        int mileage;
        while(true) {
            try {
                mileage = Integer.parseInt(input);
                break;
            }catch (NumberFormatException e){
                System.out.println("Enter a number for the mileage: ");
                input = scan.nextLine();
            }
        }
        c.setMileage(mileage);
        queue.add(c);
    }
    public static void updateCar(){
        String input;
        int choice;
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter the VIN # of the car you want to update: ");

        input = scan.nextLine();
        if(!queue.searchForVin(input)){
            System.out.println("Vin not found, returning to previous menu.");
            return;
        }
        String input2;
        while(true) {
            System.out.println("Car Update Options");
            System.out.println("1. Update Price");
            System.out.println("2. Update Mileage");
            System.out.println("3. Update Color");
            System.out.println("4. Exit");
            System.out.print("Please enter the number of the option you want: ");
            input2 = scan.nextLine();
            while(true) {
                try {
                    choice = Integer.parseInt(input2);

                    break;
                } catch (NumberFormatException e) {
                    System.out.print("Please enter the number of the option you want: ");
                    input2 = scan.nextLine();
                }
            }
            switch (choice) {
                case 1:
                    System.out.print("Enter the new Price for the car: ");
                    input2 = scan.nextLine();
                    queue.updateCar(input, input2, 2);

                    break;
                case 2:
                    System.out.print("Enter the new Mileage for the car: ");
                    input2 = scan.nextLine();
                    queue.updateCar(input, input2, 3);

                    break;
                case 3:
                    System.out.print("Enter the new Color for the car: ");
                    input2 = scan.nextLine();
                    queue.updateCar(input, input2, 6);

                    break;
                case 4:
                    return;
                default:
                    System.out.println("Only enter a number within the listed range");
            }
        }
    }
    public static void getLowPriceByMake(){
        String make;
        String model;
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter the Make of the car: ");
        make = scan.nextLine();
        System.out.print("Enter the Model of the car: ");
        model = scan.nextLine();
        Car c = queue.getLowPriceByMakeAndModel(make,model);
        if(c == null){
            System.out.println("There was no combination of Make and Model in the Database.");
        }
        else{
            System.out.println("----");
            System.out.println(c);
            System.out.println("----");
        }

    }
    public static void getLowMileagebyMake(){
        String make;
        String model;
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter the Make of the car: ");
        make = scan.nextLine();
        System.out.print("Enter the Model of the car: ");
        model = scan.nextLine();
        Car c = queue.getLowMileageByMakeAndModel(make, model);
        if(c == null){
            System.out.println("There was no combination of Make and Model in the Database.");
        }
        else{
            System.out.println("----");
            System.out.println(c);
            System.out.println("----");
        }
    }
}
