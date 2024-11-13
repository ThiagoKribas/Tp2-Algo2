package aed;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Comparator;

public class HeapManagerTest {
    
    // Comparadores de ejemplo
    private static final Comparator<Integer> ascendente = Integer::compareTo;
    private static final Comparator<Integer> descendente = (a, b) -> b.compareTo(a);
    
    @Test
    public void testConstructorVacio() {
        ArrayList<Comparator<Integer>> comparadores = new ArrayList<>();
        comparadores.add(ascendente);
        comparadores.add(descendente);
        
        HeapManager<Integer> manager = new HeapManager<>(comparadores);
        assertEquals(0, manager.size());
    }

    @Test
    public void testConstructorConDatos() {
        ArrayList<Comparator<Integer>> comparadores = new ArrayList<>();
        comparadores.add(ascendente);
        comparadores.add(descendente);
        
        Integer[] datos = new Integer[]{5, 3, 7};
        
        HeapManager<Integer> manager = new HeapManager<>(comparadores, datos);
        assertEquals(3, manager.size());
    }

    @Test
    public void testAgregarElemento() {
        ArrayList<Comparator<Integer>> comparadores = new ArrayList<>();
        comparadores.add(ascendente);
        comparadores.add(descendente);
        
        HeapManager<Integer> manager = new HeapManager<>(comparadores);
        manager.agregar(5);
        manager.agregar(3);
        manager.agregar(7);
        
        assertEquals(3, manager.size());
    }

    @Test
    public void testSacarElementosDiferentesHeaps() {
        ArrayList<Comparator<Integer>> comparadores = new ArrayList<>();
        // Min heap - el menor elemento estará en la raíz
        comparadores.add((a, b) -> a.compareTo(b));
        // Max heap - el mayor elemento estará en la raíz
        comparadores.add((a, b) -> b.compareTo(a));
        
        HeapManager<Integer> manager = new HeapManager<>(comparadores);
        
        // Agregar elementos en orden
        manager.agregar(5);
        manager.agregar(3);
        manager.agregar(7);
        
        // Verificar que sacamos el elemento correcto de cada heap
        assertEquals(Integer.valueOf(3), manager.sacar(0));  // Min heap devuelve el menor
        assertEquals(2, manager.size());
        
        assertEquals(Integer.valueOf(7), manager.sacar(1));  // Max heap devuelve el mayor
        assertEquals(1, manager.size());
    }

    @Test
    public void testEliminarElemento() {
        ArrayList<Comparator<Integer>> comparadores = new ArrayList<>();
        comparadores.add(ascendente);
        comparadores.add(descendente);
        
        HeapManager<Integer> manager = new HeapManager<>(comparadores);
        manager.agregar(5);
        manager.agregar(3);
        manager.agregar(7);
        
        int tamañoInicial = manager.size();
        manager.eliminar(0, 0);
        assertEquals(tamañoInicial - 1, manager.size());
    }

    @Test
    public void testConsistenciaEntreHeaps() {
        ArrayList<Comparator<Integer>> comparadores = new ArrayList<>();
        comparadores.add(ascendente);
        comparadores.add(descendente);
        
        HeapManager<Integer> manager = new HeapManager<>(comparadores);
        manager.agregar(5);
        manager.agregar(3);
        manager.agregar(7);
        
        // Sacar un elemento del primer heap
        Integer elementoSacado = manager.sacar(0);
        
        // Verificar que el elemento ya no está en ningún heap
        assertEquals(2, manager.size());
    }

    @Test
    public void testOperacionesCombinadas() {
        ArrayList<Comparator<Integer>> comparadores = new ArrayList<>();
        comparadores.add(ascendente);
        comparadores.add(descendente);
        
        HeapManager<Integer> manager = new HeapManager<>(comparadores);
        
        // Agregar elementos
        manager.agregar(5);
        manager.agregar(3);
        manager.agregar(7);
        // agrego friccion

        // Sacar un elemento
        manager.sacar(0);
        manager.agregar(7);
        manager.agregar(9);
        manager.agregar(5);
        manager.agregar(1);

        assertEquals(6, manager.size());
    }

    @Test
    public void testIndicesInvalidos() {
        ArrayList<Comparator<Integer>> comparadores = new ArrayList<>();
        comparadores.add(ascendente);
        comparadores.add(descendente);
        
        HeapManager<Integer> manager = new HeapManager<>(comparadores);
        
        // No debería lanzar excepción
        manager.eliminar(10, 0); // índice de objeto inválido
        manager.eliminar(0, 10); // índice de heap inválido
        
        assertEquals(0, manager.size());
    }

    @Test
    public void testMultiplesHeaps() {
        ArrayList<Comparator<Integer>> comparadores = new ArrayList<>();
        comparadores.add(ascendente);
        comparadores.add(descendente);
        comparadores.add((a, b) -> Integer.compare(Math.abs(a), Math.abs(b)));
        
        HeapManager<Integer> manager = new HeapManager<>(comparadores);
        manager.agregar(-5);
        manager.agregar(3);
        manager.agregar(-7);
        
        assertEquals(3, manager.size());
    }
}
