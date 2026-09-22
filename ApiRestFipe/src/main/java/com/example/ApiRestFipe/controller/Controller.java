package com.example.ApiRestFipe.controller;
// Declara o pacote onde essa classe vive. Segue a convenção de
// organizar o projeto em camadas: "controller" é a camada responsável
// por receber requisições HTTP.

import org.springframework.web.bind.annotation.GetMapping;
// Anotação que mapeia um método Java para responder a requisições HTTP GET
// em uma rota específica (ex: "/marcas").

import org.springframework.web.bind.annotation.PathVariable;
// Anotação que permite capturar valores presentes na própria URL
// (ex: o "5" em "/modelos/5") e injetá-los como parâmetro do método.

import org.springframework.web.bind.annotation.RestController;
// Anotação que marca essa classe como um controller REST: o Spring já
// entende que os retornos dos métodos devem ir direto no corpo da
// resposta HTTP (como texto/JSON), sem precisar renderizar uma página HTML.

import com.example.ApiRestFipe.service.Service;
// Importa a classe Service (camada de serviço), que contém a lógica
// de fato de consultar a API externa da FIPE.

@RestController 
// Marca a classe Controller como um Rest Controller do Spring.
// Isso faz o Spring registrar automaticamente essa classe para
// escutar requisições HTTP e tratar os retornos dos métodos como
// corpo da resposta (não como nome de view/template).
public class Controller {

    Service service = new Service();
    // Cria uma instância da classe Service manualmente (com "new").
    // É essa instância que será usada para chamar os métodos que
    // fazem as requisições à API da FIPE.
    // Obs: numa aplicação Spring "ideal", isso normalmente seria feito
    // via injeção de dependência (@Autowired ou construtor), deixando
    // o próprio Spring gerenciar o ciclo de vida do Service — mas do
    // jeito que está também funciona.

    @GetMapping("/")
    // Mapeia esse método para responder requisições GET na rota raiz
    // do site (ex: http://localhost:8080/).
    public String home(){
        // Retorna uma string de texto simples explicando ao usuário
        // como usar a API, como uma espécie de "menu de ajuda".
        return "Para consultar os dados do carro desejado, siga do seguinte caminho:\n" +
        "1 - /marcas para consultar as marcas existentes para consulta.\n" +
        "2 - /modelos/(número da marca desejada) para consultar os modelos da marca\n" +
        "3 - /anos/(número da marca)/(número do modelo) para consultar os anos desse modelo\n" +
        "4 - /valor/(número da marca)/(número do modelo)/(ano desejado) para consultar o  valor da fipe do modelo desajado no ano desejado.";
    }

    @GetMapping("/error")
    // Mapeia esse método para responder requisições GET na rota "/error".
    public String erro(){
        // Retorna apenas a string literal "error" como texto.
        // Obs: isso não é um tratamento de erro de verdade (não loga
        // nada, não captura exceções, não define um status HTTP de
        // erro) — é só um endpoint que devolve o texto "error" quando
        // alguém acessa /error manualmente.
        return "error";
    }

     @GetMapping("/marcas")
     // Mapeia esse método para GET em "/marcas".
    public String consultarMarcas(){
        // Delega a chamada para o Service, que faz a requisição real
        // à API da FIPE e retorna a lista de marcas de carros em JSON
        // (como String).
        return service.consultarMarcas();
    }


    @GetMapping("/modelos/{marca}")
    // Mapeia esse método para GET em "/modelos/{marca}", onde {marca}
    // é um trecho variável da URL (ex: "/modelos/56").
    public String consultarModelos(@PathVariable int marca){
        // @PathVariable pega o valor que está na posição {marca} da URL
        // e injeta automaticamente no parâmetro "marca" do método,
        // já convertido para int.

        return service.consultarModelos(marca);
        // Chama o Service passando o id da marca, que vai montar a URL
        // certa da API da FIPE (.../cars/brands/{marca}/models) e
        // retornar a lista de modelos dessa marca.
    }

    @GetMapping("/anos/{marca}/{modelo}")
    // Mapeia esse método para GET em "/anos/{marca}/{modelo}",
    // com duas partes variáveis na URL.

    public String consultarAnos(
            @PathVariable int marca,
            // Captura o valor de {marca} da URL e converte para int.
            @PathVariable int modelo){
            // Captura o valor de {modelo} da URL e converte para int.

        return service.consultarAnos(marca, modelo);
        // Chama o Service passando marca e modelo, que monta a URL
        // da FIPE (.../cars/brands/{marca}/models/{modelo}/years)
        // e retorna os anos disponíveis para esse modelo.
    }


    @GetMapping("/valor/{marca}/{modelo}/{ano}")
    // Mapeia esse método para GET em "/valor/{marca}/{modelo}/{ano}",
    // com três partes variáveis na URL.

    public String consultarValor(
            @PathVariable int marca,
            // Captura {marca} da URL como int.
            @PathVariable int modelo,
            // Captura {modelo} da URL como int.
            @PathVariable String ano){
            // Captura {ano} da URL como String (não int!), porque o
            // "ano" da FIPE não é um número puro — ele vem no formato
            // "ano-combustível" (ex: "2020-1"), que não cabe em int.

        return service.consultarValor(marca, modelo, ano);
        // Chama o Service passando marca, modelo e ano, que monta a
        // URL final da FIPE (.../years/{ano}) e retorna o valor de
        // mercado (tabela FIPE) daquele veículo específico.
    }
}