// Basico: ate 100 km por dia, sem seguro
public class AluguelBasico extends Aluguel {

    private static final int LIMITE_KM_DIA = 100;

    public AluguelBasico(int codigo, String cliente, int dias) {
        super(codigo, cliente, dias);
    }

    @Override
    public String getTipo() { return "Basico"; }

    @Override
    public String getRegraKm() {
        return "ate " + LIMITE_KM_DIA + " km/dia (" + (LIMITE_KM_DIA * getDias()) + " km no total)";
    }

    @Override
    public boolean temSeguro() { return false; }
}
