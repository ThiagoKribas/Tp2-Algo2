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

        // Inicializar comparadores para el HeapManager de Traslados (Antiguo y Redituable) -> O(1)
        ArrayList<Comparator<Traslado>> comparadores = new ArrayList<>();
        comparadores.add(new Comparadores.MasAntiguo());
        comparadores.add(new Comparadores.MasRedituable());
        
        // Inicializar el HeapManager de Traslados con la lista de Traslados -> O(|T|)
        this.HeapManager = new HeapManager<Traslado>(comparadores, traslados);

        // Inicializar comparadores para el HeapManager de Ciudades (Superavit) -> O(1)
        ArrayList<Comparator<Ciudad>> comparadorSuperavit = new ArrayList<>();
        comparadorSuperavit.add(new Comparadores.MasSuperHabit());
        
        // Inicializar lista de ciudades -> O(|C|)
        listaCiudades = new Ciudad[cantCiudades]; 
        for (int index = 0; index < cantCiudades ; index++) {
            listaCiudades[index] = new Ciudad(index);
        }
        // Inicializar HeapManagerSuperAvit con la lista de Ciudades -> O(|C|)
        this.HeapManagerSuperAvit = new HeapManager<Ciudad>(comparadorSuperavit, listaCiudades); 
    }

    public void registrarTraslados(Traslado[] traslados){
        // O(|traslados| log(|T|))

        // Agregar cada traslado al HeapManager -> O(|traslados| * log(|T|))
        for (Traslado traslado : traslados) {
            // Agregar traslado al HeapManager de traslados -> O(log(|T|))
            HeapManager.agregar(traslado);
        }
    }

    public int[] despacharMasRedituables(int n){
        // O(n (log(|T|) + log(|C|)))
        
        // Ajustar n si es mayor que el número de traslados disponibles -> O(1)
        if(n > this.HeapManager.size()){
            n = this.HeapManager.size();
        }
        int[] MasRedituables = new int[n];

        // Despachar los n traslados más redituables -> O(n (log(|T|) + log(|C|)))
        for (int index = 0; index < n; index++) {
            // Sacar el traslado más redituable del HeapManager -> O(log(|T|))
            Traslado traslado = HeapManager.sacar(1);
            if(traslado == null) break;

            // Obtener las ciudades de origen y destino -> O(1)
            Ciudad origen = listaCiudades[traslado.origen];
            Ciudad destino = listaCiudades[traslado.destino];
            
            // Almacenar el id del traslado despachado -> O(1)
            MasRedituables[index] = traslado.id;

            // Actualizar ganancias y pérdidas de las ciudades -> O(1)
            origen.modificar(traslado.gananciaNeta, 0);
            destino.modificar(0, traslado.gananciaNeta);

            // Actualizar contadores globales -> O(1)
            gananciaDespachos += traslado.gananciaNeta;
            cantDespachos += 1;

            // Actualizar las ciudades en el HeapManagerSuperAvit -> O(log(|C|))
            HeapManagerSuperAvit.actualizar(origen.id);
            HeapManagerSuperAvit.actualizar(destino.id);

            // Actualizar listas de ciudades con mayor ganancia y pérdida -> O(1)
            compararGananciaCiudades(this.idCiudadesMayorGanancia, origen);
            compararPerdidaCiudades(this.idCiudadesMayorPerdida, destino); 
        }
        return MasRedituables;
    }
    
    
    public int[] despacharMasAntiguos(int n){
        // O(n (log(|T|) + log(|C|)))
        
        // Ajustar n si es mayor que el número de traslados disponibles -> O(1)
        if(n > HeapManager.size()){
            n = HeapManager.size();
        }
        int[] MasAntiguos = new int[n];
        
        // Despachar los n traslados más antiguos -> O(n (log(|T|) + log(|C|)))
        for (int index = 0; index < n; index++) {
            // Sacar el traslado más antiguo del HeapManager -> O(log(|T|))
            Traslado traslado = HeapManager.sacar(0);
            Ciudad origen = listaCiudades[traslado.origen];
            Ciudad destino = listaCiudades[traslado.destino];
            
            // Almacenar el id del traslado despachado -> O(1)
            MasAntiguos[index] = traslado.id;

            // Actualizar ganancias y pérdidas de las ciudades -> O(1)
            origen.modificar(traslado.gananciaNeta, 0);
            destino.modificar(0, traslado.gananciaNeta);

            // Actualizar contadores globales -> O(1)
            gananciaDespachos += traslado.gananciaNeta;
            cantDespachos += 1;

            // Actualizar las ciudades en el HeapManagerSuperAvit -> O(log(|C|))
            HeapManagerSuperAvit.actualizar(origen.id);
            HeapManagerSuperAvit.actualizar(destino.id);

            // Actualizar listas de ciudades con mayor ganancia y pérdida -> O(1)
            compararGananciaCiudades(this.idCiudadesMayorGanancia, origen);
            compararPerdidaCiudades(this.idCiudadesMayorPerdida, destino); 
        }
        return MasAntiguos;
    }
    
    private void compararGananciaCiudades(ArrayList<Integer> listaId, Ciudad ciudad) {
        // O(1)

        // Verificar si hay ciudades en el sistema -> O(1)
        if (listaCiudades.length == 0){
            return;
        }
        // Si la lista está vacía, agregar el id de la ciudad -> O(1)
        if (listaId.isEmpty()) {
            listaId.add(ciudad.id);
            return;
        }
        // Obtener la ciudad con mayor ganancia actual -> O(1)
        Ciudad mayor = listaCiudades[listaId.get(0)];
        
        // Evitar comparar la ciudad consigo misma -> O(1)
        if (mayor == ciudad){
            if (listaId.size() >= 2){
                mayor = listaCiudades[listaId.get(1)];
            } else {
                return;
            }
        }
        // Comparar ganancias y actualizar la lista si es necesario -> O(1)
        if(ciudad.ganancia == mayor.ganancia){
            // Si la ganancia es igual, agregar el id de la ciudad -> O(1)
            listaId.add(ciudad.id);
        } else if (ciudad.ganancia > mayor.ganancia) {
            // Si la ganancia es mayor, limpiar la lista y agregar el id -> O(1)
            listaId.clear();
            listaId.add(ciudad.id);
        }   
    }

    private void compararPerdidaCiudades(ArrayList<Integer> listaId, Ciudad ciudad) {
        // O(1)

        // Verificar si hay ciudades en el sistema -> O(1)
        if (listaCiudades.length == 0){
            return;
        }
        // Si la lista está vacía, agregar el id de la ciudad -> O(1)
        if (listaId.isEmpty()) {
            listaId.add(ciudad.id);
            return;
        }
        // Obtener la ciudad con mayor pérdida actual -> O(1)
        Ciudad mayor = listaCiudades[listaId.get(0)];

        // Evitar comparar la ciudad consigo misma -> O(1)
        if (mayor == ciudad){
            if (listaId.size() >= 2){
                mayor = listaCiudades[listaId.get(1)];
            } else {
                return;
            }
        }
        // Comparar pérdidas y actualizar la lista si es necesario -> O(1)
        if(ciudad.perdida == mayor.perdida){
            // Si la pérdida es igual, agregar el id de la ciudad -> O(1)
            listaId.add(ciudad.id);
        } else if (ciudad.perdida > mayor.perdida) {
            // Si la pérdida es mayor, limpiar la lista y agregar el id -> O(1)
            listaId.clear();
            listaId.add(ciudad.id);
        }   
    }


    public int ciudadConMayorSuperavit(){
        // O(1)
        // Obtener la ciudad con mayor superávit del HeapManager -> O(1)
        return HeapManagerSuperAvit.obtener(0).id;
    }

    public ArrayList<Integer> ciudadesConMayorGanancia(){
        // O(1)
        // Devolver la lista de ciudades con mayor ganancia -> O(1)
        return idCiudadesMayorGanancia;
    }

    public ArrayList<Integer> ciudadesConMayorPerdida(){
        // O(1)
        // Devolver la lista de ciudades con mayor pérdida -> O(1)
        return idCiudadesMayorPerdida;
    }

    public int gananciaPromedioPorTraslado(){
        // O(1)
        // Calcular y devolver la ganancia promedio por traslado -> O(1)
        return (gananciaDespachos / cantDespachos);
    }

    public String toString(){
        return "HeapManager: " + HeapManager.toString() +"\n";
    }
    
}
