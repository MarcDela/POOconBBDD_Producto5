package InnerJoinConElCafe.modelo;

public class Resultado<T> {
    private T dato;
    private String mensaje;
    private boolean exito;

    // Constructor para operaciones exitosas
    public Resultado(T dato, String mensaje) {
        this.dato = dato;
        this.mensaje = mensaje;
        this.exito = true;
    }

    // Constructor para errores
    public Resultado(String mensajeError) {
        this.mensaje = mensajeError;
        this.exito = false;
        this.dato = null;
    }

    public T getDato() { return dato; }
    public String getMensaje() { return mensaje; }
    public boolean esExitoso() { return exito; }
}
