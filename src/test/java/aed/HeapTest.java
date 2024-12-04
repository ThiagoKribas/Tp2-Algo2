package aed;


import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;

public class HeapTest {
    
    // Comparador para enteros (max heap)
    private static final Comparator<Integer> maxComparator = Integer::compareTo;
    
    @Test
    public void testConstructorVacio() {
        Heap<Integer> heap = new Heap<>(maxComparator);
        assertEquals(0, heap.size());
    }
    
    @Test
    public void testAgregar() {
        Heap<Integer> heap = new Heap<>(maxComparator);
        heap.agregar(5);
        heap.agregar(3);
        heap.agregar(7);
        
        assertEquals(3, heap.size());
        assertEquals(Integer.valueOf(7), heap.obtenerPrimero());
    }
    
    @Test
    public void testArrayToHeap() {
        Heap<Integer> heap = new Heap<>(maxComparator);
        ArrayList<Nodo<Integer>> Lista = new ArrayList<Nodo<Integer>>();
        Lista.add(new Nodo<Integer>(1));
        Lista.add(new Nodo<Integer>(2)); 
        Lista.add(new Nodo<Integer>(3));
        Lista.add(new Nodo<Integer>(4));
        Lista.add(new Nodo<Integer>(5));
        
        heap.ArrayToHeap(Lista);
        assertEquals(5, heap.size());
        assertEquals(Integer.valueOf(5), heap.obtenerPrimero());
    }

    @Test
    public void testConstructorArray() {
        ArrayList<Nodo<Integer>> Lista = new ArrayList<Nodo<Integer>>();
        Lista.add(new Nodo<Integer>(1));
        Lista.add(new Nodo<Integer>(3)); 
        Lista.add(new Nodo<Integer>(5));
        Lista.add(new Nodo<Integer>(7));
        Lista.add(new Nodo<Integer>(9));
        Heap<Integer> heap = new Heap<>(maxComparator, Lista, 0);
        
        assertEquals(5, heap.size());
        assertEquals(Integer.valueOf(9), heap.obtenerPrimero());
    }

    @Test
    public void testSacarPrimero() {
        Heap<Integer> heap = new Heap<>(maxComparator);
        heap.agregar(5);
        heap.agregar(3);
        heap.agregar(7);
        
        assertEquals(Integer.valueOf(7), heap.sacarPrimero());
        assertEquals(Integer.valueOf(5), heap.obtenerPrimero());
        assertEquals(2, heap.size());
    }
    
    @Test
    public void testModificar() {
        Heap<Integer> heap = new Heap<>(maxComparator);
        heap.agregar(5);
        heap.agregar(3);
        heap.agregar(7);
        
        heap.modificar(1, 10); // Modificamos el 3 a 10
        assertEquals(Integer.valueOf(10), heap.obtenerPrimero());
    }
    
    @Test
    public void testHeapifyUpDown() {
        Heap<Integer> heap = new Heap<>(maxComparator);
        // Agregamos elementos para probar el heapify
        heap.agregar(1);
        heap.agregar(2);
        heap.agregar(3);
        heap.agregar(4);
        heap.agregar(5);
        
        assertEquals(Integer.valueOf(5), heap.obtenerPrimero());
        
        // Probamos eliminar el máximo
        heap.eliminar(0);
        assertEquals(Integer.valueOf(4), heap.obtenerPrimero());
    }
    
    @Test
    public void testConStringComparator() {
        Comparator<String> strComparator = String::compareTo;
        Heap<String> heap = new Heap<>(strComparator);
        
        heap.agregar("banana");
        heap.agregar("apple");
        heap.agregar("cherry");
        
        assertEquals("cherry", heap.obtenerPrimero());
        assertEquals(3, heap.size());
    }
    
    @Test
    public void testMultiplesOperaciones() {
        Heap<Integer> heap = new Heap<>(maxComparator);
        
        // Agregamos varios elementos
        heap.agregar(15);
        heap.agregar(10);
        heap.agregar(20);
        heap.agregar(17);
        heap.agregar(25);
        
        assertEquals(Integer.valueOf(25), heap.obtenerPrimero());
        
        // Eliminamos el máximo
        heap.eliminar(0);
        assertEquals(Integer.valueOf(20), heap.obtenerPrimero());
        
        // Modificamos un valor
        heap.modificar(1, 30);
        assertEquals(Integer.valueOf(30), heap.obtenerPrimero());
        
        // Verificamos el tamaño
        assertEquals(4, heap.size());
    }

    @Test
    public void testCasosBorde() {
        Heap<Integer> heap = new Heap<>(maxComparator);
        
        // Pruebo agregar y eliminar muchos elementos verificando el orden
        for (int i = 1; i <= 1000; i++) {
            heap.agregar(i);
        }
        assertEquals(1000, heap.size());
        assertEquals(Integer.valueOf(1000), heap.obtenerPrimero());
        
        for (int i = 1000; i >= 1; i--) {
            assertEquals(Integer.valueOf(i), heap.sacarPrimero());
        }
        assertEquals(0, heap.size());
    }

    @Test
    public void testModificacionesMasivas() {
        Heap<Integer> heap = new Heap<>(maxComparator);
        
        // Lleno el heap con valores y luego los modifico
        for (int i = 0; i < 100; i++) {
            heap.agregar(i);
        }
        
        // Modifico todos los valores multiplicándolos por 2
        for (int i = 0; i < heap.size(); i++) {
            heap.modificar(i, i * 2);
        }
        
        assertEquals(Integer.valueOf(198), heap.obtenerPrimero());
    }

    @Test
    public void testStringGrande() {
        Heap<String> heap = new Heap<>(String::compareTo);
        
        // Pruebo con strings de diferentes longitudes
        String[] palabras = {"z", "zz", "zzz", "a", "aa", "aaa"};
        for (String palabra : palabras) {
            heap.agregar(palabra);
        }
        
        assertEquals("zzz", heap.obtenerPrimero());
        assertEquals(6, heap.size());
    }

}
