package aed;

import java.util.ArrayList;
import java.util.Comparator;

public class BestEffort {
    
    int cantDespachos;
    int gananciaDespachos;
    ArrayList<Integer> idCiudadesMayorGanancia;
    ArrayList<Integer> idCiudadesMayorPerdida;
    HeapManager<Traslado> HeapManager;
    HeapManager<Ciudad> HeapManagerSuperAvit;
    Ciudad[] listaCiudades;

    public BestEffort(int cantCiudades, Traslado[] traslados){
        // O(|T| + |C|)


        // Inicializar variables de instancia -> O(1)
        this.cantDespachos = 0;
        this.gananciaDespachos = 0;
        this.idCiudadesMayorGanancia = new ArrayList<>();
        this.idCiudadesMayorPerdida = new ArrayList<>();

        // Inicializar comparadores para los HeapManagers -> O(1)
        ArrayList<Comparator<Traslado>> comparadores = new ArrayList<>();
        comparadores.add(new Comparadores.MasAntiguo());
        comparadores.add(new Comparadores.MasRedituable());
        ArrayList<Comparator<Ciudad>> comparadorSuperavit = new ArrayList<>();
        comparadorSuperavit.add(new Comparadores.MasSuperHabit());
        this.HeapManager = new HeapManager<Traslado>(comparadores);
        this.HeapManagerSuperAvit = new HeapManager<Ciudad>(comparadorSuperavit); // Inicializar HeapManagerSuperAvit con el comparador adecuado

        // Inicializar lista de ciudades -> O(|C|)
        listaCiudades = new Ciudad[cantCiudades]; 
        for (int index = 0; index < cantCiudades ; index++) {
            listaCiudades[index] = new Ciudad(index);
        }
        
        // Inicializar HeapManagerSuperAvit con lista de ciudades -> O(|C|)
        this.HeapManagerSuperAvit = new HeapManager<Ciudad>(comparadorSuperavit, listaCiudades); // Inicializar HeapManagerSuperAvit con el comparador adecuado
      
        // Agregar traslados al HeapManager -> O(|T|)
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
            origen.modificar(traslado.gananciaNeta, 0);
            destino.modificar(0, traslado.gananciaNeta);
            gananciaDespachos += traslado.gananciaNeta;
            cantDespachos += 1;

            HeapManagerSuperAvit.actualizar(origen.id);
            HeapManagerSuperAvit.actualizar(destino.id);

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
            origen.modificar(traslado.gananciaNeta, 0);
            destino.modificar(0, traslado.gananciaNeta);
            gananciaDespachos += traslado.gananciaNeta;
            cantDespachos += 1;
            HeapManagerSuperAvit.actualizar(origen.id);
            HeapManagerSuperAvit.actualizar(destino.id);

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
        //O(1)
        return HeapManagerSuperAvit.obtener(0).id;
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
