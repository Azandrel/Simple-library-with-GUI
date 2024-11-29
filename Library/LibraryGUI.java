import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LibraryGUI {
    private JFrame frame;
    private Library myLibrary;
    private String oldTitle; // Track the old title for editing

    public LibraryGUI() {
        myLibrary = new Library();

        frame = new JFrame("Library System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);

        addComponents();

        frame.setVisible(true);
    }

    private void addComponents() {
        // Create Components
        JLabel titleLabel = new JLabel("Title:");
        JTextField titleField = new JTextField();
        JLabel authorLabel = new JLabel("Author:");
        JTextField authorField = new JTextField();
        JLabel yearLabel = new JLabel("Year:");
        JTextField yearField = new JTextField();

        JButton addButton = new JButton("Add Book");
        JButton viewButton = new JButton("View All Books");
        JButton editButton = new JButton("Edit Book");

        JTextArea displayArea = new JTextArea();
        displayArea.setEditable(false);

        JLabel searchLabel = new JLabel("Search:");
        JTextField searchField = new JTextField();
        JComboBox<String> searchCriteria = new JComboBox<>(new String[]{"Title", "Author", "Year"});
        JButton searchButton = new JButton("Search");

        // Set Layout
        GroupLayout layout = new GroupLayout(frame.getContentPane());
        frame.setLayout(layout);

        layout.setAutoCreateGaps(true); // Adds gaps between components
        layout.setAutoCreateContainerGaps(true); // Adds gaps around the container

        // Horizontal Group
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(titleLabel)
                    .addComponent(titleField))
                .addGroup(layout.createSequentialGroup()
                    .addComponent(authorLabel)
                    .addComponent(authorField))
                .addGroup(layout.createSequentialGroup()
                    .addComponent(yearLabel)
                    .addComponent(yearField))
                .addGroup(layout.createSequentialGroup()
                    .addComponent(addButton)
                    .addComponent(viewButton)
                    .addComponent(editButton))
                .addComponent(displayArea)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(searchLabel)
                    .addComponent(searchField)
                    .addComponent(searchCriteria)
                    .addComponent(searchButton))
        );

        // Vertical Group
        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(titleLabel)
                    .addComponent(titleField))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(authorLabel)
                    .addComponent(authorField))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(yearLabel)
                    .addComponent(yearField))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(addButton)
                    .addComponent(viewButton)
                    .addComponent(editButton))
                .addComponent(displayArea)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(searchLabel)
                    .addComponent(searchField)
                    .addComponent(searchCriteria)
                    .addComponent(searchButton))
        );

        // Add Action Listeners
        addListeners(addButton, viewButton, editButton, searchButton, titleField, authorField, yearField, searchField, searchCriteria, displayArea);
    }

    private void addListeners(
        JButton addButton, JButton viewButton, JButton editButton, JButton searchButton,
        JTextField titleField, JTextField authorField, JTextField yearField,
        JTextField searchField, JComboBox<String> searchCriteria, JTextArea displayArea
    ) {
        addButton.addActionListener(e -> {
            String title = titleField.getText();
            String author = authorField.getText();
            int year;
            try {
                year = Integer.parseInt(yearField.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid year format.");
                return;
            }

            Book newBook = new Book(title, author, year);
            myLibrary.addOrUpdateBook(newBook, oldTitle); // Use oldTitle for updates
            myLibrary.saveBooksToFile();

            JOptionPane.showMessageDialog(frame, "Book saved successfully!");
            titleField.setText("");
            authorField.setText("");
            yearField.setText("");
            oldTitle = null; // Reset oldTitle after saving
        });

        viewButton.addActionListener(e -> {
            StringBuilder allBooks = new StringBuilder();
            for (Book book : myLibrary.getBooks()) {
                allBooks.append(book.getTitle())
                        .append(" by ")
                        .append(book.getAuthor())
                        .append(" (")
                        .append(book.getYearPublished())
                        .append(")\n");
            }
            displayArea.setText(allBooks.toString());
        });

        searchButton.addActionListener(e -> {
            String criteria = (String) searchCriteria.getSelectedItem();
            String searchText = searchField.getText();
            Book foundBook = null;

            if ("Title".equals(criteria)) foundBook = myLibrary.findBookByTitle(searchText);
            else if ("Author".equals(criteria)) foundBook = myLibrary.findBookByAuthor(searchText);
            else if ("Year".equals(criteria)) {
                try {
                    int year = Integer.parseInt(searchText);
                    foundBook = myLibrary.findBookByYear(year);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid year format.");
                    return;
                }
            }

            if (foundBook != null) {
                displayArea.setText("Found Book:\n" + foundBook.getTitle() + " by " +
                        foundBook.getAuthor() + " (" + foundBook.getYearPublished() + ")");
            } else {
                displayArea.setText("Book not found.");
            }
        });

        editButton.addActionListener(e -> {
            String criteria = (String) searchCriteria.getSelectedItem();
            String searchText = searchField.getText();
            Book foundBook = null;

            if ("Title".equals(criteria)) foundBook = myLibrary.findBookByTitle(searchText);
            else if ("Author".equals(criteria)) foundBook = myLibrary.findBookByAuthor(searchText);
            else if ("Year".equals(criteria)) {
                try {
                    int year = Integer.parseInt(searchText);
                    foundBook = myLibrary.findBookByYear(year);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid year format.");
                    return;
                }
            }

            if (foundBook != null) {
                oldTitle = foundBook.getTitle();
                titleField.setText(foundBook.getTitle());
                authorField.setText(foundBook.getAuthor());
                yearField.setText(String.valueOf(foundBook.getYearPublished()));
                JOptionPane.showMessageDialog(frame, "Edit the details and press 'Add Book' to save changes.");
            } else {
                JOptionPane.showMessageDialog(frame, "Book not found.");
            }
        });
    }
}
