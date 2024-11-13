package aed;

import java.util.ArrayList;

public class Nodo<T> {
    T objeto;
        ArrayList<Integer> indices;

        public Nodo(T objeto){
            this.objeto = objeto;
            this.indices = new ArrayList<>();
        }
}
