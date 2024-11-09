package aed;

public class Ciudad {

    int id;
    int ganancia;
    int perdida;
    int superavit;

    public Ciudad(int id){
        this.id = id;
    }

    public void modificar(int ganancia, int perdida){
        this.ganancia += ganancia;
        this.perdida += perdida;
        this.superavit = ganancia - perdida; 
    }
    
}