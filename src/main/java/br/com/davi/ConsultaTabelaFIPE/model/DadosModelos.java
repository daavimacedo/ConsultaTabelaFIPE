package br.com.davi.ConsultaTabelaFIPE.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosModelos (List<DadosGerais> modelos) {
}
