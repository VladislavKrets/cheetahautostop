package online.omnia.autostop;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.PersistenceException;
import java.util.Map;

/**
 * Created by lollipop on 26.09.2017.
 */
public class MySQLDaoImpl {
    private static Configuration configuration;
    private static SessionFactory sessionFactory;
    private static MySQLDaoImpl instance;

    static {
        configuration = new Configuration()
                .addAnnotatedClass(AccountEntity.class)
                .addAnnotatedClass(CheetahTokenEntity.class)
                .configure("/hibernate.cfg.xml");
        Map<String, String> properties = Utils.iniFileReader();
        configuration.setProperty("hibernate.connection.password", properties.get("password"));
        configuration.setProperty("hibernate.connection.username", properties.get("username"));
        configuration.setProperty("hibernate.connection.url", properties.get("url"));

        while (true) {
            try {
                sessionFactory = configuration.buildSessionFactory();
                break;
            } catch (PersistenceException e) {

                try {
                    System.out.println("Can't connect to db");
                    System.out.println("Waiting for 30 seconds");
                    Thread.sleep(30000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    public AccountEntity getAccountEntity(String username) {
        System.out.println(username);
        Session session = sessionFactory.openSession();
        AccountEntity accountEntity =
                session.createQuery("from AccountEntity where username=:username", AccountEntity.class)
                        .setParameter("username", username)
                        .getSingleResult();
        session.close();
        return accountEntity;
    }

    public CheetahTokenEntity getCheetahTokenEntity(int accountId) {
        Session session = sessionFactory.openSession();
        CheetahTokenEntity cheetahTokenEntity =
                session.createQuery("from CheetahTokenEntity where account_id=:accountId", CheetahTokenEntity.class)
                .setParameter("accountId", accountId)
                .getSingleResult();
        session.close();
        return cheetahTokenEntity;
    }
    public static MySQLDaoImpl getInstance() {
        if (instance == null) instance = new MySQLDaoImpl();
        return instance;
    }
}
