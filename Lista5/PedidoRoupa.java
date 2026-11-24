import enums.Categoria;
import exceptions.ValorInvalidoException;
import interfaces.Entregavel;

public class PedidoRoupa extends Pedido implements Entregavel{
        private double desconto;
    private double frete;
    public PedidoRoupa(int id, String nomeCliente, double valor, Categoria categoria) throws ValorInvalidoException {
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
    @Override
    public double calcularFrete() {
    // TODO Auto-generated method stub
    return 0;
    }
}
