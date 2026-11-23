package interfaces;

import exceptions.PedidoInvalidoException;
import exceptions.ValorInvalidoException;

public interface Processavel {
    void processarPedido() throws PedidoInvalidoException;

    double calcularValorFinal() throws ValorInvalidoException;
}
