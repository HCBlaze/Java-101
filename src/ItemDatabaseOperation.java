import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemDatabaseOperation {
    private static final String url = "jdbc:mysql://localhost:3306/gyakorlas";
    private static final String username = "root";
    private static final String password = "";

    private static List<ItemFromMysql> itemList = new ArrayList<>();

    private static void closeResources(Statement statement, Connection connection){
        try {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void insertNewItem(ItemFromMysql item){
        String insertQuery = "INSERT INTO forgalom (tipus, tengelysuly, szin) VALUES (?, ?, ?)";

        try(Connection connection = DriverManager.getConnection(url,username,password);
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)){

            preparedStatement.setString(1, item.getTipus());
            preparedStatement.setInt(2, item.getSuly());
            preparedStatement.setString(3,item.getSzin());

            int rowAffected = preparedStatement.executeUpdate();
            System.out.println(rowAffected + " row(s) inserted successfully.");

            closeResources(preparedStatement,connection);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
    public static void updateItem(ItemFromMysql item){
        String updateQuery = "UPDATE forgalom SET tipus=?, tengelysuly=?, szin=? WHERE Ssz=? ";

        try(Connection connection = DriverManager.getConnection(url,username,password);
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)){

            preparedStatement.setString(1, item.getTipus());
            preparedStatement.setInt(2, item.getSuly());
            preparedStatement.setString(3, item.getSzin());
            preparedStatement.setInt(4,item.getId());

            int rowAffected = preparedStatement.executeUpdate();
            System.out.println(rowAffected + " row(s) updated successfully.");

            closeResources(preparedStatement,connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateItemColumn(int itemId, String columnName, Object columnValue){
        String updateQuery = "UPDATE forgalom SET " +columnName+ "=? where Ssz=?";

        try(Connection connection = DriverManager.getConnection(url,username,password);
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)){

            setColumnValue(preparedStatement,1,columnValue);
            preparedStatement.setInt(2, itemId);

            int rowAffected = preparedStatement.executeUpdate();
            System.out.println(rowAffected + " row(s) updated successfully.");

            closeResources(preparedStatement,connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void setColumnValue(PreparedStatement preparedStatement, int parameterIndex, Object columnValue) throws SQLException {
        if(columnValue instanceof String){
            preparedStatement.setString(parameterIndex, (String) columnValue );
        } else if (columnValue instanceof Integer) {
            preparedStatement.setInt(parameterIndex, (Integer) columnValue );
        } else {
            throw new IllegalArgumentException("Unsupported column value type.");
        }
    }

    public static void deleteItem(int itemId) {
        String deleteQuery = "Delete from forgalom where Ssz=?";

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {

            preparedStatement.setInt(1, itemId);

            int rowAffected = preparedStatement.executeUpdate();
            if (rowAffected > 0) {
                System.out.println(rowAffected + " row(s) deleted successfully!");
            } else {
                System.out.println("No row(s) deleted. Item with " + itemId + " not found.");
            }
            closeResources(preparedStatement,connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<ItemFromMysql> getItemList() {
        return itemList;
    }
    public static void getAllData(){
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.getConnection(url,username,password);
            Statement statement = conn.createStatement();

            ResultSet resultSet = statement.executeQuery("Select * from forgalom");

            while(resultSet.next()){
                ItemFromMysql newItem = ItemFromMysql.createItemFromSet(resultSet);
                itemList.add(newItem);
                System.out.println(newItem);
            }
            closeResources(statement,conn);
        }catch (Exception e){
            System.out.println(e);
        }
    }
    public static int getNewId(){
        String getLatestId = "SELECT Ssz from forgalom order by Ssz desc limit 1";
        int latestId = 0;

        try(Connection connection = DriverManager.getConnection(url,username,password);
        Statement statement = connection.createStatement()){

           ResultSet resultSet = statement.executeQuery(getLatestId);

            if(resultSet.isFirst()){
                latestId = resultSet.getInt("Ssz");
                return latestId + 1;
            }
            closeResources(statement,connection);
        } catch (SQLException e){
            e.printStackTrace();
        }
        return -1;
    }
}
