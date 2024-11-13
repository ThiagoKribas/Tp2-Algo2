package aed;
import java.util.ArrayList;
import java.util.Comparator;

public class Heap<T> {

    ArrayList<Nodo> lista;
    Comparator<T> comparador;
    int index;

    private class Nodo {
        T objeto;
        ArrayList<Integer> indices;

        public Nodo(T objeto){
            this.objeto = objeto;
            this.indices = new ArrayList<>();
        }
    }

    //Constructor de Heap
    public Heap(Comparator<T> comparador) {
        this.comparador = comparador;
        this.lista = new ArrayList<Nodo>();
        crearHeap();
    }

    // Constructor de Heap con lista
    public Heap(Comparator<T> comparador, ArrayList<Nodo> array) {
        this.comparador = comparador;
        this.lista = new ArrayList<Nodo>(array); // Copia de la lista
        crearHeap();
    }



    private void crearHeap() {
        for (int i = lista.size() / 2 - 1; i >= 0; i--) {
            heapifyDown(i);
        }
    }
    
    //agregar se le aplica a Heap<T>
    public void agregar(T objeto){
        Nodo nodoObjeto = new Nodo(objeto);
        this.lista.add(nodoObjeto);
        
        // Inicializar los índices para todos los heaps
        nodoObjeto.indices = new ArrayList<>();
        for (int i = 0; i < lista.size(); i++) {
            nodoObjeto.indices.add(lista.size() - 1);
        }
        
        heapifyUp(lista.size() - 1);
    }

    public void eliminar(Integer indice){
        lista.set(indice, lista.get(lista.size() - 1));
        lista.remove(lista.size() - 1);
        heapifyDown(indice);
    } 

    public int size(){
        return lista.size();
    }

    public ArrayList<Integer> obtenerIndices(Integer indice){
        Nodo nodo = lista.get(indice);
        return nodo.indices;
    }
    
    // private void heapify(ArrayList <Nodo> heap){ //complejidad = O(n)

    //     int posicion = lista.size() - 1; 
        
    //     while (posicion > 0){

    //         if (EsHijoDerecho(posicion)){ 

    //             Integer padre = (posicion-2)/2;

    //             if (EsMayorQuePadre(posicion,padre)){
    //                 lista.set(padre, lista.get(posicion));
    //                 lista.set(posicion , lista.get(padre));
    //             }
                
    //         } else if (EsHijoIzquierdo(posicion)) {

    //             Integer padre = (posicion-1)/2;
                
    //             if (EsMayorQuePadre(posicion,padre)){
    //                 lista.set(padre , lista.get(posicion));
    //                 lista.set(posicion , lista.get(padre));
    //             }
                
    //         }
            
    //         posicion = posicion - 1;
    //     }
    // }

    private Integer hijoDerecho (Integer indice) {
        return 2 * indice + 2;
    }

    private Integer hijoIzquierdo (Integer indice) {
        return 2 * indice + 1;
    }

/*  private Boolean EsHijoIzquierdo (Integer indice) {
        return indice % 2 != 0;
    }

    private Boolean EsMayorQuePadre (Integer indice, Integer padre){
        return comparador.compare(lista.get(indice).objeto, lista.get(padre).objeto) > 0;
    }
*/
    public T sacarPrimero() {
        T res = lista.get(0).objeto;
        this.eliminar(0);
        return res;
    }
    
    public T obtenerPrimero() {
        return (lista.get(0).objeto);
    }

    public void modificar(int indice, T nuevoValor) {
        Nodo nodo = lista.get(indice);
        T valorAntiguo = nodo.objeto;
        nodo.objeto = nuevoValor;

        // Comparar el nuevo valor con el antiguo para decidir si usar heapifyUp o heapifyDown
        if (comparador.compare(nuevoValor, valorAntiguo) > 0) {
            heapifyUp(indice);
        } else {
            heapifyDown(indice);
        }
    }

    // Método para mantener la propiedad de heap al insertar
    private void heapifyUp(int indice) {
        while (indice > 0) {
            int padre = (indice - 1) / 2;
            if (comparador.compare(lista.get(indice).objeto, lista.get(padre).objeto) > 0) {
                swap(indice, padre);
                indice = padre;
            } else {
                break;
            }
        }
    }

    // Método para mantener la propiedad de heap al eliminar o modificar
    private void heapifyDown(int indice) {
        int mayor = indice;
        int hijoIzq = hijoIzquierdo(indice);
        int hijoDer = hijoDerecho(indice);

        if (hijoIzq < lista.size() && comparador.compare(lista.get(hijoIzq).objeto, lista.get(mayor).objeto) > 0) {
            mayor = hijoIzq;
        }

        if (hijoDer < lista.size() && comparador.compare(lista.get(hijoDer).objeto, lista.get(mayor).objeto) > 0) {
            mayor = hijoDer;
        }

        if (mayor != indice) {
            swap(indice, mayor);
            heapifyDown(mayor);
        }
    }

    // Método para intercambiar nodos
    private void swap(int i, int j) {
        Nodo temp = lista.get(i);
        lista.set(i, lista.get(j));
        lista.set(j, temp);
        
        // Actualizar índices
        if (lista.get(i).indices != null && !lista.get(i).indices.isEmpty()) {
            lista.get(i).indices.set(index, i);
        }
        if (lista.get(j).indices != null && !lista.get(j).indices.isEmpty()) {
            lista.get(j).indices.set(index, j);
        }
    }

    public void eliminarElemento(T elemento) {
        for (int i = 0; i < size(); i++) {
            if (lista.get(i).objeto.equals(elemento)) {
                eliminar(i);
                break;
            }
        }
    }

}
