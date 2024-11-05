import java.time.Year;

public class Book {
    private String title;
    private String author;
    private int yearPublished;

    public Book(String title, String author, int yearPublished) {
        this.title = title;
        this.author = author;
        setYearPublished(yearPublished); // Use the setter to apply validation
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYearPublished() {
        return yearPublished;
    }

    public void setYearPublished(int yearPublished) {
        int currentYear = Year.now().getValue(); // Get the current year
        if (yearPublished > 0 && yearPublished <= currentYear) {
            this.yearPublished = yearPublished;
        } else {
            System.out.println("Invalid year. Please enter a valid publication year.");
        }
    }

    // Method to check if the book is a classic
    public boolean isClassic() {
        return yearPublished < 1970;
    }

    public void displayInfo() {
        System.out.println("Title: " + title);
        System.out.println("Author: " + author);
        System.out.println("Year of publishing: " + yearPublished);
        System.out.println("Classic: " + (isClassic() ? "Yes" : "No"));
    }
}
