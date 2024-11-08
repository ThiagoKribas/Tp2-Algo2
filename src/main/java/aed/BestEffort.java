package aed;

import java.util.ArrayList;
import Array;

public class BestEffort {
    
    int cantDespachos;
    int gananciaDespachos;
    

    public BestEffort(int cantCiudades, Traslado[] traslados){
        // O(|C| + |T|)
        Heap heapAntiguedad = new Heap(new Comparadores.MasAntiguo());
        Heap heapGanancia = new Heap(new Comparadores.MasRedituable());
        Heap heapCiudades = new Heap(new Comparadores.MasSuperHabit());
        Ciudad[] listaCiudades = new Ciudad[cantCiudades];

        // Itero cant de ciudades (Ints)
        for (int index = 0; index < cantCiudades ; index++) {
            listaCiudades[index] = new Ciudad(index);
        }

        // Itero traslados (id, origen, destino, gananciaNeta, timestamp)
        for (Traslados traslado : traslados) {
            this.heapAntiguedad.agregar(traslado);
            this.heapGanancia.agregar(traslado)
        }

            // Clase de Heap dinamica para instaciar los heaps
    }

    public void registrarTraslados(Traslado[] traslados){
        // O(|traslados| log(|T|))
        for (Traslado[] traslado : traslados) {
            
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

        // Itero las N cantidad de despachos (SI N>Traslados.nodosDelHeap -> despacho todos)
            // Busco las N cantidad de traslados en HeapGanancias (Hay que eliminar, sabemos que es el minimo, log n) (log t) 
            // Modifico las 2 ciudades para mantener la estadistica (2 * log = log, cumple avl) (debe ser log c) 
        
        // usamos handler para eliminar del otro heap
        return null;
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

        // despachamos -> Agregamos ciudades inexistentes y actualizamos existentes

        // HEAP Superavit O(1)

        return 0;
    }

    public ArrayList<Integer> ciudadesConMayorGanancia(){
        // O(1)

        // despachamos -> Agregamos ciudades inexistentes y actualizamos existentes

        // HEAP Superavit - obs masGanancia = []

        return null;
    }

    public ArrayList<Integer> ciudadesConMayorPerdida(){
        // O(1)

        // despachamos -> Agregamos ciudades inexistentes y actualizamos existentes

        // HEAP Superavit - obs masPerdida = []

        return null;
    }

    public int gananciaPromedioPorTraslado(){
        // O(1)

        // despachamos -> Despachados ganancia sumada / cantidad de despachados 

        // HEAP Superavit - obs Ganancia Total / 2 = Int 

        return 0;
    }
    
}
