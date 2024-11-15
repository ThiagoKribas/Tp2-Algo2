package aed;

import java.util.ArrayList;
import java.util.Comparator;

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
        // Inicializar variables de instancia
        this.cantDespachos = 0;
        this.gananciaDespachos = 0;
        this.idCiudadesMayorGanancia = new ArrayList<>();
        this.idCiudadesMayorPerdida = new ArrayList<>();
        this.HeapSuperAvit = new Heap<>(new Comparadores.MasSuperHabit()); // Inicializar HeapSuperAvit con el comparador adecuado

        // Inicializar comparadores para HeapManager
        ArrayList<Comparator<Traslado>> comparadores = new ArrayList<>();
        comparadores.add(new Comparadores.MasAntiguo());
        comparadores.add(new Comparadores.MasRedituable());
        this.HeapManager = new HeapManager<Traslado>(comparadores);

        // Inicializar lista de ciudades
        listaCiudades = new Ciudad[cantCiudades]; 
        for (int index = 0; index < cantCiudades ; index++) {
            listaCiudades[index] = new Ciudad(index);
        }

      
        // Agregar traslados al HeapManager
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
        // O(n (log(|T |) + log(|C|)))
        
        if(n > this.HeapManager.size()){
            n = this.HeapManager.size();
        }
        int[] MasRedituables = new int[n];

        // Despachamos los n Traslados mas Redituables O(n log(|T|))
        // Modificamos las variables en O(1)
        for (int index = 0; index < n; index++) {
            Traslado traslado = HeapManager.sacar(1);
            if(traslado == null) break;

            Ciudad origen = listaCiudades[traslado.origen];
            Ciudad destino = listaCiudades[traslado.destino];
            
            MasRedituables[index] = traslado.id;
            origen.ganancia += traslado.gananciaNeta;
            destino.perdida += traslado.gananciaNeta;
            gananciaDespachos += traslado.gananciaNeta;
            cantDespachos += 1;


            //PREGUNTAR SI PERDIMOS/GANAMOS COMPLEJIDAD
           compararGananciaCiudades(this.idCiudadesMayorGanancia, origen);
           compararPerdidaCiudades(this.idCiudadesMayorPerdida, destino); 
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
            
            compararGananciaCiudades(this.idCiudadesMayorGanancia, origen);
            compararPerdidaCiudades(this.idCiudadesMayorPerdida, destino); 
        }
        return MasAntiguos;
    }
    
    //Todas operaciones en O(1)
    private void compararGananciaCiudades(ArrayList<Integer> listaId, Ciudad ciudad) {
        if (listaCiudades.length == 0){
            return;
        }
        if (listaId.isEmpty()) {
            listaId.add(ciudad.id);
            return;
        }
        Ciudad mayor = listaCiudades[listaId.get(0)];
        
        if (mayor == ciudad){
            if (listaId.size() >= 2){
            mayor = listaCiudades[listaId.get(1)];
            } else {return;}
        }
        if(ciudad.ganancia == mayor.ganancia){
            listaId.add(ciudad.id);
        } else if (ciudad.ganancia > mayor.ganancia) {
            listaId.clear();
            listaId.add(ciudad.id);
        }   
        return;
    }

    private void compararPerdidaCiudades(ArrayList<Integer> listaId, Ciudad ciudad) {
        if (listaCiudades.length == 0){
            return;
        }
        if (listaId.isEmpty()) {
            listaId.add(ciudad.id);
            return;
        }
        Ciudad mayor = listaCiudades[listaId.get(0)];
        
        if (mayor == ciudad){
            if (listaId.size() >= 2){
            mayor = listaCiudades[listaId.get(1)];
            } else {return;}
        }
        if(ciudad.perdida == mayor.perdida){
            listaId.add(ciudad.id);
        } else if (ciudad.perdida > mayor.perdida) {
            listaId.clear();
            listaId.add(ciudad.id);
        }   
        return;
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

    public String toString(){
        return "HeapManager: " + HeapManager.toString() +"\n";
    }
    
}
