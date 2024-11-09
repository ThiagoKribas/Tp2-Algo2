package aed;

import java.util.ArrayList;

public class BestEffort {
    
    int cantDespachos;
    int gananciaDespachos;
    ArrayList<Integer> idCiudadesMayorGanancia;
    ArrayList<Integer> idCiudadesMayorPerdida;
    HeapManager<Heap<Traslado>> HeapManager;
    Heap<Ciudad> HeapSuperAvit;

    public BestEffort(int cantCiudades, Traslado[] traslados){
        // O(|C| + |T|)

        //IMPLEMENTAR DOUBLE HEAP 

        //Heap<Traslado> heapAntiguedad = new Heap(new Comparadores.MasAntiguo());
        //Heap<Traslado> heapGanancia = new Heap(new Comparadores.MasRedituable());
        //Heap<Ciudad> heapCiudades = new Heap(new Comparadores.MasSuperHabit());
        Ciudad[] listaCiudades = new Ciudad[cantCiudades];

        // Itero cant de ciudades (Ints)
        for (int index = 0; index < cantCiudades ; index++) {
            listaCiudades[index] = new Ciudad(index);
        }

        // Itero traslados (id, origen, destino, gananciaNeta, timestamp)
        for (Traslado traslado : traslados) {
            //Agregar a HeapManager
        }

            // Clase de Heap dinamica para instaciar los heaps
    }

    public void registrarTraslados(Traslado[] traslados){
        // O(|traslados| log(|T|))
        for (Traslado traslado : traslados) {
            
        }
            // Agrego traslado al HeapAntiguedad
            // Agrego traslado al HeapGanancia

            //o

            // Agrego a multiheap (ambos heaps)

            // handler -> sincronizar los heaps
            // Clase -> [T1] [T1] -> {pos_h1 pos_h2} -> Swaps actualizan posiciones

            // 2 log(|T|) = O(log(|T|))

    }

    public int[] despacharMasRedituables(int n){
        //  O(n (log(|T |) + log(|C|)))
        int[] MasRedituables = new int[10];
         for (int index = 0; index < n; index++) {
            //MasRedituables[index] = HeapManager.obtenerMasAntiguo
         }
         
        // Itero las N cantidad de despachos (SI N>Traslados.nodosDelHeap -> despacho todos)
            // Busco las N cantidad de traslados en HeapGanancias (Hay que eliminar, sabemos que es el minimo, log n) (log t) 
            // Modifico las 2 ciudades para mantener la estadistica (2 * log = log, cumple avl) (debe ser log c) 
        
        // usamos handler para eliminar del otro heap
        return MasRedituables;
    }

    public int[] despacharMasAntiguos(int n){
        //  O(n (log(|T |) + log(|C|)))

        // Itero las N cantidad de despachos (SI N>Traslados.length -> despacho todos)
            // Busco las N cantidad de traslados en HeapAntiguedad (Hay que eliminar, sabemos que es el mas, log n) (log t) 
            // Modifico las 2 ciudades para mantener la estadistica (2 * log = log, cumple avl) (debe ser log c) 

        // usamos handler para eliminar del otro heap
        return null;
    }

    public int ciudadConMayorSuperavit(){
        // O(1)
        return HeapSuperAvit.obtenerPrimero().id;
    }

    public ArrayList<Integer> ciudadesConMayorGanancia(){
        //O(1)
        return idCiudadesMayorGanancia;
    }

    public ArrayList<Integer> ciudadesConMayorPerdida(){
        //O(1)
        return idCiudadesMayorPerdida;
    }

    public int gananciaPromedioPorTraslado(){
        //O(1)
        return (gananciaDespachos / cantDespachos);
    }
    
}
