import java.io.FileWriter;
import java.io.IOException;

public class Ticket {
    private String row;
    private int seat;
    private int price;
    private Person person;

    public Ticket(String row, int seat, int price, Person person) {
        this.row = row;
        this.price = price;
        this.seat = seat;
        this.person = person;
    }

    public String getRow() {
        return row;
    }

    public void setRow(String row) {
        this.row = row;
    }

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public void printInfo(){
        System.out.println("Name : " + person.getName());
        System.out.println("Surname : " + person.getSurname());
        System.out.println("Email : " + person.getEmail());
        System.out.println("Row : " + row);
        System.out.println("Seat : " + (seat+1));
        System.out.println("Price : " + price);
    }

    public void save(String row,int seat_num){

        String fileName = row + "_" + (seat_num+1) + ".txt";
        try{
            FileWriter writer = new FileWriter(fileName);
            writer.write("Seat : " + row+(seat_num+1) +"\n");
            writer.write("Name : " + person.getName() + " " + person.getSurname()+"\n");
            writer.write("Ticket price : " + price+"\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
