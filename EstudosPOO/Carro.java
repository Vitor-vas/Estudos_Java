// Carro: tem portas e pode ser avaliado (NAO e inspecionavel)
public class Carro extends Veiculo implements Avaliavel {

    private int quantidadePortas;
    private int somaNotas = 0;
    private int totalAvaliacoes = 0;

    public Carro(String placa, String modelo, int ano, double diaria, double km, int quantidadePortas) {
        super(placa, modelo, ano, diaria, km); // chama o construtor da classe pai
        this.quantidadePortas = quantidadePortas;
    }

    @Override
    public String getTipo() {
        return "Carro";
    }

    @Override
    public void avaliar(int nota) {
        if (nota < 1 || nota > 5) {
            System.out.println("Nota invalida! Use de 1 a 5.");
        } else {
            somaNotas += nota;
            totalAvaliacoes++;
            System.out.println("Carro avaliado com nota " + nota + ".");
        }
    }

    public double getMediaAvaliacoes() {
        if (totalAvaliacoes == 0) {
            return 0;
        }
        return (double) somaNotas / totalAvaliacoes;
    }

    @Override
    public String toString() {
        return super.toString() + " | Portas: " + quantidadePortas
                + " | Media: " + String.format("%.1f", getMediaAvaliacoes());
    }
}
