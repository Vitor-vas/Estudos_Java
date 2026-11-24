// Executivo: km ilimitada, motorista particular, pode ser avaliado
public class AluguelExecutivo extends Aluguel implements Avaliavel {

    private int somaNotas = 0;
    private int totalAvaliacoes = 0;

    public AluguelExecutivo(int codigo, String cliente, int dias) {
        super(codigo, cliente, dias);
    }

    @Override
    public String getTipo() { return "Executivo (com motorista)"; }

    @Override
    public String getRegraKm() { return "ilimitada"; }

    // Executivo nao inclui seguro, conforme o enunciado (so Premium tem)
    @Override
    public boolean temSeguro() { return false; }

    @Override
    public void avaliar(int nota) {
        if (nota < 1 || nota > 5) {
            System.out.println("Nota invalida! Use de 1 a 5.");
        } else {
            somaNotas += nota;
            totalAvaliacoes++;
            System.out.println("Aluguel #" + getCodigo() + " avaliado com nota " + nota + ".");
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
        return super.toString() + "\n   Media de avaliacoes: " + String.format("%.1f", getMediaAvaliacoes());
    }
}
