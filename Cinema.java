package cinema;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class Cinema {

    private static String[][] cinemaRoom;
    private static int rows;
    private static int seatsInRow;
    private static int currentIncome;
    private static int busySeats = 0;
    private static DecimalFormat df = new DecimalFormat("0.00");

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the number of rows:");
        rows = sc.nextInt();
        System.out.println("Enter the number of seats in each row:");
        seatsInRow = sc.nextInt();
        cinemaRoom = new String[rows][seatsInRow];
        fillCinemaRoom();
        System.out.println();

        while (true) {
            printMenu();
            int totalSeats = rows * seatsInRow;
            int action = sc.nextInt();

            switch (action){
                case 1:
                    printSeats();
                    break;
                case 2:
                    buyTicket(sc, totalSeats, rows);
                    break;
                case 3:
                    showStatistics(totalSeats);
                    break;
                case 0:
                    return;
            }
        }
    }

    private static void showStatistics(int totalSeats) {

        System.out.println("Number of purchased tickets: " + busySeats);
        float percentage = (((100 * busySeats) / (float)totalSeats));
        System.out.println("Percentage: " + df.format(percentage).replace(",", ".") + "%");

        System.out.println("Current income: $" + currentIncome);

        int totalSeatsMoney = 0;
        if (totalSeats <= 60) {
            totalSeatsMoney += totalSeats * 10;
        } else {
            int first = (rows / 2) * seatsInRow * 10;
            int sec = (rows - (rows / 2)) * seatsInRow * 8;
            totalSeatsMoney += first + sec;
        }
        System.out.println("Total income: $" + totalSeatsMoney);

        System.out.println();
    }

    private static void fillCinemaRoom() {
        for(String[] row : cinemaRoom) {
            Arrays.fill(row, "S");
        }
    }

    private static void buyTicket(Scanner sc, int totalSeats, int rowNum) {
        while (true) {
            System.out.println("Enter a row number:");
            int selectedRowNumber = sc.nextInt();
            System.out.println("Enter a seat number in that row:");
            int selectedSeatNumber = sc.nextInt();
            System.out.println();

            if (selectedRowNumber > rows || selectedSeatNumber > seatsInRow
                    || Integer.signum(selectedRowNumber) <= 0 || Integer.signum(selectedSeatNumber) <= 0) {
                System.out.println("Wrong input!\n");
                continue;
            }

            if (Objects.equals(cinemaRoom[selectedRowNumber - 1][selectedSeatNumber - 1], "B")) {
                System.out.println("That ticket has already been purchased!\n");
                continue;
            }

            cinemaRoom[selectedRowNumber - 1][selectedSeatNumber - 1] = "B";
            int ticketPrice = totalSeats <= 60 ? 10 : selectedRowNumber <= rowNum / 2 ? 10 : 8;
            busySeats += 1;
            currentIncome += ticketPrice;
            System.out.println("Ticket price: $" + ticketPrice + "\n");
            break;
        }
    }

    private static void printMenu() {
        System.out.println("1. Show the seats");
        System.out.println("2. Buy a ticket");
        System.out.println("3. Statistics");
        System.out.println("0. Exit");
    }

    public static void printSeats(){
        System.out.println("Cinema:");
        StringBuilder firstLine = new StringBuilder("  ");
        for (int i = 1; i <= seatsInRow; i++) {
            firstLine.append(i).append(" ");
        }
        System.out.println(firstLine);
        int seatsNum = 1;
        for (String[] row : cinemaRoom) {
            System.out.print(seatsNum + " ");
            for(String s : row) {
                System.out.print(s + " ");
            }
            System.out.println();
            seatsNum++;
        }
        System.out.println();
    }
}