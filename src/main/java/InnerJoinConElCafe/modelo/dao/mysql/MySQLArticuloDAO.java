package InnerJoinConElCafe.modelo.dao.mysql;

import org.hibernate.Session;
import org.hibernate.Transaction;
import InnerJoinConElCafe.modelo.HibernateUtil;
import java.util.List;
import InnerJoinConElCafe.modelo.Articulo;
import InnerJoinConElCafe.modelo.dao.ArticuloDAO;


public class MySQLArticuloDAO implements ArticuloDAO {


    @Override
    public void insertar(Articulo a) throws Exception {
        Transaction transaction = null;

        // Abrimos la sesión usando nuestro nuevo Util
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            
            // Iniciamos la transacción (equivale al setAutoCommit(false))
            transaction = session.beginTransaction();

            // PROCEDIMIENTO ALMACENADO
            // .createNativeQuery es para usar SQL "puro" o procedimientos almacenados
            session.createNativeQuery("CALL insertarArticulo(:desc, :precio, :gastos, :tiempo)", Articulo.class)
               .setParameter("desc", a.getDescripcion())
               .setParameter("precio", a.getPrecioVenta())
               .setParameter("gastos", a.getGastosEnvio())
               .setParameter("tiempo", a.getTiempoPreparacion())
               .executeUpdate();

        // TRANSACCIONES
        // Si todo sale correcto, hacemos el commit
        transaction.commit();

        } catch (Exception e) {
            // Si ocurre algun tipo de error, hacemos rollback para evitar problemas
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }

    @Override
    public List<Articulo> obtenerTodos() throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
        
            // HQL: "from Articulo" le dice a Hibernate: 
            // "Selecciona todos los registros de la tabla asociada a la CLASE Articulo"
            return session.createQuery("from Articulo", Articulo.class).list();
        
        } catch (Exception e) {
            System.err.println("Error al obtener todos los artículos: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public void eliminar(Articulo a) throws Exception {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Al igual que al insertar, borrar requiere una transacción
            transaction = session.beginTransaction();

            // Hibernate usa el campo marcado con @Id (codigo) para saber qué borrar
            // .remove() es el estándar de JPA para eliminar un objeto
            session.remove(a);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.err.println("Error al eliminar el artículo: " + e.getMessage());
            throw e;
        }
    }

    @Override public void modificar(Articulo t) throws Exception { /* Próxima fase */ }
    @Override public Articulo obtener(Integer id) throws Exception { return null; }
}