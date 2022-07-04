import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.text.DecimalFormat;
// Homework 1: Sales Register Program
// Course: CIS357
// Due date: 7/5/22
// Name: Nick Hood
// GitHub: https://github.com/nick16hood/cis357-hw1-Hood.git
// Instructor: Il-Hyung Cho
// Program description: this program will read a txt file and creating an array of products. Then in main it asks the user if they would like to start a new sale
//in which the user will answer yes or no, if yes then the user will input an item code for the item they would like to purchase and the quantity. The program then
//displays the total amount and then asks for another product code. This continues until the user inputs -1, or they reach 100 items then it will provide an item list,
//subtotal, total with tax, and asks for tendered amount then provide how much change the customer receives. Then asks if they would like to begin a new sale
//if yes then a new sale begins until the user answers no. Once the user answers no the program displays the grand total from all sales and ends the program


/**
 * @author Nicholas
 * main class where the sales take place
 * */
public class CashRegister {
    /** where the main function of the program is. It allows the user to input an item code and quantity to make as many sales as they please */
    public static void main(String[] args) {


        DecimalFormat df = new DecimalFormat("#.00");
        //answer for if you want to start a new sale
        String answer;
        //flag to check if productCode is -1 and if so end shopping trip
        Boolean flag1 = true;
        //flag to check if answer to begin new sale is no if so end shopping trip
        Boolean flag2 = true;
        //code for getting items name from p.getName and to use as a parameter for p.getPrice
        Integer productCode;
        //for the quantity
        Integer quantity;
        //total for individual shopping trip
        Double total;
        //total for individual sale with tax
        Double taxtotal = 0.00;
        //total for entire shopping spree
        Double grandTotal = 0.00;
        Scanner s = new Scanner(System.in);
        //counter for placing boughtitem into boughtItems array
        Integer count;
        //variable for the amount of cash paid
        Integer payment;

        //calls product class to use the methods and get the objects attributes
        Product p = new Product();

        System.out.println("Welcome to Hood cash register system!");

        while (flag2) {
            flag1 =true;
            total = 0.00;
            count =0;
            //array to keep the quantity, name and price of the item bought
            Product[] boughtItems = new Product[100];
            System.out.print("Beginning a new sale (Y/N) ");
            answer = s.next().toUpperCase();
            if (answer.equals(("Y"))) {
                System.out.println("-----------------------------");
                outerloop:
                while (flag1) {
                    System.out.print("Enter product code: ");
                    Scanner s1 = new Scanner(System.in);
                    //checks to make sure the product code is an integer
                    if (s1.hasNextInt()){
                        productCode = s1.nextInt();
                        //checks to mak sure product code is not the code for ending the sale
                        if (productCode != -1) {
                            //checks to make sure product code is a code within the products list
                            if (productCode >= 1 && productCode <= p.items.length) {
                                System.out.println("         item name: " + p.getName(productCode));
                                while (flag1) {
                                    System.out.print("Enter quantity: ");
                                    Scanner s2 = new Scanner(System.in);
                                    //checks for quantity to be a proper amount ie integer and not a letter or half
                                    if (s2.hasNextInt()) {
                                        quantity = s2.nextInt();
                                        total = total + p.getPrice(quantity, productCode);
                                        System.out.println("       item total:  $ " + df.format(p.getPrice(quantity, productCode)));
                                        if (count == 0) {
                                            boughtItems[count] = new Product(quantity, p.getName((productCode)), p.getPrice(quantity, productCode));
                                            count++;
                                        }
                                        else {
                                            if (sameItem(boughtItems, p.getName(productCode))) {
                                                sameItemAdd(boughtItems, p.getName(productCode), productCode, quantity);
                                            }
                                            else {
                                                boughtItems[count] = new Product(quantity, p.getName((productCode)), p.getPrice(quantity, productCode));
                                                count++;
                                            }
//                                            for (Product pr : boughtItems) {
//                                                System.out.println(pr.itemName + " , " + p.getName(productCode));
//                                                if (pr != null && pr.itemName == p.getName(productCode)) {
//                                                    pr.itemCode = pr.itemCode + quantity;
//                                                    pr.itemPrice = pr.itemPrice + p.getPrice(quantity, productCode);
//                                                    System.out.println("added together");
//                                                    break;
//                                                }
//                                                else {
//                                                    boughtItems[count] = new Product(quantity, p.getName((productCode)), p.getPrice(quantity, productCode));
//                                                    count++;
//                                                    System.out.println("added new product");
//                                                    break;
//                                                }
//                                            }
                                        }
                                        System.out.println();
                                        if (count == boughtItems.length) {
                                            System.out.println("You have reached your maximum limit. Please start a new sale to continue.");
                                            break outerloop;
                                        }
                                        break;
                                    }
                                    else {
                                        System.out.println("!!! Invalid quantity");
                                        System.out.println();
                                    }
                                }
                            }
                            else {
                                System.out.println("!!! Invalid product code");
                                System.out.println();
                            }
                        }
                        else {
                            flag1 = false;
                        }
                    }
                    else {
                        System.out.println("!!! Invalid product code");
                        System.out.println();
                    }
                }

                arraySort(boughtItems);
                System.out.println("Items list:");
                //for loop to loop through all bought items and print them in the item list
                for (Product pr : boughtItems) {
                    if (pr != null) {
                        System.out.printf("      " + pr.itemCode + " " + pr.itemName + "         $ " + df.format(pr.itemPrice) + "\n");
                    }
                    else {
                        break;
                    }
                }
                grandTotal = grandTotal +total;
                System.out.println("Subtotal               $ " + df.format(total));
                taxtotal = (total + (total*.06));
                System.out.println("Total with tax (6%)    $ " + df.format(taxtotal));
                Boolean finalflag = true;
                while (finalflag) {
                    System.out.print("Tendered amount        $ ");
                    Scanner pay = new Scanner(System.in);
                    payment = pay.nextInt();
                    if(payment >= (total + (total*.06))) {
                        finalflag = false;
                        System.out.println("Change                 $ " + df.format(payment - taxtotal));
                        System.out.println("-----------------------------");
                    }
                }
            }
            else {

                flag2 = false;
            }
        }
        System.out.println("Your total sale for the day is $ " + df.format(grandTotal));
        System.out.println("Thank you for using POST system. Goodbye.");


    }

    public static Boolean sameItem(Product[] items, String name) {
        Product p = new Product();
        for (int i= 0; i < items.length -1; i++) {
            if (items[i] != null) {
                if (items[i].itemName == name) {

                    return true;
                }
            }
        }
        return false;
    }
    public static Product[] sameItemAdd(Product[] items, String name, Integer code, Integer quantity) {
        Product p = new Product();
        for (int i= 0; i < items.length -1; i++) {
            if (items[i] != null) {
                if (items[i].itemName == name) {
                    items[i].itemCode = items[i].itemCode + quantity;
                    items[i].itemPrice = items[i].itemPrice + p.getPrice(quantity, code);
                    return items;
                }
            }
        }
        return items;
    }
    public static Product[] arraySort(Product[] items)
    {
        outerloop:
        for(int i = 0; i < items.length - 1; i++) {

            for(int j = i+1; j < items.length; j++) {

                if (items[j] != null) {
                    if(items[i].itemName.compareTo(items[j].itemName) > 0) {
                        Product temp = items[i];
                        items[i] = items[j];
                        items[j] = temp;
                    }
                }
            }
        }
        return items;
    }

    /**
     * @author Nicholas
     * class for reading a txt file and creating an array of products with a code, name, and price field and to get the name or price of a specific item using its code
     * */
    public static class Product {
        Product[] items = new Product[10];

        private int itemCode;
        private String itemName;
        private double itemPrice;

        /** creates a product object with the following fields */
        Product(int itmCode, String itmName, double itmPrice) {
            this.itemCode = itmCode;
            this.itemName = itmName;
            this.itemPrice = itmPrice;

        }

        Integer index =0;

        /** reads the txt file and creates an array of the products */
        Product() {

            try {
                Scanner productfile = new Scanner(new File("C:/Users/Nicholas/IdeaProjects/HW1/src/HW1Products.txt"));
                while (productfile.hasNext()) {
                    String[] item = productfile.nextLine().split(", ");
                    itemCode = Integer.valueOf(item[0]);
                    itemName = item[1];
                    itemPrice = Double.valueOf(item[2]);

                    items[index] = new Product(itemCode, itemName, itemPrice);
                    index++;
                }
                productfile.close();
            }
            catch (IOException ex){
                System.out.println(ex.toString());
            }
        }


        /** used to get a specific products name using its code */
        public String getName(int code){
            return items[code-1].itemName;
        }

        /** used to get the price of a specific product using its code */
        public Double getPrice(int quantity, int code){
            return (items[code-1].itemPrice)*quantity;
        }

    }

}
