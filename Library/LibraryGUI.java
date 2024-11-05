import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LibraryGUI {
    private JFrame frame;
    private Library myLibrary;

    public LibraryGUI() {
        // Initialize library
        myLibrary = new Library();

        // Create and set up the window
        frame = new JFrame("Library System");
        frame.setSize(400, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null); // Use absolute positioning for simplicity
        
        addComponents();

        // Display the window
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new LibraryGUI(); // Create and show the GUI
    }

    private void addComponents() {
        // Create and position labels and text fields for book details
        JLabel titleLabel = new JLabel("Title:");
        titleLabel.setBounds(20, 20, 100, 30);
        frame.add(titleLabel);

        JTextField titleField = new JTextField();
        titleField.setBounds(120, 20, 200, 30);
        frame.add(titleField);

        JLabel authorLabel = new JLabel("Author:");
        authorLabel.setBounds(20, 60, 100, 30);
        frame.add(authorLabel);

        JTextField authorField = new JTextField();
        authorField.setBounds(120, 60, 200, 30);
        frame.add(authorField);

        JLabel yearLabel = new JLabel("Year:");
        yearLabel.setBounds(20, 100, 100, 30);
        frame.add(yearLabel);

        JTextField yearField = new JTextField();
        yearField.setBounds(120, 100, 200, 30);
        frame.add(yearField);

        // Button to add a book
        JButton addButton = new JButton("Add Book");
        addButton.setBounds(20, 140, 150, 30);
        frame.add(addButton);

        // Button to view all books
        JButton viewButton = new JButton("View All Books");
        viewButton.setBounds(180, 140, 150, 30);
        frame.add(viewButton);

        // Text area to display books
        JTextArea displayArea = new JTextArea();
        displayArea.setBounds(20, 180, 340, 150);
        displayArea.setEditable(false); // Make it read-only
        frame.add(displayArea);

        // Search field and button
        JLabel searchLabel = new JLabel("Search Book:");
        searchLabel.setBounds(20, 340, 100, 30);
        frame.add(searchLabel);

        JTextField searchField = new JTextField();
        searchField.setBounds(120, 340, 200, 30);
        frame.add(searchField);

        JButton searchButton = new JButton("Search Book");
        searchButton.setBounds(120, 380, 150, 30);
        frame.add(searchButton);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the title from the search field
                String title = searchField.getText();
                
                // Search for the book in the library
                Book foundBook = myLibrary.findBookByTitle(title);
                
                // Display the result
                if (foundBook != null) {
                    // Display book details if found
                    displayArea.setText("Book found:\n" + 
                                        "Title: " + foundBook.getTitle() + "\n" +
                                        "Author: " + foundBook.getAuthor() + "\n" +
                                        "Year: " + foundBook.getYearPublished());
                } else {
                    // Show message if the book is not found
                    displayArea.setText("Book not found in the library.");
                }
                
                // Clear the search field after search
                searchField.setText("");
            }
        });
        

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = titleField.getText();
                String author = authorField.getText();
                int year;
        
                // Try to parse the year input
                try {
                    year = Integer.parseInt(yearField.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid year format. Please enter a number.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
        
                // Add book to library
                Book newBook = new Book(title, author, year);
                myLibrary.addBook(newBook);
        
                // Save the book to file immediately after adding
                myLibrary.saveBooksToFile();
                JOptionPane.showMessageDialog(frame, "Book added and saved successfully!");
        
                // Clear the input fields
                titleField.setText("");
                authorField.setText("");
                yearField.setText("");
            }
        });
        

        // Add ActionListener for View All Books button
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Retrieve and display all books
                StringBuilder allBooks = new StringBuilder();
                for (Book book : myLibrary.getBooks()) {
                    allBooks.append(book.getTitle()).append(" by ").append(book.getAuthor()).append(" (").append(book.getYearPublished()).append(")\n");
                }
                displayArea.setText(allBooks.toString());
            }
        });
    }
}
