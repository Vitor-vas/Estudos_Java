// Premium: km ilimitada, com seguro
public class AluguelPremium extends Aluguel {

    public AluguelPremium(int codigo, String cliente, int dias) {
        super(codigo, cliente, dias);
    }

    @Override
    public String getTipo() { return "Premium"; }

    @Override
    public String getRegraKm() { return "ilimitada"; }

    @Override
    public boolean temSeguro() { return true; }
}
