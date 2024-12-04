package aed;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class BestEffortStress {

    int cantCiudades;
    Traslado[] listaTraslados;
    Random random;

    @BeforeEach
    void setUp(){
        // Inicializamos con una gran cantidad de ciudades y traslados para estresar el sistema
        cantCiudades = 1000; // Mil ciudades
        int cantTraslados = 100000; // Cien mil traslados
        listaTraslados = new Traslado[cantTraslados];
        random = new Random(42); // Semilla fija para reproducibilidad

        for (int i = 0; i < cantTraslados; i++) {
            int origen = random.nextInt(cantCiudades);
            int destino;
            do {
                destino = random.nextInt(cantCiudades);
            } while (destino == origen); // Aseguramos que origen y destino sean diferentes

            int gananciaNeta = random.nextInt(1000) + 1; // Ganancia entre 1 y 1000
            int timestamp = i; // Timestamp incremental

            listaTraslados[i] = new Traslado(i, origen, destino, gananciaNeta, timestamp);
        }
    }

    @Test
    void stressTestDespacharMasRedituables() {
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);

        // Despachamos 50,000 traslados más redituables
        int n = 50000;
        int[] despachados = sis.despacharMasRedituables(n);

        assertEquals(n, despachados.length, "No se despachó la cantidad esperada de traslados.");

        // Verificamos que los traslados están ordenados por ganancia decreciente
        for (int i = 1; i < despachados.length; i++) {
            Traslado t1 = listaTraslados[despachados[i - 1]];
            Traslado t2 = listaTraslados[despachados[i]];

            assertTrue(t1.gananciaNeta >= t2.gananciaNeta, "Los traslados no están ordenados por ganancia decreciente.");
        }
    }

    @Test
    void stressTestDespacharMasAntiguos() {
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);

        // Despachamos 50,000 traslados más antiguos
        int n = 50000;
        int[] despachados = sis.despacharMasAntiguos(n);

        assertEquals(n, despachados.length, "No se despachó la cantidad esperada de traslados.");

        // Verificamos que los traslados están ordenados por timestamp creciente
        for (int i = 1; i < despachados.length; i++) {
            Traslado t1 = listaTraslados[despachados[i - 1]];
            Traslado t2 = listaTraslados[despachados[i]];

            assertTrue(t1.timestamp <= t2.timestamp, "Los traslados no están ordenados por timestamp creciente.");
        }
    }

    @Test
    void stressTestRegistrarYDespachar() {
        BestEffort sis = new BestEffort(this.cantCiudades, new Traslado[0]);

        // Registramos los traslados en lotes de 10,000
        int batchSize = 10000;
        for (int i = 0; i < listaTraslados.length; i += batchSize) {
            int end = Math.min(i + batchSize, listaTraslados.length);
            Traslado[] batch = Arrays.copyOfRange(listaTraslados, i, end);
            sis.registrarTraslados(batch);

            // Despachamos 5,000 traslados más redituables
            int[] despachados = sis.despacharMasRedituables(5000);
            assertEquals(5000, despachados.length, "No se despachó la cantidad esperada de traslados.");
        }
    }

    @Test
    void stressTestEstadisticas() {
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);

        // Despachamos todos los traslados
        int totalTraslados = listaTraslados.length;
        sis.despacharMasRedituables(totalTraslados);

        // Verificamos que la ganancia promedio sea correcta
        int gananciaTotal = Arrays.stream(listaTraslados).mapToInt(t -> t.gananciaNeta).sum();
        int gananciaPromedioEsperada = gananciaTotal / totalTraslados;
        assertEquals(gananciaPromedioEsperada, sis.gananciaPromedioPorTraslado(), "La ganancia promedio por traslado no es correcta.");

        // Verificamos que la ciudad con mayor superávit sea correcta
        int[] ganancias = new int[cantCiudades];
        int[] perdidas = new int[cantCiudades];

        for (Traslado t : listaTraslados) {
            ganancias[t.origen] += t.gananciaNeta;
            perdidas[t.destino] += t.gananciaNeta;
        }

        int[] superavit = new int[cantCiudades];
        for (int i = 0; i < cantCiudades; i++) {
            superavit[i] = ganancias[i] - perdidas[i];
        }

        int ciudadConMayorSuperavitEsperada = 0;
        for (int i = 1; i < cantCiudades; i++) {
            if (superavit[i] > superavit[ciudadConMayorSuperavitEsperada]) {
                ciudadConMayorSuperavitEsperada = i;
            }
        }

        assertEquals(ciudadConMayorSuperavitEsperada, sis.ciudadConMayorSuperavit(), "La ciudad con mayor superávit no es correcta.");
    }

    @Test
    void stressTestConcurrentDespachar() {
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);

        // Despachamos intercaladamente más redituables y más antiguos
        int n = 10000;
        for (int i = 0; i < 5; i++) {
            int[] despachadosRedituables = sis.despacharMasRedituables(n);
            int[] despachadosAntiguos = sis.despacharMasAntiguos(n);

            assertEquals(n, despachadosRedituables.length, "No se despachó la cantidad esperada de traslados redituables.");
            assertEquals(n, despachadosAntiguos.length, "No se despachó la cantidad esperada de traslados antiguos.");
        }
    }

    @Test
    void stressTestConCiudadesExtremas() {
        // Test con una gran cantidad de ciudades
        cantCiudades = 100000; // Cien mil ciudades
        int cantTraslados = 100000;
        listaTraslados = new Traslado[cantTraslados];
        random = new Random(42);

        for (int i = 0; i < cantTraslados; i++) {
            int origen = random.nextInt(cantCiudades);
            int destino;
            do {
                destino = random.nextInt(cantCiudades);
            } while (destino == origen);

            int gananciaNeta = random.nextInt(1000) + 1;
            int timestamp = i;

            listaTraslados[i] = new Traslado(i, origen, destino, gananciaNeta, timestamp);
        }

        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);

        // Despachamos algunos traslados y verificamos que las operaciones funcionen
        int[] despachados = sis.despacharMasRedituables(50000);
        assertEquals(50000, despachados.length, "No se despachó la cantidad esperada de traslados.");
    }

    @Test
    void stressTestRegistrarTrasladosMasivos() {
        BestEffort sis = new BestEffort(this.cantCiudades, new Traslado[0]);

        // Registramos 1 millón de traslados
        int cantTraslados = 1000000;
        Traslado[] nuevosTraslados = new Traslado[cantTraslados];

        for (int i = 0; i < cantTraslados; i++) {
            int origen = random.nextInt(cantCiudades);
            int destino;
            do {
                destino = random.nextInt(cantCiudades);
            } while (destino == origen);

            int gananciaNeta = random.nextInt(5000) + 1;
            int timestamp = i + listaTraslados.length; // Continuamos los timestamps

            nuevosTraslados[i] = new Traslado(i + listaTraslados.length, origen, destino, gananciaNeta, timestamp);
        }

        sis.registrarTraslados(nuevosTraslados);

        // Despachamos 100,000 traslados más redituables
        int[] despachados = sis.despacharMasRedituables(100000);
        assertEquals(100000, despachados.length, "No se despachó la cantidad esperada de traslados.");
    }


    @Test
    void stressTestDespacharConMasGananciaDeAUno() {
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);

        // Despachamos de a uno los traslados más redituables
        for (int i = 0; i < 1000; i++) {
            int[] despachados = sis.despacharMasRedituables(1);
            assertEquals(1, despachados.length, "No se despachó un traslado.");
        }

        // Verificamos las ciudades con mayor ganancia y pérdida
        ArrayList<Integer> ciudadesConMayorGanancia = sis.ciudadesConMayorGanancia();
        ArrayList<Integer> ciudadesConMayorPerdida = sis.ciudadesConMayorPerdida();

        assertNotNull(ciudadesConMayorGanancia, "La lista de ciudades con mayor ganancia es nula.");
        assertNotNull(ciudadesConMayorPerdida, "La lista de ciudades con mayor pérdida es nula.");
    }

    @Test
    void stressTestPromedioPorTraslado() {
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);

        // Despachamos traslados en lotes y calculamos el promedio
        int totalDespachados = 0;
        int totalGanancia = 0;
        int lote = 10000;

        while (sis.HeapManager.size() > 0) {
            int n = Math.min(lote, sis.HeapManager.size());
            int[] despachados = sis.despacharMasRedituables(n);
            totalDespachados += despachados.length;

            // Sumar las ganancias de los traslados despachados
            for (int id : despachados) {
                totalGanancia += listaTraslados[id].gananciaNeta;
            }

            int gananciaPromedioEsperada = totalGanancia / totalDespachados;
            int gananciaPromedioSistema = sis.gananciaPromedioPorTraslado();

            assertEquals(gananciaPromedioEsperada, gananciaPromedioSistema, "La ganancia promedio por traslado no coincide.");
        }
    }

    @Test
    void stressTestMayorSuperavit() {
        BestEffort sis = new BestEffort(this.cantCiudades, this.listaTraslados);

        // Despachamos traslados y verificamos la ciudad con mayor superávit después de cada despacho
        int lote = 5000;
        int[] ganancias = new int[cantCiudades];
        int[] perdidas = new int[cantCiudades];

        while (sis.HeapManager.size() > 0) {
            int n = Math.min(lote, sis.HeapManager.size());
            int[] despachados = sis.despacharMasAntiguos(n);

            for (int id : despachados) {
                Traslado t = listaTraslados[id];
                ganancias[t.origen] += t.gananciaNeta;
                perdidas[t.destino] += t.gananciaNeta;
            }

            // Calcular ciudad con mayor superávit esperado
            int ciudadConMayorSuperavitEsperada = 0;
            int maxSuperavit = ganancias[0] - perdidas[0];
            for (int i = 1; i < cantCiudades; i++) {
                int superavit = ganancias[i] - perdidas[i];
                if (superavit > maxSuperavit || (superavit == maxSuperavit && i < ciudadConMayorSuperavitEsperada)) {
                    maxSuperavit = superavit;
                    ciudadConMayorSuperavitEsperada = i;
                }
            }

            int ciudadConMayorSuperavitSistema = sis.ciudadConMayorSuperavit();

            assertEquals(ciudadConMayorSuperavitEsperada, ciudadConMayorSuperavitSistema, "La ciudad con mayor superávit no coincide.");
        }
    }
    
}
