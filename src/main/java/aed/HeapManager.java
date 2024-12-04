package aed;

import java.util.ArrayList;
import java.util.Comparator;

public class HeapManager<T> {
    private ArrayList<Heap<T>> heapList;
    private ArrayList<Nodo<T>> nodoList;
    // Ciudad[] ciudadesInfo; 
    
    // Constructor
    public HeapManager(ArrayList<Comparator<T>> comparadores) {
        heapList = new ArrayList<>();
        nodoList = new ArrayList<>();

        for (int i = 0; i < comparadores.size(); i++) {
            Heap<T> heap = new Heap<>(comparadores.get(i));
            heap.index = i; // Asignamos el índice del heap
            heapList.add(heap);
        }
    }

    // Constructor con datos iniciales
    public HeapManager(ArrayList<Comparator<T>> comparadores, T[] datosIniciales) {
        heapList = new ArrayList<>();
        nodoList = new ArrayList<>();
        ArrayList<Nodo<T>> input = new ArrayList<>();
        int indexNodo = 0;
        for (T dato : datosIniciales) {
            Nodo<T> nodo = new Nodo<T>(dato);
            nodo.indices.add(indexNodo);
            indexNodo++;
            nodoList.add(nodo);
            input.add(nodo);
        }
        
        for (int index = 0; index < comparadores.size(); index++) {
            Heap<T> heap = new Heap<>(comparadores.get(index), input);
            heap.index = index;
            heapList.add(heap);
        }
        
    }

    // Agregar elemento a todos los heaps
    public void agregar(T valor) {
        if(valor != null){
            Nodo<T> nodoValor = new Nodo<T>(valor);
            for (Heap<T> heap : heapList) {
                heap.agregar(nodoValor); // Agregar el nodo a cada heap
            }
            nodoList.add(nodoValor); // Añadir el nodo a la lista de nodos
        }
    }

    // Complejidad: O(k log n), donde k es la cantidad de heaps
    // Sacar el primer elemento de un heap y eliminarlo de los demás
    public T sacar(int index) {
        if (index < 0 || index >= heapList.size()){
             return null;}
        Heap<T> heap = heapList.get(index);
        T elemento = heap.obtenerPrimero();

        // Eliminar el elemento de todos los heaps
        this.eliminar(0, index);

        return elemento;
    }

    // Ver el primer elemento de un heap
    public T obtener(int index) {
        if (index < 0 || index >= heapList.size()){
             return null;}
        Heap<T> heap = heapList.get(index);
        T elemento = heap.obtenerPrimero();

        return elemento;
    }

    // Complejidad: O(k log n)
    // Eliminar objeto de todos los heaps dado un índice
    public void eliminar(int Objindex, int heapIndex){

        if (heapIndex >= heapList.size()){
            return;
        }

        Heap<T> heap = heapList.get(heapIndex);

        if (Objindex >= heap.size()){
            return;
        }

        ArrayList<Integer> indicesObj = heap.obtenerIndices(Objindex); // Obtener índices en cada heap

        for (int index = 0; index < heapList.size(); index++) {
            heapList.get(index).eliminar(indicesObj.get(index)); // Eliminamos en cada heap
        }   
    }

    // Obtener el primer elemento de cada heap
    public ArrayList<T> verPrimero() {
        ArrayList<T> res = new ArrayList<>();
        for (Heap<T> heap : heapList) {
            res.add(heap.obtenerPrimero());
        }
        return res;
    }

    public int size(){
        if(heapList.isEmpty()) return 0;
        return heapList.get(0).size();
    }

    // Complejidad: O(k log n)
    public void actualizar(int posLista){
        for (int index = 0; index < heapList.size(); index++) {
            heapList.get(index).modificar(nodoList.get(posLista).indices.get(index), nodoList.get(posLista).objeto); // Actualizamos en cada heap
        }   
    }
    
    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < heapList.size(); i++) {
            res.append("Heap ").append(i).append(": ").append(heapList.get(i).toString()).append("\n");
        }
        return res.toString();
    }
}
