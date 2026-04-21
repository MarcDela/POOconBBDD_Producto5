package InnerJoinConElCafe.modelo;
import jakarta.persistence.*;

@Entity
@DiscriminatorValue("Estandar")
public class ClienteEstandar extends Cliente {
    
    /* Constructor para Hibernate */
    public ClienteEstandar() {
        super();
        this.cuotaAnual = 0.0;
        this.descuentoEnvio = 0.0;
    }

    public ClienteEstandar(String nombre, String domicilio, String nif, String email){
        super(nombre, domicilio, nif, email);
        this.cuotaAnual = 0.0;
        this.descuentoEnvio = 0.0;
    }


    @Override
    public String toString() {
        return super.toString() + 
        "Tipo Cliente: Estandar ";
    }

    @Override
    public double getCuotaAnual() {
        return 0.0;
    }

    @Override
    public double getDescuentoEnvio() {
        return 0.0;
    }
}
