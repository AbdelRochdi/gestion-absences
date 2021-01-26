package ma.youcode.dao;

import ma.youcode.connexion.Connexion;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PromoDaoImpl implements PromoDao {
    @Override
    public ArrayList<String> getPromoValues() {
        ArrayList<String> promos = new ArrayList<>();
        Connection connection = null;
        try {
            DataSource dataSource = Connexion.getSingleDataSource();
            connection = dataSource.getConnection();
            String query = "SELECT titre FROM promos";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                promos.add(resultSet.getString("titre"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return promos;
    }
}
