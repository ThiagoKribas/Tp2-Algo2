package aed;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class HeapManager<T> {
    private List<Heap<T>> heaps;

    public HeapManager(List<Comparator<T>> comparadores){
        heaps = new ArrayList<>();

        for (Comparator<T> comparador : comparadores) {

            Heap<T> heap = new Heap<T>(comparador);
            
            heaps.add(heap);

        }
    }

    // agrego comparadores y datos en ambos heaps
    public HeapManager(List<Comparator<T>> comparadores, List<T> datosIniciales) {
        heaps = new ArrayList<>();

        for (Comparator<T> comparador : comparadores) {

            Heap<T> heap = new Heap<T>(comparador);
            
            heaps.add(heap);

        }

        for (T dato : datosIniciales) {
            
            Heap<T>.Nodo nodo = null;

            for (Heap<T> heap : heaps) {
                if (nodo == null) {
                    nodo = heap.new Nodo(dato);
                }
                heap.agregar(nodo);
            }
        }

    }

    // agrego dinamicamente el elemento en todos los heaps
    public void agregar(T valor) {

        Heap<T>.Nodo nodo = null;

        for (Heap<T> heap : heaps) {
            if (nodo == null) {
                nodo = new Nodo(valor);
            }
            heap.agregar(nodo);
        }

    }

    // elimino en los heaps
    public void eliminar(T valor) {
        Heap<T>.Nodo nodo = buscarNodo(valor);
        if (nodo == null) return;

        for (Heap<T> heap : heaps) {
            heap.eliminar(nodo);
        }
    }
}
