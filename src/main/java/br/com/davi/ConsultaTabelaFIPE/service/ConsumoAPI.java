package br.com.davi.ConsultaTabelaFIPE.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsumoAPI {

    public String obterDados(String endereco){
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest resquest = HttpRequest.newBuilder()
                .uri(URI.create(endereco))
                .build();

        HttpResponse<String> response =null;

        try{
            response = client.send(resquest, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e){
            throw new RuntimeException(e);
        }

        return response.body();
    }

}
