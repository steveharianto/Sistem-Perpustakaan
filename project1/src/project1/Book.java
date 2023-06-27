package project1;

import java.sql.*;

public class Book {
    private int id;
    private String nama;
    private int stok;
    Connection connection = null;

    public Book(int id, String nama, int stok) {
        this.id = id;
        this.nama = nama;
        this.stok = stok;
        try{
            // Establish a database connection
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_sip", "root", "");
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Functions
    public void update() {
        try {
            // Create the SQL query
            String query = "UPDATE book SET nama = ?, stok = ? WHERE id = ?";
            
            // Create the prepared statement
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, nama);
            statement.setInt(2, stok);
            statement.setInt(3, id);
            
            // Execute the update query
            statement.executeUpdate();
            
            System.out.println("Book updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete() {
        try {
            // Create the SQL query
            String query = "DELETE FROM book WHERE id = ?";
            
            // Create the prepared statement
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            
            // Execute the delete query
            statement.executeUpdate();
            
            System.out.println("Book deleted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Getter & Setter

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public int getStok() {
        return stok;
    }

    public void setStok(int stok) {
        this.stok = stok;
    }
}

