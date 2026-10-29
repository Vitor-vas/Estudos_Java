abstract class Equipamento {
    protected String codigo;
    protected String descricao;
    protected Estado estado;

    protected abstract void operar();

    public boolean podeOperar(Estado estado){
        if (estado == Estado.DISPONIVEL) {
            return true;
        }
        return false;
    }
}

public class 
