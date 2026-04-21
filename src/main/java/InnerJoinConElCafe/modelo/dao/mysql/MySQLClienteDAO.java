package InnerJoinConElCafe.modelo.dao.mysql;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import InnerJoinConElCafe.modelo.HibernateUtil;

import InnerJoinConElCafe.modelo.Cliente;
import InnerJoinConElCafe.modelo.dao.ClienteDAO;


public class MySQLClienteDAO implements ClienteDAO {

    @Override
    public void insertar(Cliente c) throws Exception {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            
            /* Usamos persist porque ya hemos mapeado la jerarquía. Hibernate detectará si es Premium/Estandar y rellenará la columna 'tipo' solo. */
            session.persist(c);
            
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }

    @Override
    public List<Cliente> obtenerTodos() throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Hibernate mira la columna 'tipo' y crea automáticamente instancias de ClientePremium o ClienteEstandar según corresponda.
            return session.createQuery("from Cliente", Cliente.class).list();
        } catch (Exception e) {
            System.err.println("Error al obtener clientes: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override public void modificar(Cliente t) throws Exception {}
    @Override public void eliminar(Cliente t) throws Exception {}
    @Override public Cliente obtener(String id) throws Exception { return null; }
}