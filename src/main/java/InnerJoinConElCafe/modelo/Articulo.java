package InnerJoinConElCafe.modelo;

import jakarta.persistence.*; // Importa todas las anotaciones necesarias

@Entity
@Table(name = "articulos") //Le indicamos a que tabla pertenece en MySQL
public class Articulo {

    @Id // Define la clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Indica que es autoincremental
    @Column(name = "codigo") // Nombre de la columna en la tabla
    private int codigo;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "precioVenta")
    private double precioVenta;

    @Column(name = "gastosEnvio")
    private double gastosEnvio;

    @Column(name = "tiempoPreparacion")
    private int tiempoPreparacion;

    // --- IMPORTANTE: CONSTRUCTOR VACÍO ---
    // Hibernate lo usa para crear el objeto antes de rellenarlo con datos de la BD
    public Articulo() {}

    // Mantenemos el constructor 
    public Articulo(String descripcion, double precioVenta, double gastosEnvio, int tiempoPreparacion) {
        this.descripcion = descripcion;
        this.precioVenta = precioVenta;
        this.gastosEnvio = gastosEnvio;
        this.tiempoPreparacion = tiempoPreparacion;
    }

    @Override
    public String toString() {
        return "Articulo{" +
                "codigo='" + codigo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", precioVenta=" + String.format("%.2f", precioVenta) +
                ", gastosEnvio=" + String.format("%.2f", gastosEnvio) +
                ", tiempoPreparacion=" + tiempoPreparacion +
                '}';
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public double getGastosEnvio() {
        return gastosEnvio;
    }

    public void setGastosEnvio(double gastosEnvio) {
        this.gastosEnvio = gastosEnvio;
    }

    public int getTiempoPreparacion() {
        return tiempoPreparacion;
    }

    public void setTiempoPreparacion(int tiempoPreparacion) {
        this.tiempoPreparacion = tiempoPreparacion;
    }
}
