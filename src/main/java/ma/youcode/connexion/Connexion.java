package ma.youcode.connexion;

import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;

public class Connexion {

    private static BasicDataSource singleDataSource;

    public static DataSource getSingleDataSource(){
        String url = "jdbc:mysql://localhost:3306/gestion_absences";
        String username = "root";
        String password = "";

        if (singleDataSource == null){
            singleDataSource = new BasicDataSource();
            singleDataSource.setInitialSize(5);
            singleDataSource.setUrl(url);
            singleDataSource.setUsername(username);
            singleDataSource.setPassword(password);

        }


        return singleDataSource;
    }






}
