/**
 * Created by Chris on 11/15/15.
 */
import java.io.*;
import java.util.*;
import java.util.Stack;

public class Airline {
    static String [] cities;
    static EdgeWeightedDigraph graph;


    //static ArrayList<DirectedEdge> added;
    public static void main(String[] args) throws FileNotFoundException, IOException
    {
        //added = new ArrayList<>();

        Scanner scan = new Scanner(System.in);
        System.out.print("INPUT FILE:");
        String filename = scan.nextLine();
        File f = new File(filename);
        scan = new Scanner(f);
        int numberOfCities = Integer.parseInt(scan.nextLine()) + 1;
        graph = new EdgeWeightedDigraph(numberOfCities);

        cities = new String[numberOfCities];
        cities[0] = "Null City";
        for(int i =1; i<cities.length; i++){
            cities[i] = scan.nextLine();
            //System.out.println(cities[i]);
        }
        while(scan.hasNext()){
            String data = scan.nextLine();
            //System.out.println(data);
            String [] arr  = data.split(" ");
            int city1 = Integer.parseInt(arr[0]);
            int city2 = Integer.parseInt(arr[1]);
            int distance = Integer.parseInt(arr[2]);
            double price = Double.parseDouble(arr[3]);
            //list.addEdge(city1, city2, distance, price);
            graph.addEdge(new DirectedEdge(city1, city2, distance, price));
            graph.addEdge(new DirectedEdge(city2, city1, distance, price));
            //System.out.println();
        }
        scan = new Scanner(System.in);
        while(true) {
            System.out.println("Flight Options");
            System.out.println("1. Print Direct Routes");
            System.out.println("2. Display MST");
            System.out.println("3. Shortest Path on Miles");
            System.out.println("4. Cheapest Path");
            System.out.println("5. Least Number of Stops");
            System.out.println("6. All Routes Less than an Amount");
            System.out.println("7. Add A Route");
            System.out.println("8. Remove A Route");
            System.out.println("9. Exit (And Save)");
            System.out.print("Please Enter a number: ");
            int choice;
            while (true) {
                try {
                    String s = scan.nextLine();
                    choice = Integer.parseInt(s);
                    break;
                } catch (NumberFormatException e) {
                    System.out.print("Please Enter a number: ");
                }
            }
            String c1, c2;
            int city1 =0;
            int city2 =0;
            switch (choice) {
                case 1:
                    printDirect();
                    break;
                case 2:
                    mst();
                    break;
                case 3:

                    while(city1<=0){

                            printCityListExcludingCity(0);
                            System.out.print("Enter Departure City: ");
                            c1 = scan.nextLine();
                            city1 = intFromCity(c1);

                        }

                    while(city2<=0){

                            printCityListExcludingCity(city1);
                            System.out.print("Enter Destination City: ");
                            c2 = scan.nextLine();
                            city2 = intFromCity(c2);

                    }
                    shortestDistance(city1, city2);
                    break;
                case 4:

                    while(city1 <=0){

                        printCityListExcludingCity(0);
                        System.out.print("Enter Departure City: ");
                        c1 = scan.nextLine();
                        city1 = intFromCity(c1);


                    }
                    while(city2<=0) {

                        printCityListExcludingCity(city1);
                        System.out.print("Enter Destination City: ");
                        c2 = scan.nextLine();
                        city2 = intFromCity(c2);
                    }


                    cheapest(city1, city2);
                    break;
                case 5:
                    while(city1 <=0){

                        printCityListExcludingCity(0);
                        System.out.print("Enter Departure City: ");
                        c1 = scan.nextLine();
                        city1 = intFromCity(c1);


                    }
                    while(city2<=0) {

                        printCityListExcludingCity(city1);
                        System.out.print("Enter Destination City: ");
                        c2 = scan.nextLine();
                        city2 = intFromCity(c2);
                    }

                    printLeastNumberOfHops(city1, city2);
                    break;
                case 6:
                    while(true){
                        try{

                            System.out.print("Enter Cost: ");
                            city1 = Integer.parseInt(scan.nextLine());
                            break;
                        }catch (NumberFormatException e){
                            System.out.println("Enter A Number");
                        }
                    }
                    allLessThan(city1);
                    break;
                case 7:
                    while(city1 <=0){

                        printCityListExcludingCity(0);
                        System.out.print("Enter Departure City: ");
                        c1 = scan.nextLine();
                        city1 = intFromCity(c1);


                    }
                    while(city2<=0) {

                        printCityListExcludingCity(city1);
                        System.out.print("Enter Destination City: ");
                        c2 = scan.nextLine();
                        city2 = intFromCity(c2);
                    }

                    int distance;
                    while(true){
                        try{

                            //printCityListExcludingCity(city1);
                            System.out.print("Enter Distance: ");
                            distance = Integer.parseInt(scan.nextLine());
                            break;
                        }catch (NumberFormatException e){
                            System.out.println("Enter A Number");
                        }
                    }
                    double price;
                    while(true){
                        try{

                            //printCityListExcludingCity(city1);
                            System.out.print("Enter Cost: ");
                            price = Double.parseDouble(scan.nextLine());
                            break;
                        }catch (NumberFormatException e){
                            System.out.println("Enter A Number");
                        }
                    }
                    addFlight(city1, city2,distance,price);
                    break;
                case 8:
                    while(city1 <=0){

                        printCityListExcludingCity(0);
                        System.out.print("Enter Departure City: ");
                        c1 = scan.nextLine();
                        city1 = intFromCity(c1);


                    }
                    while(city2<=0) {

                        printCityListExcludingCity(city1);
                        System.out.print("Enter Destination City: ");
                        c2 = scan.nextLine();
                        city2 = intFromCity(c2);
                    }

                    remove(city1, city2);
                    break;
                case 9:
                    save(filename);

            }
            System.out.println();
        }

    }
    public static void printCityListExcludingCity(int exclude){
        for(int i =1; i<cities.length; i++){
            if(i == exclude){
                continue;
            }
            System.out.println(i + ". " + cities[i]);
        }
    }
    public static void printDirect(){
        //System.out.println("Printing Direct Routes:");
        for(int i=1; i<cities.length;i++ ){
            Iterator<DirectedEdge> edges = graph.adj(i).iterator();
            if(edges == null) continue;
            while(edges.hasNext()){
                DirectedEdge edge = edges.next();
                //edges.remove();
                int city1 =edge.from();
                int city2 = edge.to();
                int distance =(int) edge.weight();
                double price = edge.weight2();

                System.out.println("From " +cities[city1] + " to " + cities[city2] + " for a distance of " + distance + " miles costing $" + price );
            }
        }
    }
    public static void shortestDistance(int v1, int v2){
        DijkstraSP min = new DijkstraSP(graph, v1);
        //System.out.println(min.pathTo(v2));
        Iterator<DirectedEdge> nodes = min.pathTo(v2).iterator();
        System.out.println("Shortest distance from " + cities[v1]+ " to "+cities[v2]+ " is " + nodes.next().weight());
        System.out.println("Path with edges (in reverse order):");
        String print = "";
        while(nodes.hasNext()){
            DirectedEdge n = nodes.next();
            print = cities[n.to()]+ " " + n.weight() +" "+  print;
        }
        print+= cities[v1];
        System.out.println(print);

    }
    public static void printLeastNumberOfHops(int begin, int end){
        BreadthFirstPaths bfs = new BreadthFirstPaths(graph, begin);

        Iterable<Integer> path = bfs.pathTo(end);
        //System.out.println(path);
        String [] path2 = path.toString().split(" ");
        System.out.println("Least number of flights from " + cities[begin] + " to " + cities[end] + " is: " + (path2.length - 1));
        System.out.println("Path (In reverse order) ");
        for(int i =path2.length -1; i>=0; i--){
            System.out.print(cities[Integer.parseInt(path2[i])] + " ");
        }
        System.out.println();
    }
    public static void cheapest(int v1, int v2){
        DijkstraSP min = new DijkstraSP(graph, v1,1);
        Iterator<DirectedEdge> nodes = min.pathTo2(v2).iterator();
        System.out.println("Cheapest from " + cities[v1]+ " to "+cities[v2]+ " is " + (nodes.next().weight()));
        System.out.println("Path with edges (in reverse order):");
        String print = "";
        while(nodes.hasNext()){
            DirectedEdge n = nodes.next();
            print = cities[n.to()]+ " " + n.weight2() +" "+  print;
        }
        print+= cities[v1];
        System.out.println(print);

    }
    public static void addFlight(int v1, int v2, int miles, double price){

        graph.addEdge(new DirectedEdge(v1, v2, miles, price));
        graph.addEdge(new DirectedEdge(v2, v1, miles, price));
        //added.add(new DirectedEdge(v1, v2, miles, price));
        System.out.println("From " + cities[v1] + " to " + cities[v2] + " for a distance of " + miles + " miles costing $" + price);
    }
    public static void mst(){
        PrimMST p = new PrimMST(graph);
        //System.out.println(p.edges());
        Iterator<DirectedEdge> e = p.edges().iterator();
        while(e.hasNext()){
            DirectedEdge edge = e.next();
            System.out.println(cities[edge.to()] + "," + cities[edge.from()] + ": " + edge.weight());
        }
    }
    public static void allLessThan(int cost){


        int counter =0;
        ArrayList<String> route = new ArrayList<>();
        //System.out.println(min.path(1,3));
        for(int i =1; i<cities.length; i++) {
            for (int x = 1; x < cities.length; x++) {
                if (x == i) {
                    x++;
                    if (x == cities.length) {
                       break;
                    }
                }
                //System.out.println(getAllPaths(i, x));
                ArrayList<ArrayList<Integer>> allPaths = getAllPaths(i,x);
                for(int a =0; a<allPaths.size(); a++){
                    Iterator<Integer> path = allPaths.get(a).iterator();
                    int price =0;
                    int city1 = path.next();
                    int city2 = path.next();
                    String output = cities[city1];
                    while(true){
                        Iterator<DirectedEdge> e = graph.adj(city1).iterator();
                        while(e.hasNext()){
                            DirectedEdge edge = e.next();
                            if(edge.to() == city2){
                                price += edge.weight2();
                                output += " " + (int)edge.weight2() + " " + cities[city2];
                            }
                        }
                        if(!path.hasNext())
                            break;
                        city1 = city2;
                        city2 = path.next();
                    }
                    if(price<=cost){

                        String out = "Cost: " + price + " Path (reversed): " + output;
                        if(!route.contains(out)) {
                            System.out.println(out);
                            counter++;
                            route.add(out);
                        }
                    }

                }

            }
        }
        //System.out.println(counter);

    }
    public static void remove(int v1, int v2){
        Iterator<DirectedEdge> edges = graph.adj[v1].iterator();
        Stack<DirectedEdge> s = new Stack<>();
        while(edges.hasNext()){
            DirectedEdge e = edges.next();
            if(e.to() == v2){
                continue;
            }
            s.push(e);
        }
        graph.adj[v1] = new Bag<>();
        while(!s.isEmpty()){
            graph.adj[v1].add(s.pop());
        }

        edges = graph.adj[v2].iterator();
        s = new Stack<>();


        while(edges.hasNext()){
            DirectedEdge e = edges.next();
            if(e.to() == v1){
                continue;
            }
            s.push(e);
        }
        graph.adj[v2] = new Bag<>();
        while(!s.isEmpty()){
            graph.adj[v2].add(s.pop());
        }
        /*for(int i =0; i<added.size(); i++){
            if((v1 == added.get(i).from() && v2 == added.get(i).to()) ||(v2 == added.get(i).from() && v1 == added.get(i).to()) ){
                added.remove(i);
            }
        }*/
    }
    public static void save(String filename)throws IOException{
        File f = new File(filename);
        ArrayList<DirectedEdge> copies = new ArrayList<>();
        //File f = new File("airlineSave.txt");
        FileWriter writer = new FileWriter(f);
        writer.write((cities.length - 1) + "\n");
        for(int i = 1; i<cities.length; i++){
            writer.write(cities[i] + "\n");
        }
        for(int i=1; i<cities.length;i++ ) {
            Iterator<DirectedEdge> edges = graph.adj(i).iterator();
            if (edges == null) continue;
            while (edges.hasNext()) {
                DirectedEdge edge = edges.next();
                //edges.remove();
                int city1 = edge.from();
                int city2 = edge.to();
                int distance = (int) edge.weight();
                int price =(int) edge.weight2();
                //System.out.println("From " +cities[city1] + " to " + cities[city2] + " for a distance of " + distance + " miles costing $" + price );
                if(copies.size() == 0){
                    writer.write(city1 + " "+ city2 + " " + distance + " " + price + "\n"  );
                }
                for(int x = 0; x<copies.size(); x++){
                    if(copies.get(x).to() == city1 && copies.get(x).from() == city2){
                        break;
                    }
                    if(x == copies.size()-1){
                        writer.write(city1 + " "+ city2 + " " + distance + " " + price + "\n");
                    }
                }

                copies.add(edge);
            }
        }
        writer.close();
        System.exit(0);
    }
    public static int intFromCity(String city){
        for(int i =1; i<cities.length; i++){
            if(city.equalsIgnoreCase(cities[i])){
                return i;
            }
        }
        return -1;
    }

    public static  ArrayList<ArrayList<Integer>> getAllPaths(int source, int destination) {

        ArrayList<ArrayList<Integer>> paths = new ArrayList<ArrayList<Integer>>();
        recursive(source, destination, paths, new LinkedHashSet<Integer>());
        return paths;
    }

    // so far this dude ignore's cycles.
    private static void recursive (int c, int d, ArrayList<ArrayList<Integer>> paths, LinkedHashSet<Integer> path) {
        path.add(c);

        if (c == d) {
            paths.add(new ArrayList<Integer>(path));
            path.remove(c);
            return;
        }

        ArrayList<Integer> edges = new ArrayList<>();
        Iterator<DirectedEdge> e = graph.adj(c).iterator();
        while(e.hasNext()){
            edges.add(e.next().to());
        }

        for (int t : edges) {
            if (!path.contains(t)) {
                recursive (t, d, paths, path);
            }
        }

        path.remove(c);
    }

}
