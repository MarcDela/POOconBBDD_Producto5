package InnerJoinConElCafe.modelo;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            // Crea la SessionFactory a partir del archivo hibernate.cfg.xml
            // .configure() busca por defecto el archivo en la raíz de 'resources'
            return new Configuration().configure().buildSessionFactory();
            
        } catch (Throwable ex) {
            System.err.println("¡Error! No se pudo crear la SessionFactory inicial." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    // Método para obtener la factoría de sesiones desde los DAO
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    // Método para cerrar la conexión al salir de la aplicación
    public static void shutdown() {
        getSessionFactory().close();
    }
}