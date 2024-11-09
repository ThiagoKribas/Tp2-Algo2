package aed;

import java.util.ArrayList;

/* import aed.Comparadores.MasAntiguo;
import aed.Comparadores.MasRedituable; */

public class BestEffort {
    
    int cantDespachos;
    int gananciaDespachos;
    ArrayList<Integer> idCiudadesMayorGanancia;
    ArrayList<Integer> idCiudadesMayorPerdida;
    HeapManager<Traslado> HeapManager;
    Heap<Ciudad> HeapSuperAvit;
    Ciudad[] listaCiudades;

    public BestEffort(int cantCiudades, Traslado[] traslados){
        // O(|C| + |T|)

        //TODO REVISAR COMPARADORES; CLASS; IMPLEMENTACION.
/*         Comparadores.MasAntiguo comparadorMasAntiguo = new Comparadores.MasAntiguo();
        Comparadores.MasRedituable comparadorMasRedituable = new Comparadores.MasRedituable();
 */
        Comparadores[] comparadores = new Comparadores[2];
/*      comparadores[0] = comparadorMasAntiguo;
        comparadores[1] = comparadorMasRedituable; */
        HeapManager = new HeapManager<Traslado>(comparadores);

        listaCiudades = new Ciudad[cantCiudades];

        // Itero cant de ciudades O(|C|)
        for (int index = 0; index < cantCiudades ; index++) {
            listaCiudades[index] = new Ciudad(index);
        }

        // Itero traslados O(2|T|) -> O(|T|)
        for (Traslado traslado : traslados) {
            HeapManager.agregar(traslado);
        }

    }

    public void registrarTraslados(Traslado[] traslados){
        // O(|traslados| log(|T|))

        // Agrega |traslados| elementos, y la funcion agregar funciona en log(|T|) 
        for (Traslado traslado : traslados) {
            HeapManager.agregar(traslado);
        }
    }

    public int[] despacharMasRedituables(int n){
        //  O(n (log(|T |) + log(|C|)))
        
        if(n > HeapManager.size()){
            n = HeapManager.size();
        }
        int[] MasRedituables = new int[n];

        // Despachamos los n Traslados mas Redituables O(n log(|T|))
        // Modificamos las variables en O(1)
        for (int index = 0; index < n; index++) {
            Traslado traslado = HeapManager.sacar(1);
            Ciudad origen = listaCiudades[traslado.origen];
            Ciudad destino = listaCiudades[traslado.destino];
            
            MasRedituables[index] = traslado.id;
            origen.ganancia += traslado.gananciaNeta;
            destino.perdida += traslado.gananciaNeta;
            gananciaDespachos += traslado.gananciaNeta;
            cantDespachos += 1;

            compararStatsCiudades(idCiudadesMayorGanancia, origen);
            compararStatsCiudades(idCiudadesMayorPerdida, destino); 
        }
        return MasRedituables;
    }
    
    
    public int[] despacharMasAntiguos(int n){
        //  O(n (log(|T |) + log(|C|)))
        
        if(n > HeapManager.size()){
            n = HeapManager.size();
        }
        int[] MasAntiguos = new int[n];
        
        // Despachamos los n Traslados mas Antiguos O(n log(|T|))
        // Modificamos las variables en O(1)
        for (int index = 0; index < n; index++) {
            Traslado traslado = HeapManager.sacar(0);
            Ciudad origen = listaCiudades[traslado.origen];
            Ciudad destino = listaCiudades[traslado.destino];
            
            MasAntiguos[index] = traslado.id;
            origen.ganancia += traslado.gananciaNeta;
            destino.perdida += traslado.gananciaNeta;
            gananciaDespachos += traslado.gananciaNeta;
            cantDespachos += 1;
            
            compararStatsCiudades(idCiudadesMayorGanancia, origen);
            compararStatsCiudades(idCiudadesMayorPerdida, destino); 
        }
        return MasAntiguos;
    }
    
    //Todas operaciones en O(1)
    private void compararStatsCiudades(ArrayList<Integer> listaId, Ciudad ciudad) {
        Ciudad mayor = listaCiudades[listaId.get(0)];
        if(ciudad.ganancia == mayor.ganancia){
            listaId.add(ciudad.id);
        } else if (ciudad.ganancia > mayor.ganancia) {
            listaId.clear();
            listaId.add(ciudad.id);
        }   
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
