package aed;
import java.util.ArrayList;

public class HeapManager<T> {
    private ArrayList<Heap<T>> heapList;


    //Construcctor de HeapMaster vacio
    public HeapManager(Comparadores[] comparadores){
        heapList = new ArrayList<>();

        for (int index = 0; index < comparadores.length; index++) {
            Heap<T> heap = new Heap<T>(comparadores[index]);
            heap.index = index;
        }

        for (Comparadores comparador : comparadores) {

            Heap<T> heap = new Heap<T>(comparador);
            heapList.add(heap);

        }
    }

    // Construcctor de HeapMaster con datos
    public HeapManager(Comparadores[] comparadores, T[] datosIniciales) {
        heapList = new ArrayList<>();

        for (int index = 0; index < comparadores.length; index++) {
            Heap<T> heap = new Heap<T>(comparadores[index]);
            heap.index = index;
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
        Heap<T> heapObj = heapList.get(index);
        ArrayList<Integer> indicesPrimero = heapObj.obtenerIndices(0);
        T obj = heapObj.obtenerPrimero();

        eliminar(indicesPrimero.get(0), 0);

/*
        for (int i = 0; i < heapList.size(); index++) {
            if (index != i) {
                heapList.get(i).eliminar(indicesPrimero.get(i));
            }
        }
 */
        return obj;
    }
}
