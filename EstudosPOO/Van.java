// Van: tem maximo de passageiros, pode ser avaliada E inspecionada
// (implementa duas interfaces ao mesmo tempo)
public class Van extends Veiculo implements Avaliavel, Inspecionavel {

    private int maxPassageiros;
    private int somaNotas = 0;
    private int totalAvaliacoes = 0;

    public Van(String placa, String modelo, int ano, double diaria, double km, int maxPassageiros) {
        super(placa, modelo, ano, diaria, km);
        this.maxPassageiros = maxPassageiros;
    }

    @Override
    public String getTipo() {
        return "Van";
    }

    @Override
    public void avaliar(int nota) {
        if (nota < 1 || nota > 5) {
            System.out.println("Nota invalida! Use de 1 a 5.");
        } else {
            somaNotas += nota;
            totalAvaliacoes++;
            System.out.println("Van avaliada com nota " + nota + ".");
        }
    }

    public double getMediaAvaliacoes() {
        if (totalAvaliacoes == 0) {
            return 0;
        }
        return (double) somaNotas / totalAvaliacoes;
    }

    @Override
    public void realizarInspecao() {
        setStatus(StatusVeiculo.MANUTENCAO);
        System.out.println("Inspecao realizada na van " + getPlaca() + ". Status alterado para MANUTENCAO.");
    }

    @Override
    public String toString() {
        return super.toString() + " | Max passageiros: " + maxPassageiros
                + " | Media: " + String.format("%.1f", getMediaAvaliacoes());
    }
}
