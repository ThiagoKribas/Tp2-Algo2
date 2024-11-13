package aed;

import java.util.ArrayList;
import java.util.Comparator;

public class HeapManager<T> {
    private ArrayList<Heap<T>> heapList;

    // Constructor vacío
    public HeapManager(ArrayList<Comparator<T>> comparadores) {
        heapList = new ArrayList<>();

        for (int i = 0; i < comparadores.size(); i++) {
            Heap<T> heap = new Heap<>(comparadores.get(i));
            heap.index = i;
            heapList.add(heap);
        }
    }

    // Constructor con datos iniciales
    public HeapManager(ArrayList<Comparator<T>> comparadores, T[] datosIniciales) {
        heapList = new ArrayList<>();

        for (int index = 0; index < comparadores.size(); index++) {
            Heap<T> heap = new Heap<>(comparadores.get(index));
            heap.index = index;
            heapList.add(heap);
        }

        for (T dato : datosIniciales) {
            agregar(dato);
        }
    }

    // Agregar elemento a todos los heaps
    public void agregar(T valor) {
        if(valor != null){
            for (Heap<T> heap : heapList) {
                heap.agregar(valor);
            }
        }
    }

    // Sacar el primer elemento de un heap y eliminarlo de los demás
    public T sacar(int index) {
        if (index < 0 || index >= heapList.size()) return null;
        Heap<T> heap = heapList.get(index);
        T elemento = heap.obtenerPrimero();

        // Eliminar el elemento de todos los heaps
        for (Heap<T> h : heapList) {
            h.eliminarElemento(elemento);
        }

        return elemento;
    }

    // Eliminar objeto de todos los heaps dado un índice
    public void eliminar(int heapIndex, int objIndex) {
        if (heapIndex < 0 || heapIndex >= heapList.size()) return;
        Heap<T> heap = heapList.get(heapIndex);
        if (objIndex < 0 || objIndex >= heap.size()) return;

        T elemento = heap.get(objIndex);
        if (elemento == null) return;

        for (Heap<T> h : heapList) {
            h.eliminarElemento(elemento);
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

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < heapList.size(); i++) {
            res.append("Heap ").append(i).append(": ").append(heapList.get(i).toString()).append("\n");
        }
        return res.toString();
    }
}
