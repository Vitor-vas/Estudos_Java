// Moto: tem cilindrada e pode ser inspecionada (NAO recebe avaliacao)
public class Moto extends Veiculo implements Inspecionavel {

    private int cilindrada;

    public Moto(String placa, String modelo, int ano, double diaria, double km, int cilindrada) {
        super(placa, modelo, ano, diaria, km);
        this.cilindrada = cilindrada;
    }

    @Override
    public String getTipo() {
        return "Moto";
    }

    // Regra adotada: ao ser inspecionado, o veiculo vai para MANUTENCAO
    @Override
    public void realizarInspecao() {
        setStatus(StatusVeiculo.MANUTENCAO);
        System.out.println("Inspecao realizada na moto " + getPlaca() + ". Status alterado para MANUTENCAO.");
    }

    @Override
    public String toString() {
        return super.toString() + " | Cilindrada: " + cilindrada + "cc";
    }
}
