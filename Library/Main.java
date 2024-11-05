import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        new LibraryGUI();

        Scanner scanner = new Scanner(System.in);

        Library myLibrary = new Library();
        ArrayList<Book> sessionBooks = new ArrayList<>();

        while (true) {
            System.out.println("Welcome to the library! \nPlease enter the number of the action You wish to perform:");
            System.out.println("1. Add new books \n2. View all books \n3. Exit");

            int userChoice = scanner.nextInt();
            scanner.nextLine();

            if (userChoice == 1){
                while (true) {
                    Book newBook = myLibrary.addBookFromInput();
                    sessionBooks.add(newBook);

                    myLibrary.saveBooksToFile();
                    System.out.println("Book saved to file.");
        
                    System.out.println("Would you like to add another book? (Yes/No)");
                    String addMore = scanner.nextLine();
                    if (addMore.equalsIgnoreCase("No")) {
                        break;
                    }
                }
            } else if (userChoice == 2) {
                myLibrary.displayAllBooks();
            } else if (userChoice == 3) {
                break;
            }
        }

        scanner.close();
    }
}