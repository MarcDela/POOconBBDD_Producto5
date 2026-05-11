package InnerJoinConElCafe.modelo;

import java.time.LocalDateTime;
import InnerJoinConElCafe.excepciones.PedidoException;
import jakarta.persistence.*;

@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "numeroPedido")
    private int numeroPedido;
    @Column(name = "cantidad")
    private int cantidad;
    @Column(name = "fechaHora")
    private LocalDateTime fechaHora;
    @ManyToOne(fetch = FetchType.EAGER) // Muchos pedidos pueden pertenecer a UN cliente - FetchType.EAGER 
    @JoinColumn(name = "cliente_nif", referencedColumnName = "nif") // El nombre de la FK
    private Cliente cliente;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "articulo_codigo")
    private Articulo articulo;

    public Pedido() {}

    @Override
    public String toString() {
        return "Pedido{" +
                "numeroPedido=" + numeroPedido +
                ", cantidad=" + cantidad +
                ", fechaHora=" + fechaHora +
                ", cliente=" + cliente +
                ", articulo=" + articulo +
                '}';
    }

    public Pedido(int numeroPedido, int cantidad, LocalDateTime fechaHora, Articulo articulo, Cliente cliente) {
        this.numeroPedido = numeroPedido;
        this.cantidad = cantidad;
        this.fechaHora = fechaHora;
        this.cliente = cliente;
        this.articulo = articulo;
    }

    public double calcularPrecio() {
        double precioBase = articulo.getPrecioVenta() * cantidad;
        double envioBase = articulo.getGastosEnvio() * cantidad;
        double envioConDescuento = cliente.aplicarDescuento(envioBase);
    
        return precioBase + envioConDescuento;
    }

    public boolean puedeCancelarse(){

        LocalDateTime ahora = LocalDateTime.now();
        LocalDateTime momentoEnvio = fechaHora.plusMinutes(articulo.getTiempoPreparacion());
        return ahora.isBefore(momentoEnvio);
    }

    public void cancelar() throws PedidoException { 
        if(!puedeCancelarse()){
            // Lanzamos la excepcion específica
            throw new PedidoException("El pedido no puede cancelarse. Tiempo insuficiente");
        }
    }



    //getters y setters
    public int getNumeroPedido() {
        return numeroPedido;
    }

    public void setNumeroPedido(int numeroPedido) {
        this.numeroPedido = numeroPedido;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Articulo getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }
}