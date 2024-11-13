package aed;
import java.util.ArrayList;
import java.util.Comparator;

public class HeapManager<T> {
    private ArrayList<Heap<T>> heapList;


    //Construcctor de HeapMaster vacio
    public HeapManager(ArrayList<Comparator<T>> comparadores){
        heapList = new ArrayList<>();

        for (int i = 0; i < comparadores.size(); i++) {
            Heap<T> heap = new Heap<>(comparadores.get(i));
            heap.index = i;
            heapList.add(heap);
        }
    }

    // Construcctor de HeapMaster con datos
    public HeapManager(Comparator<T>[] comparadores, T[] datosIniciales) {
        heapList = new ArrayList<>();

        for (int index = 0; index < comparadores.length; index++) {
            Heap<T> heap = new Heap<T>((Comparator<T>)comparadores[index]);
            heap.index = index;
            heapList.add(heap);
        }

        for (T dato : datosIniciales) {
                agregar(dato);
        }
    }

    public int size(){
        return heapList.get(0).size();
    }

    // agrego dinamicamente el elemento en todos los heapList
    public void agregar(T valor) {
        for (Heap<T> heap : heapList) {
            heap.agregar(valor);
        }
    }

    // Elimino objeto de todos heaps dado un indice de objeto en un nodo O(heapList.size() * log(|T|))
    //
    public void eliminar(int Objindex, int heapIndex){
        
        if (heapIndex >= heapList.size()){
            return;
        }

        Heap<T> heap = heapList.get(heapIndex);

        if (Objindex >= heap.size()){
            return;
        }

        ArrayList<Integer> indicesObj = heap.obtenerIndices(Objindex);
        
        for (int index = 0; index < heapList.size(); index++) {
            heapList.get(index).eliminar(indicesObj.get(index));                
        }   
    }

    //Obtengo el primer objeto de un heap y elimino del resto O(heapList.size() * log(|T|))
    // heapList.size() es constante en este problema -> O(log(|T|))
    public T sacar(int index){
        if (index >= heapList.size() || heapList.get(index).size() == 0) {
            throw new IllegalStateException("Heap vacío o índice inválido");
        }
        
        Heap<T> heap = heapList.get(index);
        T elemento = heap.obtenerPrimero();
        
        // Eliminar el elemento de todos los heaps
        for (Heap<T> h : heapList) {
            h.eliminarElemento(elemento);
        }
        
        return elemento;
    }
}
