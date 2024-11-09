package aed;

public class Ciudad {
    
    int id;
    int ganancia;
    int perdida;
    int superavit;

    public Ciudad(id){
        this.id = id;
    }

    public modificar(ganancia, perdida){
        this.ganancia += ganancia;
        this.perdida += perdida;
        this.superavit = ganancia - perdida; 
    }
    
}