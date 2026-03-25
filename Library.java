import java.util.*;

public class Library {
    private List<Book> books = new ArrayList<>();

    public void addBook(Book book) {
        books.add(book);
    }

    public void showAllBooks() {
        System.out.println("\n--- Library Catalog ---");
        for (Book b : books) System.out.println(b);
    }

    public void issueBook(int id) {
        for (Book b : books) {
            if (b.getId() == id && !b.isIssued()) {
                b.setIssued(true);
                System.out.println("✅ Book issued successfully!");
                return;
            }
        }
        System.out.println("❌ Book not available or invalid ID.");
    }

    public static void main(String[] args) {
        Library myLibrary = new Library();
        myLibrary.addBook(new Book(101, "Java Programming", "Pushpraj"));
        myLibrary.addBook(new Book(102, "DSA Simplified", "Singh"));

        // Basic Console Interface
        Scanner sc = new Scanner(System.in);
        myLibrary.showAllBooks();
        
        System.out.print("\nEnter Book ID to issue: ");
        int id = sc.nextInt();
        myLibrary.issueBook(id);
        
        myLibrary.showAllBooks();
        sc.close();
    }
}