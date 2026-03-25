// Ya phir saare SQL tools ke liye:
import java.sql.*; // 👈 Ye line miss ho rahi thi


public class DatabaseHandler {
    private static final String URL = "jdbc:mysql://localhost:3306/library_db";
    private static final String USER = "root";
    private static final String PASS = System.getenv("DB_PASSWORD");

    public static void connect() {
        try {
            if (PASS == null) {
                System.out.println("❌ Error: Password null hai! System variable nahi mila.");
                return;
            }
            // Driver load karna
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            Connection con = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("✅ Connected Successfully!");
            con.close();
        } catch (Exception e) {
            System.out.println("❌ Connection Failed: " + e.getMessage());
        }
    }
    public static void addBook(int id, String title, String author) {
    String query = "INSERT INTO books (id, title, author) VALUES (?, ?, ?)";
    
    try (Connection con = DriverManager.getConnection(URL, USER, PASS);
         PreparedStatement pstmt = con.prepareStatement(query)) {
        
        pstmt.setInt(1, id);
        pstmt.setString(2, title);
        pstmt.setString(3, author);
        
        int rows = pstmt.executeUpdate();
        if (rows > 0) {
            System.out.println("📖 Book added to Database: " + title);
        }
        
    } catch (SQLException e) {
        System.out.println("❌ Error adding book: " + e.getMessage());
    }
    }
    public static void showBooks() {
    String query = "SELECT * FROM books";
    
    try (Connection con = DriverManager.getConnection(URL, USER, PASS);
         Statement stmt = con.createStatement();
         ResultSet rs = stmt.executeQuery(query)) {
        
        System.out.println("\n📚 --- LIBRARY INVENTORY --- 📚");
        System.out.println("-------------------------------------------------");
        System.out.printf("%-5s | %-20s | %-15s | %-10s%n", "ID", "Title", "Author", "Status");
        System.out.println("-------------------------------------------------");

        while (rs.next()) {
            int id = rs.getInt("id");
            String title = rs.getString("title");
            String author = rs.getString("author");
            boolean isIssued = rs.getBoolean("is_issued");
            String status = isIssued ? "Borrowed" : "Available";

            System.out.printf("%-5d | %-20s | %-15s | %-10s%n", id, title, author, status);
        }
        System.out.println("-------------------------------------------------");

    } catch (SQLException e) {
        System.out.println("❌ Error fetching books: " + e.getMessage());
    }
}
    //// issueBook
        public static void issueBook(int id) {
    String query = "UPDATE books SET is_issued = true WHERE id = ? AND is_issued = false";
    
    try (Connection con = DriverManager.getConnection(URL, USER, PASS);
         PreparedStatement pstmt = con.prepareStatement(query)) {
        
        pstmt.setInt(1, id);
        int rows = pstmt.executeUpdate();
        
        if (rows > 0) {
            System.out.println("✅ Book ID " + id + " has been issued!");
        } else {
            System.out.println("⚠️ Book already issued ya ID galat hai.");
        }
        
    } catch (SQLException e) {
        System.out.println("❌ Error issuing book: " + e.getMessage());
    }
}
// Delete book
       public static void deleteBook(int id) {
    String query = "DELETE FROM books WHERE id = ?";
    
    try (Connection con = DriverManager.getConnection(URL, USER, PASS);
         PreparedStatement pstmt = con.prepareStatement(query)) {
        
        pstmt.setInt(1, id);
        int rows = pstmt.executeUpdate();
        
        if (rows > 0) {
            System.out.println("🗑️ Book ID " + id + " deleted from database.");
        } else {
            System.out.println("⚠️ Book ID " + id + " nahi mili.");
        }
        
    } catch (SQLException e) {
        System.out.println("❌ Error deleting book: " + e.getMessage());
    }
}
public static void main(String[] args) {
    // 1. Connection check karein
    connect();

    // 2. Pehle list dekho
    System.out.println("Pehle ki list:");
    showBooks();

    // 3. Ek book issue karke dekho (ID 101)
    issueBook(101);

    // 4. Ek purani book delete karke dekho (ID 102)
    deleteBook(102);

    // 5. Final list dekho
    System.out.println("\nUpdate ke baad ki list:");
    showBooks();
}
}