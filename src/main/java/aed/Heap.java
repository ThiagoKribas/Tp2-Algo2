package aed;
import java.util.ArrayList;
import java.util.Comparator;

public class Heap<T> {

    ArrayList<Nodo> lista;
    Comparator<T> comparador;

    public class Nodo {
        T objeto;
        ArrayList<Integer> indices = null;// seria una lista donde la posicion 0 es la posicion en este heap y la otra posicion es en el otro heap

        public Nodo (T objeto, ArrayList<Integer> indices) {
            this.objeto = objeto;
            this.indices = indices;
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
    public void agregar(Nodo objeto){
       this.lista.add(objeto);
        heapifyUp(lista.size() - 1);
    }

    public void eliminar(Integer indice){
        lista.set(indice, lista.get(lista.size() - 1));
        lista.remove(lista.size() - 1);
        heapifyDown(indice);
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

    private Boolean EsHijoIzquierdo (Integer indice) {
        return indice % 2 != 0;
    }

    private Boolean EsMayorQuePadre (Integer indice, Integer padre){
        return comparador.compare(lista.get(indice).objeto, lista.get(padre).objeto) > 0;
    }

    public Nodo sacarPrimero() {
        Nodo res = lista.get(0);
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
            int indiceDelPadre = (indice - 1) % 2;

            if (comparador.compare(lista.get(indice).objeto, lista.get(indiceDelPadre).objeto) > 0) {

                // si el padre tiene menor prioridad, subo al hijo ✅
                swap(indice, indiceDelPadre);
                indice = indiceDelPadre;

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
        
        Nodo subir = lista.get(i);
        lista.set(i, lista.get(j));
        lista.set(j, subir);

        // Si tiene indices los actualiza (TODO: cambiar a diccionarios)

        if (lista.get(i).indices != null) {

            lista.get(i).indices.set(0, i);

        }

        if (lista.get(j).indices != null) {

            lista.get(j).indices.set(0, j);

        }

    }

}
