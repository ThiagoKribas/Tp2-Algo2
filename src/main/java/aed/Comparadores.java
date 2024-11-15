package aed;

import java.util.Comparator;

class Comparadores {

    static int menorId(int value, Traslado obj1, Traslado obj2){

        // Checkeamos si es la menor id
        if (value == 0){
            value = Integer.compare(obj2.id, obj1.id);
        }
        return value;
    }

    static int menorId(int value, Ciudad obj1, Ciudad obj2){

        // Checkeamos si es la menor id
        if (value == 0){
            value = Integer.compare(obj2.id, obj1.id);
        }
        return value;
    }

    // Comparacion dinamica de atributo Traslado.timestamp
    static class MasAntiguo implements Comparator<Traslado>{

        public int compare(Traslado t1, Traslado t2){
            int value = Integer.compare(t2.timestamp, t1.timestamp);
            value = menorId(value, t2, t1);
            return value;
        }
    }

    // Comparacion dinamica de atributo Traslado.gananciaNeta
    static class MasRedituable implements Comparator<Traslado>{

        public int compare(Traslado t1, Traslado t2){
            int value = Integer.compare(t1.gananciaNeta, t2.gananciaNeta);
            value = menorId(value, t1, t2);
            return value;
        }
    }

    // Comparacion dinamica de atributo Ciudad.superavit
    static class MasSuperHabit implements Comparator<Ciudad>{
        
        public int compare(Ciudad c1, Ciudad c2){
            int value = Integer.compare(c1.superavit, c2.superavit);
            value = menorId(value, c1, c2);
            return value;
        }    
    }
}
