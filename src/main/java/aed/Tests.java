package aed;

import java.util.ArrayList;

public class Tests {

    public static void main(String[] args) {
        testNuevoSistemaYRegistrarTraslados();
        testDespacharMasRedituables();
        testDespacharMasAntiguos();
        testEstadisticas();
    }

    public static void testNuevoSistemaYRegistrarTraslados() {
        System.out.println("=== Test: nuevoSistema y registrarTraslados ===");
        Traslado[] trasladosIniciales = new Traslado[]{
            new Traslado(0, 0, 1, 100, 1),
            new Traslado(1, 1, 2, 200, 2)
        };
        BestEffort sistema = new BestEffort(3, trasladosIniciales);

        Traslado[] nuevosTraslados = new Traslado[]{
            new Traslado(2, 2, 0, 150, 3)
        };
        sistema.registrarTraslados(nuevosTraslados);

        System.out.println("Traslados registrados correctamente.");
        System.out.println();
    }

    public static void testDespacharMasRedituables() {
        System.out.println("=== Test: despacharMasRedituables ===");
        Traslado[] trasladosIniciales = new Traslado[]{
            new Traslado(0, 0, 1, 100, 1),
            new Traslado(1, 1, 2, 200, 2),
            new Traslado(2, 2, 0, 150, 3)
        };
        BestEffort sistema = new BestEffort(3, trasladosIniciales);

        int[] despachados = sistema.despacharMasRedituables(2);
        System.out.print("Despachados más redituables: ");
        for (int id : despachados) {
            System.out.print(id + " ");
        }
        // Debería imprimir: Despachados más redituables: 1 2
        System.out.println();
        System.out.println();
    }

    public static void testDespacharMasAntiguos() {
        System.out.println("=== Test: despacharMasAntiguos ===");
        Traslado[] trasladosIniciales = new Traslado[]{
            new Traslado(0, 0, 1, 100, 3),
            new Traslado(1, 1, 2, 200, 1),
            new Traslado(2, 2, 0, 150, 2)
        };
        BestEffort sistema = new BestEffort(3, trasladosIniciales);

        int[] despachados = sistema.despacharMasAntiguos(2);
        System.out.print("Despachados más antiguos: ");
        for (int id : despachados) {
            System.out.print(id + " ");
        }
        // Debería imprimir: Despachados más antiguos: 1 2
        System.out.println();
        System.out.println();
    }

    public static void testEstadisticas() {
        System.out.println("=== Test: Estadísticas ===");
        Traslado[] trasladosIniciales = new Traslado[]{
            new Traslado(0, 0, 1, 100, 1),
            new Traslado(1, 0, 2, 200, 2),
            new Traslado(2, 1, 0, 150, 3),
            new Traslado(3, 2, 0, 120, 4)
        };
        BestEffort sistema = new BestEffort(3, trasladosIniciales);

        // Despachamos todos los traslados
        int[] despachados = sistema.despacharMasRedituables(4);

        int ciudadMayorSuperavit = sistema.ciudadConMayorSuperavit();
        System.out.println("Ciudad con mayor superávit: " + ciudadMayorSuperavit);
        // Debería ser la ciudad 0: ganancia = 300, pérdida = 270, superávit = 30

        ArrayList<Integer> ciudadesMayorGanancia = sistema.ciudadesConMayorGanancia();
        System.out.println("Ciudades con mayor ganancia: " + ciudadesMayorGanancia);
        // Debería ser [0], ganancia de 300

        ArrayList<Integer> ciudadesMayorPerdida = sistema.ciudadesConMayorPerdida();
        System.out.println("Ciudades con mayor pérdida: " + ciudadesMayorPerdida);
        // Debería ser [0], pérdida de 270

        int gananciaPromedio = sistema.gananciaPromedioPorTraslado();
        System.out.println("Ganancia promedio por traslado: " + gananciaPromedio);
        // Debería ser (100+200+150+120)/4 = 142
        System.out.println();
    }
}

// Definición de la clase Traslado
class Traslado {
    int id;
    int origen;
    int destino;
    int gananciaNeta;
    int timestamp;

    public Traslado(int id, int origen, int destino, int gananciaNeta, int timestamp) {
        this.id = id;
        this.origen = origen;
        this.destino = destino;
        this.gananciaNeta = gananciaNeta;
        this.timestamp = timestamp;
    }
}
