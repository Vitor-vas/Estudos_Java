import enums.Categoria;
import enums.StatusPedido;
import exceptions.ValorInvalidoException;
import interfaces.Processavel;

public class PedidoAlimento extends Pedido {
    private double desconto;
    private double frete;
    public PedidoAlimento(int id, String nomeCliente, double valor, Categoria categoria) throws ValorInvalidoException {
        super(id, nomeCliente, valor, categoria);
        if (valor>=100) {
            desconto = 0.1;
            frete = 20+(0.1*valor);
        }
    }
    @Override
    public double calcularValorFinal() throws ValorInvalidoException {
        // TODO Auto-generated method stub
        return 0;
    }
}
