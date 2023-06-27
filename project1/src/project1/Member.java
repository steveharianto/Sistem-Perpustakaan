package project1;

import java.sql.*;

public class Member {
    private String username;
    private String password;
    Connection connection = null;

    public Member(String username, String password) {
        this.username = username;
        this.password = password;
        try{
            // Establish a database connection
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_sip", "root", "");
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Functions
    public boolean login() {
        try {
            // Prepare a statement to check if the username exists in the database
            String query = "SELECT COUNT(*) FROM Member WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);

            // Execute the query
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);

            if (count == 0) {
                System.out.println("Username does not exist");
                return false;
            }

            // Prepare a statement to check if the password is correct
            query = "SELECT COUNT(*) FROM Member WHERE username = ? AND password = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);

            // Execute the query
            resultSet = statement.executeQuery();
            resultSet.next();
            count = resultSet.getInt(1);

            if (count == 0) {
                System.out.println("Password is incorrect");
                return false;
            }

            // Username and password are correct
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            // Close the connection
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public String register() {
        try {
            // Prepare a statement to check if the username already exists
            String query = "SELECT COUNT(*) FROM Member WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);

            // Execute the query
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);

            if (count > 0) {
                System.out.println("Username is taken");
                return "Username Telah Diambil";
            } else {
                // Prepare a statement to insert the new member into the database
                query = "INSERT INTO Member (username, password) VALUES (?, ?)";
                statement = connection.prepareStatement(query);
                statement.setString(1, username);
                statement.setString(2, password);

                // Execute the query
                int rowsInserted = statement.executeUpdate();

                if (rowsInserted > 0) {
                    System.out.println("Registration successful");
                }
                return "Daftar Sukses!";
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the connection
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return "Register Failed";
    }
    
    public void delete() {
        try {
            // Prepare a statement to delete the member from the database
            String query = "DELETE FROM Member WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);

            // Execute the query
            int rowsDeleted = statement.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Member deleted successfully");
            } else {
                System.out.println("Member not found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the connection
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public void update(String newPassword) {
        try {
            // Prepare a statement to update the password of the member in the database
            String query = "UPDATE Member SET password = ? WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, newPassword);
            statement.setString(2, username);

            // Execute the query
            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Password updated successfully");
            } else {
                System.out.println("Member not found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close the connection
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }



    
    // Getter & Setter
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
