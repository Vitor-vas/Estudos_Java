// Classe abstrata: define o que todo veiculo tem, mas nao pode ser instanciada diretamente
public abstract class Veiculo {

    // Atributos privados (encapsulamento)
    private String placa;
    private String modelo;
    private int anoFabricacao;
    private double valorDiaria;
    private double quilometragem;
    private StatusVeiculo status;

    public Veiculo(String placa, String modelo, int anoFabricacao, double valorDiaria, double quilometragem) {
        this.placa = placa;
        this.modelo = modelo;
        this.anoFabricacao = anoFabricacao;
        this.valorDiaria = valorDiaria;
        this.quilometragem = quilometragem;
        this.status = StatusVeiculo.DISPONIVEL; // todo veiculo novo comeca disponivel
    }

    // Getters
    public String getPlaca() { return placa; }
    public String getModelo() { return modelo; }
    public int getAnoFabricacao() { return anoFabricacao; }
    public double getValorDiaria() { return valorDiaria; }
    public double getQuilometragem() { return quilometragem; }
    public StatusVeiculo getStatus() { return status; }

    // Setter apenas do status (os outros dados nao mudam depois de criados)
    public void setStatus(StatusVeiculo status) {
        this.status = status;
    }

    // Metodo abstrato: cada subclasse informa o seu tipo
    public abstract String getTipo();

    // toString sobrescrito: cada subclasse pode chamar super.toString() e complementar
    @Override
    public String toString() {
        return getTipo() + " | Placa: " + placa
                + " | Modelo: " + modelo
                + " | Ano: " + anoFabricacao
                + " | Diaria: R$ " + String.format("%.2f", valorDiaria)
                + " | Km: " + quilometragem
                + " | Status: " + status;
    }
}
