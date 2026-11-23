import enums.Categoria;
import enums.StatusPedido;
import exceptions.PedidoInvalidoException;
import exceptions.ValorInvalidoException;
import interfaces.Processavel;

public abstract class Pedido implements Processavel{
    private int id;
    private String nomeCliente;
    private double valor;
    private StatusPedido status;
    private Categoria categoria;

    public Pedido(int id, String nomeCliente, double valor, Categoria categoria) throws ValorInvalidoException{
        if(valor <= 0){
            throw new ValorInvalidoException("Valor menor ou igual a 0 e invalido");
        }
        this.id = id;
        this.nomeCliente = nomeCliente;
        this.valor = valor;
        this.status = StatusPedido.PENDENTE;
        this.categoria = categoria;
    }
    @Override 
    public void processarPedido() throws PedidoInvalidoException{
        if(status == StatusPedido.CANCELADO){
            throw new PedidoInvalidoException("Impossivel processar pedido cancelado");
        }
        if(status == StatusPedido.ENTREGUE){
            throw new PedidoInvalidoException("Impossivel processar pedido entregue");            
        }
        setStatus(StatusPedido.PROCESSANDO);
    }
    public Categoria getCategoria() {
        return categoria;
    }
    public int getId() {
        return id;
    }
    public String getNomeCliente() {
        return nomeCliente;
    }
    public StatusPedido getStatus() {
        return status;
    }
    public double getValor() {
        return valor;
    }
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }
    public void setStatus(StatusPedido status) {
        this.status = status;
    }
    public void setValor(double valor) {  
        this.valor = valor;
    }
    
}
