package project1;

import java.sql.*;

public class Borrow {
    private int id;
    private int id_book;
    private String username;
    private String status;
    Connection connection = null;

    public Borrow(int id, int id_book, String username, String status) {
        this.id = id;
        this.id_book = id_book;
        this.username = username;
        this.status = status;
        try{
            // Establish a database connection
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_sip", "root", "");
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Functions
    public String insert() {
        try {
            // Prepare the SQL statements
            String borrowQuery = "INSERT INTO Borrow (id_book, username, status) VALUES (?, ?, ?)";
            String updateStockQuery = "UPDATE Book SET stok = stok - 1 WHERE id = ?";

            // Start a transaction
            connection.setAutoCommit(false);

            // Insert a new record into Borrow table
            PreparedStatement borrowStatement = connection.prepareStatement(borrowQuery);
            borrowStatement.setInt(1, id_book);
            borrowStatement.setString(2, username);
            borrowStatement.setString(3, status);
            int rowsInserted = borrowStatement.executeUpdate();

            // Update the stock in Book table
            PreparedStatement updateStockStatement = connection.prepareStatement(updateStockQuery);
            updateStockStatement.setInt(1, id_book);
            int rowsUpdated = updateStockStatement.executeUpdate();

            if (rowsInserted > 0 && rowsUpdated > 0) {
                connection.commit();
                System.out.println("Borrow record inserted successfully.");
                return "Berhasil Mencatat Pinjaman";
            } else {
                connection.rollback();
                System.out.println("Failed to insert borrow record.");
                return "Gagal Mencatat Pinjaman";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return "Gagal Meminjam";
    }
    
    public String getBookName() {
        String bookName = "";
        try {
            // Prepare the SQL statement
            String query = "SELECT nama FROM Book WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id_book);

            // Execute the query
            ResultSet resultSet = statement.executeQuery();

            // Retrieve the book name
            if (resultSet.next()) {
                bookName = resultSet.getString("nama");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookName;
    }
    
    public void updateData() {
        try {
            // Prepare the SQL statement
            String query = "SELECT id_book, username, status FROM Borrow WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);

            // Execute the query
            ResultSet resultSet = statement.executeQuery();

            // Update the class variables based on the retrieved data
            if (resultSet.next()) {
                id_book = resultSet.getInt("id_book");
                username = resultSet.getString("username");
                status = resultSet.getString("status");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public String returnBook() {
        updateData();
        try {
            // Prepare the SQL statements
            String updateBorrowQuery = "UPDATE Borrow SET status = 'Returned' WHERE id = ?";
            String updateStockQuery = "UPDATE Book SET stok = stok + 1 WHERE id = ?";

            // Start a transaction
            connection.setAutoCommit(false);

            // Update the status in the Borrow table
            PreparedStatement updateBorrowStatement = connection.prepareStatement(updateBorrowQuery);
            updateBorrowStatement.setInt(1, id);
            int rowsUpdatedBorrow = updateBorrowStatement.executeUpdate();

            // Update the stock in the Book table
            PreparedStatement updateStockStatement = connection.prepareStatement(updateStockQuery);
            updateStockStatement.setInt(1, id_book);
            int rowsUpdatedStock = updateStockStatement.executeUpdate();

            if (rowsUpdatedBorrow > 0 && rowsUpdatedStock > 0) {
                connection.commit();
                System.out.println("Book returned successfully.");
                return "Buku Dikembalikan";
            } else {
                connection.rollback();
                System.out.println("Failed to return the book.");
                return "Gagal Mengembalikan Buku";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return "Gagal Mengembalikan Buku";
    }


    // Getter & Setter
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdBook() {
        return id_book;
    }

    public void setIdBook(int id_book) {
        this.id_book = id_book;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

