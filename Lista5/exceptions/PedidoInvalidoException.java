package exceptions;

public class PedidoInvalidoException extends Exception{
    public PedidoInvalidoException(String errorMessage){
            super(errorMessage);
    }
}
