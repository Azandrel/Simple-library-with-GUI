import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileReader;
import java.io.BufferedReader;

public class Library {
    
    private ArrayList<Book> books;

    public Library() {
        books = new ArrayList<>();
        loadBooksFromFile();
    }

    public void addBook(Book book) {
        books.add(book);
        System.out.println("Book added: " + book.getTitle());
    }

    public void displayAllBooks(){
        for (int i = 0; i < books.size(); i++) {
                Book book = books.get(i);
                book.displayInfo();
                System.out.println("------");
            }
    }

    public ArrayList<Book> getBooks() {
        return books;
    }
    
    public Book findBookByTitle(String title) {
        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            if (book.getTitle().equalsIgnoreCase(title)) {
                return book;
            } 
        }
        return null;
    }

    public void removeBook(String title) {
        Book bookToRemove = findBookByTitle(title);
        if(bookToRemove != null) {
            books.remove(bookToRemove);
        } else {
            System.out.println("Book not found!");
        }
    }

    public Book addBookFromInput() {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Please enter the book title: ");
        String title = scanner.nextLine();
        
        System.out.println("Please enter the book author: ");
        String author = scanner.nextLine();
        
        System.out.println("Please enter the year of publishing: ");
        int yearPublished = scanner.nextInt();
        
        Book newBook = new Book(title, author, yearPublished);
        
        addBook(newBook);
        
        return newBook;
    }

    public void saveBooksToFile() {
        BufferedWriter writer = null;
        try {
            FileWriter fileWriter = new FileWriter("library.txt");
            writer = new BufferedWriter(fileWriter);
    
            for (int i = 0; i < books.size(); i++) {
                Book book = books.get(i); // Access each book by index
                String bookDetails = book.getTitle() + "," + book.getAuthor() + "," + book.getYearPublished();
                writer.write(bookDetails); // Write book details to the file
                writer.newLine(); // Add a new line for the next book
            }
        } catch (IOException e) {
            System.out.println("An error occurred while saving books to file: " + e.getMessage());
        } finally {
            try {
                if (writer != null) {
                    writer.close(); // Ensure the writer is closed after use
                }
            } catch (IOException e) {
                System.out.println("An error occurred while closing the writer: " + e.getMessage());
            }
        }
    }
    
    public void loadBooksFromFile() {
        BufferedReader reader = null;
        try {
            // Open the file with FileReader and wrap it with BufferedReader for efficient reading
            reader = new BufferedReader(new FileReader("library.txt"));
            String line;
    
            // Read each line until the end of the file
            while ((line = reader.readLine()) != null) {
                // Split the line by commas to get title, author, and yearPublished
                String[] bookData = line.split(",");
                if (bookData.length == 3) { // Check to ensure the line has the expected format
                    String title = bookData[0];
                    String author = bookData[1];
                    int yearPublished = Integer.parseInt(bookData[2]); // Convert year to integer
    
                    // Create a new Book object and add it to the books list
                    Book book = new Book(title, author, yearPublished);
                    books.add(book);
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while loading books from file: " + e.getMessage());
        } finally {
            // Ensure the BufferedReader is closed after use
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                System.out.println("An error occurred while closing the file reader: " + e.getMessage());
            }
        }
    }    
}
