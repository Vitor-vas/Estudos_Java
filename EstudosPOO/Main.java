import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static Scanner sc = new Scanner(System.in);
    static Sistema sistema = new Sistema();

    public static void main(String[] args) {
        carregarDadosIniciais();

        int opcao;
        do {
            exibirMenu();
            opcao = lerInt("Escolha: ");
            System.out.println();

            switch (opcao) {
                case 1: associar(); break;
                case 2: sistema.exibirVeiculos(); break;
                case 3: sistema.exibirAlugueis(); break;
                case 4: sistema.exibirDisponiveis(); break;
                case 5: avaliar(); break;
                case 6: inspecionar(); break;
                case 7: sistema.removerEmManutencao(); break;
                case 0: System.out.println("Encerrando..."); break;
                default: System.out.println("Opcao invalida!");
            }
        } while (opcao != 0);
    }

    // Cria 3 veiculos e 2 alugueis assim que o programa inicia
    static void carregarDadosIniciais() {
        sistema.cadastrarVeiculo(new Carro("ABC-1234", "Onix", 2022, 150.0, 15000, 4));
        sistema.cadastrarVeiculo(new Moto("MOT-5678", "CG 160", 2021, 80.0, 8000, 160));
        sistema.cadastrarVeiculo(new Van("VAN-9012", "Sprinter", 2020, 300.0, 40000, 15));

        sistema.cadastrarAluguel(new AluguelBasico(1, "Maria Silva", 3));
        sistema.cadastrarAluguel(new AluguelExecutivo(2, "Joao Souza", 5));
    }

    static void exibirMenu() {
        System.out.println("\n===== LOCADORA DE VEICULOS =====");
        System.out.println("1 - Associar veiculo a um aluguel");
        System.out.println("2 - Exibir todos os veiculos");
        System.out.println("3 - Exibir todos os alugueis");
        System.out.println("4 - Exibir veiculos disponiveis");
        System.out.println("5 - Avaliar (carro, van ou aluguel executivo)");
        System.out.println("6 - Realizar inspecao (moto ou van)");
        System.out.println("7 - Remover veiculos em manutencao");
        System.out.println("0 - Sair");
    }

    // Le um inteiro com tratamento de erro (try/catch)
    static int lerInt(String mensagem) {
        System.out.print(mensagem);
        try {
            return Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    static void associar() {
        sistema.exibirAlugueis();
        int codigo = lerInt("Codigo do aluguel: ");
        sistema.exibirDisponiveis();
        System.out.print("Placa do veiculo: ");
        String placa = sc.nextLine().trim();
        sistema.associarVeiculoAoAluguel(codigo, placa);
    }

    static void avaliar() {
        ArrayList<Avaliavel> lista = sistema.getAvaliaveis();
        if (lista.isEmpty()) {
            System.out.println("Nada para avaliar.");
            return;
        }
        for (int i = 0; i < lista.size(); i++) {
            System.out.println((i + 1) + " - " + lista.get(i));
        }
        int escolha = lerInt("Numero do item: ");
        if (escolha < 1 || escolha > lista.size()) {
            System.out.println("Item invalido.");
            return;
        }
        int nota = lerInt("Nota (1 a 5): ");
        lista.get(escolha - 1).avaliar(nota); // polimorfismo: cada classe avalia do seu jeito
    }

    static void inspecionar() {
        ArrayList<Veiculo> lista = sistema.getInspecionaveis();
        if (lista.isEmpty()) {
            System.out.println("Nenhum veiculo inspecionavel.");
            return;
        }
        for (int i = 0; i < lista.size(); i++) {
            System.out.println((i + 1) + " - " + lista.get(i));
        }
        int escolha = lerInt("Numero do veiculo: ");
        if (escolha < 1 || escolha > lista.size()) {
            System.out.println("Veiculo invalido.");
            return;
        }
        Inspecionavel item = (Inspecionavel) lista.get(escolha - 1);
        item.realizarInspecao();
    }
}
