package InnerJoinConElCafe.modelo;
import jakarta.persistence.*;

@Entity
@Table(name = "clientes")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // Todo a una tabla
@DiscriminatorColumn(name = "tipo", discriminatorType = DiscriminatorType.STRING) // La columna que diferencia
public abstract class Cliente {

    @Id
    @Column(name = "email")
    protected String email;

    @Column(name = "nombre")
    protected String nombre;

    @Column(name = "domicilio")
    protected String domicilio;

    @Column(name = "nif")
    protected String nif;

    // Este campo es de solo lectura para Java, Hibernate lo llena mediante el Discriminador
    @Column(name = "tipo", insertable = false, updatable = false)
    protected String tipo;

    @Column(name = "cuotaAnual")
    protected double cuotaAnual;

    @Column(name = "descuentoEnvio")
    protected double descuentoEnvio;

    public Cliente() {}

    public Cliente(String nombre, String domicilio, String nif, String email) {
        this.nombre = nombre;
        this.domicilio = domicilio;
        this.nif = nif;
        this.email = email;
    }
        
    @Override
    public String toString() {
        return "Cliente{" +
                "nombre='" + nombre + '\'' +
                ", domicilio='" + domicilio + '\'' +
                ", nif='" + nif + '\'' +
                ", email='" + email + '\'' +
                "} ";
    }
  


    //getters y setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double aplicarDescuento(double precioBase) {
        return precioBase; // El cliente estándar devuelve el precio tal cual
    }

    public abstract double getCuotaAnual();
    public abstract double getDescuentoEnvio();

}
