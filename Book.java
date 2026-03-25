public class Book {
    private int id;
    private String title;
    private String author;
    private boolean isIssued;

    public Book(int id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isIssued = false;
    }

    // Getters and Setters
    public int getId() { return id; }
    public String getTitle() { return title; }
    public boolean isIssued() { return isIssued; }
    public void setIssued(boolean issued) { isIssued = issued; }

    @Override
    public String toString() {
        return String.format("ID: %d | Title: %-20s | Author: %-15s | Status: %s", 
                id, title, author, (isIssued ? "Borrowed" : "Available"));
    }
}