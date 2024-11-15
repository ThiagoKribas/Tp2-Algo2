package aed;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Comparator;

public class HeapManagerTest {

    private static final Comparator<Integer> ascendente = Integer::compareTo;   // Min heap
    private static final Comparator<Integer> descendente = (a, b) -> b.compareTo(a);  // Max heap

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

        ArrayList<Integer> primeros = manager.verPrimero();
        assertEquals(Integer.valueOf(3), primeros.get(1));  // Min heap
        assertEquals(Integer.valueOf(7), primeros.get(0));  // Max heap
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

        ArrayList<Integer> primeros = manager.verPrimero();
        assertEquals(Integer.valueOf(3), primeros.get(1));  // Min heap
        assertEquals(Integer.valueOf(7), primeros.get(0));  // Max heap
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
        manager.eliminar(0, 1);  // Eliminar el primer elemento del min heap (3)
        assertEquals(tamañoInicial - 1, manager.size());

        ArrayList<Integer> primeros = manager.verPrimero();
        //debug
        System.out.println(primeros);
        assertEquals(Integer.valueOf(5), primeros.get(1));  // Nuevo min heap
        assertEquals(Integer.valueOf(7), primeros.get(0));  // Max heap sigue siendo 7
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

        // Sacar un elemento del min heap
        Integer elementoSacado = manager.sacar(1);  // Debería ser 3
        assertEquals(Integer.valueOf(3), elementoSacado);

        // Verificar que el elemento ya no está en ningún heap
        ArrayList<Integer> primeros = manager.verPrimero();
        assertFalse(primeros.contains(3), "El elemento eliminado no debería estar presente en ningún heap.");
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

        // Sacar un elemento del min heap
        Integer sacado1 = manager.sacar(1);  // Debería ser 3
        assertEquals(Integer.valueOf(3), sacado1);
        assertEquals(2, manager.size());

        // Agregar más elementos
        manager.agregar(7);
        manager.agregar(9);
        manager.agregar(5);
        manager.agregar(1);

        assertEquals(6, manager.size());

        ArrayList<Integer> primeros = manager.verPrimero();
        assertEquals(Integer.valueOf(1), primeros.get(1));  // Nuevo min heap
        assertEquals(Integer.valueOf(9), primeros.get(0));  // Max heap
    }

    @Test
    public void testIndicesInvalidos() {
        ArrayList<Comparator<Integer>> comparadores = new ArrayList<>();
        comparadores.add(ascendente);
        comparadores.add(descendente);

        HeapManager<Integer> manager = new HeapManager<>(comparadores);

        // No debería lanzar excepción
        manager.eliminar(10, 0); // Índice de heap inválido
        manager.eliminar(0, 10); // Índice de objeto inválido

        assertEquals(0, manager.size());
    }

    @Test
    public void testMultiplesHeaps() {
        ArrayList<Comparator<Integer>> comparadores = new ArrayList<>();
        comparadores.add(ascendente);  // Max heap
        comparadores.add(descendente); // Min heap
        comparadores.add((a, b) -> Integer.compare(Math.abs(a), Math.abs(b))); // Abs heap

        HeapManager<Integer> manager = new HeapManager<>(comparadores);
        manager.agregar(-5);
        manager.agregar(3);
        manager.agregar(-7);

        ArrayList<Integer> primeros = manager.verPrimero();
        assertEquals(Integer.valueOf(-7), primeros.get(1));  // Min heap debería ser -7
        assertEquals(Integer.valueOf(3), primeros.get(0));   // Max heap debería ser 3
        assertEquals(Integer.valueOf(-7), primeros.get(2));  // Abs heap debería ser -7 (|3| < |-5| < |-7|)

        assertEquals(3, manager.size());
    }
}
//AGREGAR TEST DE SACAR xq no esta funcionando