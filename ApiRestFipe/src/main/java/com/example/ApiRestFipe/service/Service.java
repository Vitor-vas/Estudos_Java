package com.example.ApiRestFipe.service;
// Declara o pacote onde essa classe fica. Por convenção, "service" é
// a camada responsável pela lógica de negócio — aqui, a lógica de
// se comunicar com a API externa da FIPE.

import org.springframework.http.ResponseEntity;
// Classe do Spring que representa uma resposta HTTP completa: corpo,
// status code e headers. É o que o RestTemplate devolve ao fazer
// uma chamada HTTP.
import org.springframework.web.client.HttpClientErrorException;
// Exceção lançada automaticamente pelo RestTemplate quando a resposta
// da API tem um status de erro do lado do cliente (4xx), como 404
// (não encontrado) ou 400 (requisição inválida).
import org.springframework.web.client.RestTemplate;
// Classe do Spring usada para fazer requisições HTTP (GET, POST etc.)
// para APIs externas — é o "cliente HTTP" que você usa pra consultar
// a API da FIPE.

import org.slf4j.Logger;
// Interface de logging usada para registrar mensagens (erros, avisos,
// infos) de forma padronizada, em vez de usar System.out.println.
import org.slf4j.LoggerFactory;
// Fábrica que cria instâncias de Logger vinculadas a uma classe
// específica — assim, cada mensagem de log já vem identificada com
// a origem (aqui, "Service").
import org.springframework.http.ResponseEntity;
// (Import duplicado — ResponseEntity já foi importado acima. Essa
// linha é redundante e pode ser removida sem afetar o funcionamento.)
import org.springframework.web.client.HttpClientErrorException;
// (Import duplicado — HttpClientErrorException já foi importado acima.
// Também pode ser removido.)
import org.springframework.web.client.RestTemplate;
// (Import duplicado — RestTemplate já foi importado acima. Idem.)

public class Service {
    private static final Logger logger = LoggerFactory.getLogger(Service.class);
    // Cria uma instância de Logger vinculada à classe Service.
    // "static" porque é compartilhada por todas as instâncias da
    // classe (não precisa criar um logger novo por objeto);
    // "final" porque essa referência nunca muda depois de criada.

    private static final String linkApi = "https://fipe.parallelum.com.br/api/v2";
    // Constante com a URL base da API da FIPE. Todos os métodos de
    // consulta abaixo completam essa URL com o caminho específico
    // do recurso que querem acessar (marcas, modelos, anos, valor).

    public String consultaApi(String urlApi){
        // Método genérico que faz a chamada HTTP para qualquer URL da
        // API da FIPE que for passada como parâmetro. Todos os outros
        // métodos de consulta (marcas, modelos, anos, valor) usam este
        // método por baixo dos panos.
        String dados;
        // Variável que vai guardar o resultado final (o JSON de
        // resposta, ou uma mensagem de erro), que será retornado ao
        // final do método.

        RestTemplate restTemplate = new RestTemplate();
        // Cria uma nova instância do cliente HTTP do Spring, usado
        // para efetivamente fazer a requisição GET à API externa.

        try {
            // Início do bloco try: qualquer erro de comunicação HTTP
            // (como um 404 retornado pela API) vai ser capturado pelo
            // catch logo abaixo, em vez de quebrar a aplicação sem
            // controle.

            ResponseEntity<String> responseEntity = restTemplate.getForEntity(urlApi, String.class);
            // Faz a requisição GET para a URL recebida como parâmetro.
            // O resultado é encapsulado em um ResponseEntity<String>,
            // onde o corpo da resposta é tratado como String (JSON cru,
            // sem parsear para objetos Java).

            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                // Verifica se o status HTTP da resposta está na faixa
                // de sucesso (200-299, ex: 200 OK).
                dados = responseEntity.getBody();
                // Se deu certo, extrai o corpo da resposta (o JSON) e
                // guarda na variável "dados".
            } else {
                // Caso o status não seja de sucesso, mas também não
                // tenha lançado exceção (situação rara com RestTemplate,
                // já que a maioria dos erros 4xx/5xx já cai no catch).
                dados = "Falha ao obter dado. Código de status: " + responseEntity.getStatusCode();
                // Monta uma mensagem de erro simples com o código de
                // status recebido.
            }
        } catch (HttpClientErrorException e) {
            // Captura especificamente erros HTTP do lado do cliente
            // (4xx), como 404 (recurso não encontrado) — que é o que
            // acontece quando se consulta uma marca/modelo/ano
            // inexistente na FIPE.

            logger.error("Erro ao consultar API FIPE. URL: {} | Status: {} | Resposta: {}",
                    urlApi, e.getStatusCode(), e.getResponseBodyAsString());
            // Registra um log de erro estruturado, incluindo: a URL que
            // falhou, o status HTTP retornado pela API, e o corpo da
            // resposta de erro (geralmente contém uma mensagem explicando
            // o motivo, como "veículo não encontrado").
            // Os "{}" são placeholders do SLF4J, substituídos na ordem
            // pelos argumentos passados depois da mensagem.

            throw e; // deixa o GlobalExceptionHandler tratar
            // Relança a mesma exceção capturada. Isso é importante:
            // sem essa linha, a exceção "morreria" aqui dentro do
            // Service, e o método continuaria (ou quebraria de outra
            // forma). Relançando, a exceção sobe até o controller e é
            // interceptada pelo @RestControllerAdvice (GlobalExceptionHandler),
            // que transforma isso numa resposta HTTP de erro amigável.
        }

        return dados;
        // Retorna o resultado da consulta: o JSON de sucesso, ou a
        // mensagem de falha (no caso do else acima). Se a exceção foi
        // lançada no catch, essa linha nunca é alcançada.
    }


    public String consultarMarcas(){
        // Método público específico para consultar as marcas de carros.
        return consultaApi(linkApi + "/cars/brands");
        // Monta a URL completa (.../api/v2/cars/brands) e delega a
        // chamada para o método genérico consultaApi.
    }

    public String consultarModelos(int id){
        // Método público específico para consultar os modelos de uma
        // marca. Recebe o id da marca como parâmetro.
        return consultaApi(linkApi + "/cars/brands/" + id + "/models");
        // Monta a URL (.../cars/brands/{id}/models) concatenando o id
        // recebido, e delega para consultaApi.
    }
    public String consultarAnos(int marca, int modelo) {
        // Método público específico para consultar os anos disponíveis
        // de um determinado modelo de uma determinada marca.
        return consultaApi(linkApi + "/cars/brands/" + marca + "/models/" + modelo + "/years");
        // Monta a URL (.../cars/brands/{marca}/models/{modelo}/years)
        // concatenando marca e modelo, e delega para consultaApi.
    }
    public String consultarValor(int marca, int modelo, String ano) {
        // Método público específico para consultar o valor (tabela
        // FIPE) de um veículo específico: marca + modelo + ano.
        // "ano" é String porque o formato usado pela API inclui o tipo
        // de combustível (ex: "2020-1"), não sendo um número puro.
        return consultaApi(linkApi + "/cars/brands/" + marca + "/models/" + modelo + "/years/" + ano);
        // Monta a URL final (.../years/{ano}) concatenando marca,
        // modelo e ano, e delega para consultaApi — que efetivamente
        // faz a chamada HTTP e trata erros.
    }


}