import java.util.ArrayList;

// Classe abstrata: um aluguel possui varios veiculos (associacao 1 para N)
public abstract class Aluguel {

    private int codigo;
    private String cliente;
    private int dias;
    private ArrayList<Veiculo> veiculos = new ArrayList<>();

    public Aluguel(int codigo, String cliente, int dias) {
        this.codigo = codigo;
        this.cliente = cliente;
        this.dias = dias;
    }

    public int getCodigo() { return codigo; }
    public String getCliente() { return cliente; }
    public int getDias() { return dias; }
    public ArrayList<Veiculo> getVeiculos() { return veiculos; }

    // Associa um veiculo ao aluguel, mas so se ele estiver disponivel
    public boolean adicionarVeiculo(Veiculo v) {
        if (v.getStatus() != StatusVeiculo.DISPONIVEL) {
            return false;
        }
        veiculos.add(v);
        v.setStatus(StatusVeiculo.ALUGADO);
        return true;
    }

    // Metodos abstratos: cada tipo de aluguel define suas proprias regras
    public abstract String getTipo();
    public abstract String getRegraKm();
    public abstract boolean temSeguro();

    // Calcula o valor total: soma das diarias de todos os veiculos x dias
    public double calcularValorTotal() {
        double total = 0;
        for (Veiculo v : veiculos) {
            total += v.getValorDiaria() * dias;
        }
        return total;
    }

    @Override
    public String toString() {
        String texto = "Aluguel #" + codigo + " [" + getTipo() + "]"
                + " | Cliente: " + cliente
                + " | Dias: " + dias
                + " | Km: " + getRegraKm()
                + " | Seguro: " + (temSeguro() ? "Sim" : "Nao")
                + " | Total: R$ " + String.format("%.2f", calcularValorTotal());
        texto += "\n   Veiculos:";
        if (veiculos.isEmpty()) {
            texto += " nenhum";
        }
        for (Veiculo v : veiculos) {
            texto += "\n   - " + v.getTipo() + " " + v.getPlaca() + " (" + v.getModelo() + ")";
        }
        return texto;
    }
}
