package br.com.davi.ConsultaTabelaFIPE.service;

import java.util.List;

public interface IConverteDados {
    <T> T converterDados(String json, Class<T> classe);

    <T> List<T> converteLista(String json, Class<T> classe);
}
