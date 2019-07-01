import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class Project {

    public static Car createCar(String brand, String type, double price, int owner_id) throws SQLException {
        Connection connection = null;
        Car car = new Car(brand, type, price, owner_id);

        connection = MySqlDatabase.getDatabaseConnection();
        Statement sqlStatement = connection.createStatement();

        String sql = String.format("INSERT INTO cars (brand, type, price, owner_id) VALUES ('%s' , '%s', %s, %s);",
                car.getBrand(),
                car.getType(),
                car.getPrice(),
                car.getOwner_id());
        sqlStatement.executeUpdate(sql);
        connection.close();

        return car;
    }

    public static Car createCarUsingStoredProcedure(String brand, String type, double price, int owner_id)
            throws SQLException {

        Connection connection = null;
        Car car = new Car(brand, type, price, owner_id);


        connection = MySqlDatabase.getDatabaseConnection();
        String sql = "CALL create_car(?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, brand);
        preparedStatement.setString(2, type);
        preparedStatement.setDouble(3, price);
        preparedStatement.setInt(4, owner_id);

        preparedStatement.execute();
        connection.close();

        return car;
    }

    public static CarOwner createOwner(String firstName, String lastName) throws SQLException {
        Connection connection = null;


        connection = MySqlDatabase.getDatabaseConnection();
        Statement sqlStatement = connection.createStatement();

        String sql = String.format("INSERT INTO car_owners (firstName, lastName) VALUES ('%s' , '%s');",
                firstName, lastName);

        sqlStatement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);

        ResultSet resultSet = sqlStatement.getGeneratedKeys();
        resultSet.next();

        int owner_id = resultSet.getInt(1);
        connection.close();

        return new CarOwner(firstName, lastName, owner_id);


    }

    public static void updateOwner(String firstName, String lastName, int owner_id) throws SQLException {
        Connection connection = null;

        connection = MySqlDatabase.getDatabaseConnection();
        Statement sqlStatement = connection.createStatement();

        String sql = String.format("UPDATE car_owners SET firstName = '%s', lastName = '%s' WHERE id = %s;",
                firstName, lastName, owner_id);

        sqlStatement.executeUpdate(sql);

        connection.close();
    }

    public static void deleteOwner(int owner_id) throws SQLException {
        Connection connection = null;

        connection = MySqlDatabase.getDatabaseConnection();
        Statement sqlStatement = connection.createStatement();

        String sql = String.format("DELETE FROM car_owners WHERE id = %s;", owner_id);
        sqlStatement.executeUpdate(sql);
        connection.close();
    }

    public static List<Car> getAllCars() throws SQLException {
        Connection connection = null;


        connection = MySqlDatabase.getDatabaseConnection();
        Statement sqlStatement = connection.createStatement();

        String sql = "SELECT * FROM cars;";
        ResultSet resultSet = sqlStatement.executeQuery(sql);

        List<Car> cars = new ArrayList<Car>();

        while (resultSet.next()) {
            String brand = resultSet.getString(1);
            String type = resultSet.getString(2);
            double price = resultSet.getDouble(3);
            int owner = resultSet.getInt(4);

            Car car = new Car(brand, type, price, owner);
            cars.add(car);
        }
        resultSet.close();
        connection.close();
        return cars;


    }

    public static void attemptToListCars() {
        try {
            List<Car> cars = getAllCars();
            for (Car car : cars) {
                System.out.println(car.toString());
            }
        } catch (SQLException sqlException) {
            System.out.println("Failed to get cars");
            System.out.println(sqlException.getMessage());
        }
    }

    public static void attemptToCreateNewCar(String brand, String type, double price, int owner_id) {
        try {
            Car car = createCar(brand, type, price, owner_id);
            System.out.println(car.toString());
        } catch (SQLException sqlException) {
            System.out.println("Failed to create car");
            System.out.println(sqlException.getMessage());
        }
    }

    public static void attemptToCreateNewCarUsingSP(String brand, String type, double price, int owner_id) {
        try {
            createCarUsingStoredProcedure(brand, type, price, owner_id);
        } catch (SQLException sqlException) {
            System.out.println("Failed to create car");
            System.out.println(sqlException.getMessage());
        }
    }

    public static void attemptToCreateNewOwner(String firstName, String lastName) {
        try {
            CarOwner carOwner = createOwner(firstName, lastName);
            System.out.println(carOwner.toString());
        } catch (SQLException sqlException) {
            System.out.println("Failed to create owner");
            System.out.println(sqlException.getMessage());
        }

    }

    public static void attemptToUpdateOwner(String firstName, String lastName, int owner_id) {
        try {
            updateOwner(firstName, lastName, owner_id);
        } catch (SQLException sqlException) {
            System.out.println("Failed to update owner");
            System.out.println(sqlException.getMessage());
        }

    }

    public static void attemptToDeleteOwner(int owner_id) {
        try {
            deleteOwner(owner_id);
        } catch (SQLException sqlException) {
            System.out.println("Failed to delete owner");
            System.out.println(sqlException.getMessage());
        }
    }

    public static void main(String[] args){

        if (args[0].equals("ListCars")) {
            attemptToListCars();
        } else if (args[0].equals("CreateCar")) {
            String brand = args[1];
            String type = args[2];
            double price = Double.parseDouble(args[3]);
            int owner_id = Integer.parseInt(args[4]);
            attemptToCreateNewCar(brand, type, price, owner_id);
        } else if (args[0].equals("CreateCarSP")) {
            String brand = args[1];
            String type = args[2];
            double price = Double.parseDouble(args[3]);
            int owner_id = Integer.parseInt(args[4]);
            attemptToCreateNewCarUsingSP(brand, type, price, owner_id);
        } else if (args[0].equals("CreateOwner")) {
            String firstName = args[1];
            String lastName = args[2];
            attemptToCreateNewOwner(firstName, lastName);
        } else if (args[0].equals("UpdateOwner")) {
            String firstName = args[1];
            String lastName = args[2];
            int owner_id = Integer.parseInt(args[3]);
            attemptToUpdateOwner(firstName, lastName, owner_id);
        } else if (args[0].equals("DeleteOwner")) {
            int owner_id = Integer.parseInt(args[1]);
            attemptToDeleteOwner(owner_id);
        }
    }
}
