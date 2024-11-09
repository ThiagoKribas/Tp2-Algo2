public package aed;

class Comparadores {

    public menorId(int value, T obj1, T obj2){

        // Checkeamos si es la menor id
        if (value == 0){
            value = int.compare(obj2.id, obj1.id);
        }
        return value;
    }

    // Comparacion dinamica de atributo Traslado.timestamp

    static class MasAntiguo implements Comparator<Traslado>{

        public int compare(T t1, T t2){
            int value = int.compare(t2.timestamp, t1.timestamp);
            value = menorId(value, t2, t1);
            return value;
        }
    }

    // Comparacion dinamica de atributo Traslado.gananciaNeta

    static class MasRedituable implements Comparator<Traslado>{

        public int compare(T t1, T t2){
            int value = int.compare(t1.gananciaNeta, t2.gananciaNeta);
            value = menorId(t1, t2);
            return value;
        }
    }

    // Comparacion dinamica de atributo Ciudad.superavit

    static class MasSuperHabit implements Comparator<Ciudad>{
        
        public int compare(T c1, T c2){
            int value = int.compare(c1.superavit, c2.superavit);
            value = menorId(c1, c2);
            return value;
        }    
    }
}
