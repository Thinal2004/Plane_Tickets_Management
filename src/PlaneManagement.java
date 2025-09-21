import java.util.Scanner;

public class PlaneManagement {

    static int [][] seats;      //declare seats 2D array
    static Ticket [] tickets = new Ticket[52];      //declare and initialise tickets array to store tickets (can store up to 52 tickets)
    static int ticketCount = 0;     //stores number of booked ticket count

    public static void main(String [] args){
        System.out.println("Welcome to the Plane Management application");

        initialiseRows();   //call the method to initialise rows and seats

        boolean show_menu_again = true;
        //loop will iterate until user enters 0
        while(show_menu_again){
            int option = showMenu();
            //call the relevant methods when user inputs the option number
            switch(option){
                case 1 :
                    buy_seat();
                    break;
                case 2 :
                    cancel_seat();
                    break;
                case 3 :
                    find_first_available();
                    break;
                case 4 :
                    show_seating_plan();
                    break;
                case 5 :
                    print_tickets_info();
                    break;
                case 6 :
                    search_ticket();
                    break;
                case 0 :
                    show_menu_again = false;
                    System.out.println("Program ended.");
                    break;
                default:
                    System.out.println("Enter a valid option");
            }
        }
    }

    //initialise rows and seat count
    public static void initialiseRows(){
        seats = new int[4][];
        seats[0] = new int [14];
        seats[1] = new int [12];
        seats[2] = new int [12];
        seats[3] = new int [14];
    }

    //show menu in the console and returns the option
    public static int showMenu(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("**************************************");
        System.out.println("*           MENU OPTIONS             *");
        System.out.println("**************************************");
        System.out.println("    1) Buy a seat");
        System.out.println("    2) Cancel a seat");
        System.out.println("    3) Find first available seat");
        System.out.println("    4) Show seating plan");
        System.out.println("    5) Print tickets information and total sales");
        System.out.println("    6) Search ticket");
        System.out.println("    0) Quit");
        System.out.println("**************************************");
        System.out.print("Please select an option: ");

        int option = scanner.nextInt();
        return option;      //return the option number
    }

    //assign a row number to row letter
    public static int row_number_assign(String row){
        int row_num = -1;
        switch (row.toUpperCase()){
            case "A":
                row_num = 0;
                break;
            case "B":
                row_num = 1;
                break;
            case "C":
                row_num = 2;
                break;
            case "D":
                row_num = 3;
                break;
            default:
                System.out.println("Invalid row!");
        }

        return row_num;      //return the corresponding row number of the row letter
    }

    //to check whether the seat number is valid or not
    public static boolean validate_seat(int row_num,int seat_num){

            if((row_num == 0 || row_num == 3) & (seat_num>=0 & seat_num<=13)){
                return true;
            }
            else if((row_num == 1 || row_num == 2) & (seat_num>=0 & seat_num<=11)){
                return true;
            }
            else{
                System.out.println("Invalid seat number!");
                return false;
            }
    }

    public static void buy_seat(){
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your first name : ");
        String name = scanner.nextLine();
        System.out.print("Enter your surname : ");
        String surname = scanner.nextLine();
        System.out.print("Enter your email : ");
        String email = scanner.nextLine();

        // create an object from Person class
        Person person = new Person(name,surname,email);

        String row = null;
        int row_num = -1;
        int seat_num = -1;
        boolean checked = false;
        int ticketPrice;

        //program will ask for the row and the seat until user enters valid inputs
        while(!checked){
            System.out.print("Enter the row letter :");
            row = scanner.next();

            row_num = row_number_assign(row);   //call the method and assign the relevant row number

            if(row_num != -1){
                System.out.print("Enter the seat number :");
                seat_num = scanner.nextInt() - 1;

                checked = validate_seat(row_num,seat_num);      //call the method and return true if the seat and row is valid; unless return false
            }
        }

        //set ticket price according to the seat
        if(seat_num <= 4){
            ticketPrice = 200;
        } else if (seat_num <= 8) {
            ticketPrice = 150;
        }
        else{
            ticketPrice = 180;
        }

        //book the ticket if the seat is available
        if(seats[row_num][seat_num] == 0){
            seats[row_num][seat_num] = 1;

            //create a ticket object
            Ticket ticket = new Ticket(row,seat_num,ticketPrice,person);

            //add the ticket to the tickets array
            if(ticketCount < tickets.length){
                tickets[ticketCount] = ticket;
                ticketCount ++;
                System.out.println("Tickets booked : " + ticketCount);
            }

            //call the save method to create a file with ticket info
            ticket.save(row,seat_num);

            System.out.println("Seat booked successfully");
        }
        else{
            System.out.println("Seat has already booked");
        }
    }

    //to cancel to booked ticket
    public static void cancel_seat(){
        Scanner scanner = new Scanner(System.in);

        String row = null;
        int row_num = -1;
        int seat_num = -1;
        boolean checked = false;

        while(!checked){
            System.out.print("Enter the row letter :");
            row = scanner.next();

            row_num = row_number_assign(row);

            if(row_num != -1){
                System.out.print("Enter the seat number :");
                seat_num = scanner.nextInt() - 1;

                checked = validate_seat(row_num,seat_num);
            }
        }

        if(seats[row_num][seat_num] == 1){
            seats[row_num][seat_num] = 0;

            for(int i=0;i<ticketCount;i++){
                if(tickets[i] != null && tickets[i].getRow().equals(row) && tickets[i].getSeat() == seat_num){
                    for(int j =i;j< ticketCount-1;j++){
                        tickets[j] = tickets[j+1];
                    }
                    tickets[ticketCount-1] = null;
                    ticketCount--;
                    break;
                }
            }

            System.out.println("Seat booking canceled successfully.");
        }
        else{
            System.out.println("Seat is not booked");
        }
    }

    public static void find_first_available(){
        for(int row=0;row< seats.length;row++){
            for(int seat=0;seat<seats[row].length;seat++){
                if(seats[row][seat] == 0){
                    System.out.println("First available seat is : row " + (row+1) + " seat number " + (seat+1) );
                    return;
                }
            }
        }
        System.out.println("All the seats are booked.");
    }

    public static void show_seating_plan(){
        for(int row=0;row< seats.length;row++){
            for(int seat=0;seat<seats[row].length;seat++){
                if(seats[row][seat] == 0){
                    System.out.print("O " );
                }
                else{
                    System.out.print("X ");
                }
            }
            System.out.print("\n");
        }
    }

    public static void print_tickets_info(){
        int total_tickets_price = 0;
        for(int i = 0; i < ticketCount ; i++){
            tickets[i].printInfo();
            total_tickets_price += tickets[i].getPrice();
            System.out.print("\n");
        }
        System.out.println("Total price of tickets : £" + total_tickets_price);
        System.out.print("\n");
    }

    public static void search_ticket(){
        Scanner scanner = new Scanner(System.in);

        String row = null;
        int row_num = -1;
        int seat_num = -1;
        boolean checked = false;

        while(!checked){
            System.out.print("Enter the row letter :");
            row = scanner.next();

            row_num = row_number_assign(row);

            if(row_num != -1){
                System.out.print("Enter the seat number :");
                seat_num = scanner.nextInt() - 1;

                checked = validate_seat(row_num,seat_num);
            }
        }

        if(seats[row_num][seat_num] == 1){
            for(Ticket tk : tickets){
                if(tk != null && tk.getRow().equals(row) && tk.getSeat() == seat_num){
                    tk.printInfo();
                }
            }
        }
        else{
            System.out.println("This seat is available");
        }

    }

}
