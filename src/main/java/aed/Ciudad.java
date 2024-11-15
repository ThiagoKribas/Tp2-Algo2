package aed;

public class Ciudad {

    int id;
    int ganancia;
    int perdida;
    int superavit;

    public Ciudad(int id){
        this.id = id;
    }
    // Operaciones simples -> O(1) 
    public void modificar(int ganancia, int perdida){
        this.ganancia += ganancia;
        this.perdida += perdida;
        this.superavit = this.ganancia - this.perdida; 
    }
    
}