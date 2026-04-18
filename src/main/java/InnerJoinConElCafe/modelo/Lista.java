package InnerJoinConElCafe.modelo;

import java.util.ArrayList;

public class Lista <T> {
    
    protected ArrayList<T> lista;

    public Lista() {
        this.lista = new ArrayList<>();
    }

    public void añadir(T elemento) {
        this.lista.add(elemento);
    }

    public void borrar(T elemento) {
        this.lista.remove(elemento);
    }

    public ArrayList<T> getArrayList() {
        return new ArrayList<>(lista); // Devolvemos una copia para proteger la integridad
    }

    public int getSize() {
        return lista.size();
    }
}
