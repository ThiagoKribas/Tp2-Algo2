package aed;
import java.util.ArrayList;
import java.util.Comparator;

public class Heap<T> {

    ArrayList<Nodo<T>> lista;
    Comparator<T> comparador;
    int index;

    // Constructor de Heap
    public Heap(Comparator<T> comparador) {
        this.comparador = comparador;
        this.lista = new ArrayList<Nodo<T>>();
        this.index = 0;
    }

    // Constructor de Heap con lista
    public Heap(Comparator<T> comparador, ArrayList<Nodo<T>> input){
        this.comparador = comparador;
        this.index = 0;
        ArrayToHeap(input);
    }

    // Transforma una lista en Heap usando el comparador previamente especificado
    public void ArrayToHeap(ArrayList<Nodo<T>> input) {
        this.lista = new ArrayList<Nodo<T>>(input);
        for (int i = 0; i < this.lista.size(); i++){
            lista.get(i).indices.add(i);
        }

        for(int i = ((this.lista.size() / 2) - 1); i >= 0 ; i--){
            siftDown(i);
        }
    }

    // Método para agregar un elemento
    // Complejidad: O(log n)
    public void agregar(T objeto){
        Nodo<T> nodoObjeto = new Nodo<T>(objeto);
        this.lista.add(nodoObjeto);
        
        // Inicializar solo un índice para el heap actual
        nodoObjeto.indices = new ArrayList<>();
        nodoObjeto.indices.add(lista.size() - 1);  // Solo agregamos un índice
        this.siftUp(lista.size() - 1);
    }

    // Método para agregar un nodo
    // Complejidad: O(log n)
    public void agregar(Nodo<T> nodoObjeto){
        this.lista.add(nodoObjeto);

        // Agregamos el indice al nodo actual
        nodoObjeto.indices.add(lista.size() - 1);  // Solo agregamos un índice
        
        this.siftUp(lista.size() - 1);
    }

    // Método para eliminar un elemento por índice
    // Complejidad: O(log n)
    public void eliminar(Integer indice){
        if(indice >= lista.size() || indice < 0) return; 
        Nodo<T> ultimoNodo = lista.get(lista.size() - 1);
        lista.set(indice, ultimoNodo);
        lista.remove(lista.size() - 1);
        ultimoNodo.indices.set(index, indice);
        siftDown(indice);
    } 

    // Método para eliminar un elemento por valor
    // Complejidad: O(n)
    public void eliminarElemento(T elemento) {
        for(int i = 0; i < lista.size(); i++) {
            if(lista.get(i).objeto.equals(elemento)) {
                eliminar(i);
                break; // Suponiendo elementos únicos
            }
        }
    }

    // Método para obtener un elemento por índice
    // Complejidad: O(1)
    public T get(int index){
        if(index >= 0 && index < lista.size()){
            return lista.get(index).objeto;
        }
        return null;
    }

    public int size(){
        return lista.size();
    }

    public ArrayList<Integer> obtenerIndices(Integer indice){
        Nodo<T> nodo = lista.get(indice);
        return nodo.indices;
    }

    // Sift Up
    private void siftUp(int indice) {
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

    // Sift Down
    private void siftDown(int indice) {
        int size = lista.size();
        while (true) {
            int izquierdo = 2 * indice + 1;
            int derecho = 2 * indice + 2;
            int mayor = indice;

            if (izquierdo < size && comparador.compare(lista.get(izquierdo).objeto, lista.get(mayor).objeto) > 0) {
                mayor = izquierdo;
            }

            if (derecho < size && comparador.compare(lista.get(derecho).objeto, lista.get(mayor).objeto) > 0) {
                mayor = derecho;
            }

            if (mayor != indice) {
                swap(indice, mayor);
                indice = mayor;
            } else {
                break;
            }
        }
    }

    // Método para intercambiar elementos y actualizar índices
    private void swap(int i, int j) {
        Nodo<T> temp = lista.get(i);
        lista.set(i, lista.get(j));
        lista.set(j, temp);
        
        // Actualizar índices solo para el heap actual
        if (lista.get(i).indices != null && !lista.get(i).indices.isEmpty()) {
            lista.get(i).indices.set(index, i);  // Actualizamos el único índice
        }
        if (lista.get(j).indices != null && !lista.get(j).indices.isEmpty()) {
            lista.get(j).indices.set(index, j);  // Actualizamos el único índice
        }
    }

    // Complejidad: O(log n)
    public T sacarPrimero() {
        if(lista.isEmpty()) return null;
        T res = lista.get(0).objeto;
        eliminar(0);
        return res;
    }

    // Complejidad: O(1)
    public T obtenerPrimero() {
        if(lista.isEmpty()) return null;
        return lista.get(0).objeto;
    }

    // Complejidad: O(log n)
    public void modificar(int indice, T nuevoValor) {
        if(indice >= lista.size()) return;
        Nodo<T> nodo = lista.get(indice);
        T valorAntiguo = lista.get((indice - 1 )/ 2).objeto;
        nodo.objeto = nuevoValor;

        // Comparar el nuevo valor con el antiguo para decidir si usar siftUp o siftDown
        if (comparador.compare(nuevoValor, valorAntiguo) > 0) {
            this.siftUp(indice);
        } else {
            this.siftDown(indice);
        }
    }

    // toString para debugging
    public String toString(){
        StringBuilder res = new StringBuilder();
        for (Nodo<T> nodo : lista){
            res.append(nodo.objeto.toString()).append(" ");
        }
        return res.toString();
    }
}
