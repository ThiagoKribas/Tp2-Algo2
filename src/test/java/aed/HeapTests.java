package aed;

import java.util.ArrayList;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class HeapTests {
    private Heap<Integer> heap;

    @BeforeEach
    void setUp() {
        Comparator<Integer> comp = Integer::compareTo;
        ArrayList<Integer> data = new ArrayList<>();
        heap = new Heap<>(comp, data);
        heap.agregar(3);
        heap.agregar(1);
        heap.agregar(6);
        heap.agregar(5);
        heap.agregar(2);
        heap.agregar(4);
    }

    @Test
    void testHeapify() {
        assertTrue(heap.obtenerIndices(0) >= heap.obtenerIndices(1) && heap.obtenerIndices(0) >= heap.obtenerIndices(2));
    }

    @Test
    void testPosiciones(){
        assertEquals(6, heap.obtenerIndices(0));
        assertEquals(5, heap.obtenerIndices(1));
        assertEquals(4, heap.obtenerIndices(2));
        assertEquals(1, heap.obtenerIndices(3));
        assertEquals(2, heap.obtenerIndices(4));
        assertEquals(3, heap.obtenerIndices(5));
    }

    @Test
    void testEncolar() {
        heap.agregar(10);
        assertEquals(10, heap.agregar(0));
        heap.agregar(9);
        assertEquals(9, heap.agregar(1));
        heap.agregar(8);
        assertEquals(8, heap.agregar(3));
        heap.agregar(5);    
        assertEquals(5, heap.agregar(8));

    }

    @Test
    void testDesencolarPos() {
        int elementoEliminado = heap.desencolarPos(0);
        assertEquals(6, elementoEliminado);
        assertEquals(5, heap.acceder(0));
        assertEquals(5, heap.longitud()); 
        elementoEliminado = heap.desencolarPos(0);
        assertEquals(5, elementoEliminado);
        assertEquals(2, heap.acceder(2));
        assertEquals(4, heap.longitud()); 
    }

    @Test
    void testGeneral(){
        heap.encolar(10);
        heap.encolar(9);
        assertEquals(2, heap.acceder(4));
        assertEquals(8, heap.longitud());
        int elementoEliminado = heap.desencolarPos(2);
        assertEquals(6, elementoEliminado);
        elementoEliminado = heap.desencolarPos(4);
        assertEquals(2, elementoEliminado);
        assertEquals(3, heap.acceder((heap.longitud())-1));
    }

    @Test
    void testTieneHijos() {
        assertTrue(heap.tieneHijos(0));
        assertFalse(heap.tieneHijos(heap.longitud() - 1));
    }

    @Test
    void testTienePadre() {
        assertFalse(heap.tienePadre(0));
        assertTrue(heap.tienePadre(1)); 
        assertTrue(heap.tienePadre(3)); 
        assertTrue(heap.tienePadre(5)); 
        assertTrue(heap.tienePadre(4)); 
    }

    @Test
    void testPosicionesHijosYPadre() {
        assertEquals(1, heap.posHijoIzq(0));
        assertEquals(2, heap.posHijoDer(0));
        assertEquals(0, heap.posPadre(1));
        assertEquals(3, heap.posHijoIzq(1));
        assertEquals(4, heap.posHijoDer(1));
        assertEquals(1, heap.posPadre(4));
        assertEquals(1, heap.posMayorHijo(0));
        assertEquals(4, heap.posMayorHijo(1));
    }

}
