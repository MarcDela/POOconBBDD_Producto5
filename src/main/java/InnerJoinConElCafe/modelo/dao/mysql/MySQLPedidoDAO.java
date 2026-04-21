package InnerJoinConElCafe.modelo.dao.mysql;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

import InnerJoinConElCafe.modelo.Articulo;
import InnerJoinConElCafe.modelo.Cliente;
import InnerJoinConElCafe.modelo.HibernateUtil;
import InnerJoinConElCafe.modelo.Pedido;
import InnerJoinConElCafe.modelo.dao.PedidoDAO;

public class MySQLPedidoDAO implements PedidoDAO {

    @Override
    public void insertar(Pedido p) throws Exception {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
        
            // RE-HIDRATACIÓN:
            // En lugar de usar p.getCliente(), le pedimos a la sesión que busque al cliente por su PK (email)
            Cliente clienteFresco = session.get(Cliente.class, p.getCliente().getEmail());
        
            // Hacemos lo mismo con el artículo usando su código
            Articulo articuloFresco = session.get(Articulo.class, p.getArticulo().getCodigo());
        
            if (clienteFresco == null || articuloFresco == null) {
                throw new Exception("No se encontró el cliente o el artículo en la base de datos.");
            }

            // Actualizamos el pedido con los objetos que ESTA sesión sí reconoce
            p.setCliente(clienteFresco);
            p.setArticulo(articuloFresco);
        
            // Ahora session.persist no fallará porque cliente y articulo están en estado 'Persistent' (activos)
            session.persist(p);
        
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }

    @Override
    public List<Pedido> obtenerTodos() throws Exception {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Hibernate ve las anotaciones @ManyToOne y recupera automáticamente los objetos Cliente y Articulo asociados.
            return session.createQuery("from Pedido", Pedido.class).list();
        } catch (Exception e) {
            System.err.println("Error al obtener pedidos: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override public void eliminar(Pedido t) throws Exception {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            
            // Buscamos el pedido para asegurarnos de que está en la sesión y borrarlo
            Pedido pedidoAEliminar = session.get(Pedido.class, t.getNumeroPedido());
            if (pedidoAEliminar != null) {
                session.remove(pedidoAEliminar);
            }
            
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }

    @Override public void modificar(Pedido t) throws Exception {}
    @Override public Pedido obtener(Integer id) throws Exception { return null; }
}