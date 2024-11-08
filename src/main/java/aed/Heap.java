package aed;
import java.util.ArrayList;

public class Heap<T> {

    ArrayList<Nodo> lista;
    Comparator comparador;

    private class Nodo {

        T objeto;
        ArrayList<int> indices;

        public Nodo (T objeto, ArrayList<Int> indice) {
            this.indices = new ArrayList<int>;
            this.indices.agregar(indice)
            this.objeto = objeto;
        }
        
    }


    public Heap(Comparator<T> comparador){
        this.comparador = comparador;
    }
    
    public void agregar(T objeto){
        Nodo objetoNodo = new Nodo(objeto, lista.size)
        lista.agregar(objetoNodo)
        heapify(objetoNodo, objetoNodo.indice)
    }


    public void eliminar(Integer indice){
        this.lista[indice] = this.lista[this.lista.length - 1];
        this.lista[this.lista.length - 1] = null;
        } 

    public void heapify(Integer indice){
    
    } 

    public Nodo sacarPrimero() {
        
    }
    
    public Nodo obtenerPrimero() {
        return this.lista[0];
    }

    public modificar(indice) {
        
    }
}
