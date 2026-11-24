import java.util.ArrayList;

// Classe de gerenciamento: guarda as listas e concentra as funcionalidades
public class Sistema {

    private ArrayList<Veiculo> veiculos = new ArrayList<>();
    private ArrayList<Aluguel> alugueis = new ArrayList<>();

    public void cadastrarVeiculo(Veiculo v) { veiculos.add(v); }
    public void cadastrarAluguel(Aluguel a) { alugueis.add(a); }

    public Veiculo buscarVeiculoPorPlaca(String placa) {
        for (Veiculo v : veiculos) {
            if (v.getPlaca().equalsIgnoreCase(placa)) {
                return v;
            }
        }
        return null;
    }

    public Aluguel buscarAluguelPorCodigo(int codigo) {
        for (Aluguel a : alugueis) {
            if (a.getCodigo() == codigo) {
                return a;
            }
        }
        return null;
    }

    // 1. Associar veiculo a aluguel
    public void associarVeiculoAoAluguel(int codigo, String placa) {
        Aluguel a = buscarAluguelPorCodigo(codigo);
        Veiculo v = buscarVeiculoPorPlaca(placa);
        if (a == null) {
            System.out.println("Aluguel nao encontrado.");
        } else if (v == null) {
            System.out.println("Veiculo nao encontrado.");
        } else if (a.adicionarVeiculo(v)) {
            System.out.println("Veiculo " + placa + " associado ao aluguel #" + codigo + ".");
        } else {
            System.out.println("Veiculo indisponivel (status: " + v.getStatus() + ").");
        }
    }

    // 2. Exibir todos os veiculos
    public void exibirVeiculos() {
        if (veiculos.isEmpty()) {
            System.out.println("Nenhum veiculo cadastrado.");
        }
        for (Veiculo v : veiculos) {
            System.out.println(v);
        }
    }

    // 3. Exibir todos os alugueis
    public void exibirAlugueis() {
        if (alugueis.isEmpty()) {
            System.out.println("Nenhum aluguel cadastrado.");
        }
        for (Aluguel a : alugueis) {
            System.out.println(a);
            System.out.println();
        }
    }

    // 4. Exibir apenas veiculos disponiveis
    public void exibirDisponiveis() {
        boolean achou = false;
        for (Veiculo v : veiculos) {
            if (v.getStatus() == StatusVeiculo.DISPONIVEL) {
                System.out.println(v);
                achou = true;
            }
        }
        if (!achou) {
            System.out.println("Nenhum veiculo disponivel.");
        }
    }

    // 5. Avaliar: monta uma lista com tudo que e Avaliavel (veiculos E alugueis)
    public ArrayList<Avaliavel> getAvaliaveis() {
        ArrayList<Avaliavel> lista = new ArrayList<>();
        for (Veiculo v : veiculos) {
            if (v instanceof Avaliavel) {          // instanceof: verifica se o objeto implementa a interface
                lista.add((Avaliavel) v);          // cast: trata o Veiculo como Avaliavel
            }
        }
        for (Aluguel a : alugueis) {
            if (a instanceof Avaliavel) {
                lista.add((Avaliavel) a);
            }
        }
        return lista;
    }

    // 6. Inspecionaveis: apenas veiculos que implementam Inspecionavel
    public ArrayList<Veiculo> getInspecionaveis() {
        ArrayList<Veiculo> lista = new ArrayList<>();
        for (Veiculo v : veiculos) {
            if (v instanceof Inspecionavel) {
                lista.add(v);
            }
        }
        return lista;
    }

    // 7. Remover veiculos em manutencao (tambem tira de qualquer aluguel)
    public void removerEmManutencao() {
        int removidos = 0;
        // Percorre de tras para frente para poder remover sem pular elementos
        for (int i = veiculos.size() - 1; i >= 0; i--) {
            Veiculo v = veiculos.get(i);
            if (v.getStatus() == StatusVeiculo.MANUTENCAO) {
                for (Aluguel a : alugueis) {
                    a.getVeiculos().remove(v);
                }
                veiculos.remove(i);
                removidos++;
            }
        }
        System.out.println(removidos + " veiculo(s) em manutencao removido(s).");
    }
}
