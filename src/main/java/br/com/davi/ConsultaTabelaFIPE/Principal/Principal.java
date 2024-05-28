package br.com.davi.ConsultaTabelaFIPE.Principal;

import br.com.davi.ConsultaTabelaFIPE.model.DadosGerais;
import br.com.davi.ConsultaTabelaFIPE.model.DadosModelos;
import br.com.davi.ConsultaTabelaFIPE.model.Veiculo;
import br.com.davi.ConsultaTabelaFIPE.service.ConsumoAPI;
import br.com.davi.ConsultaTabelaFIPE.service.ConverteDados;

import java.util.*;

public class Principal {
    private Scanner leitura = new Scanner(System.in);
    private ConsumoAPI consumo = new ConsumoAPI();
    private ConverteDados conversor = new ConverteDados();
    private final String URL_BASE ="https://parallelum.com.br/fipe/api/v1/";

    public void exibeMenu(){
        var menu = """
                 **** OPÇÕES ****
                 
                 Carro
                 
                 Moto
                 
                 Caminhão
                 
                 Digite uma das opções para consultar valores:
                 """;

        System.out.println(menu);
        var tipo = leitura.nextLine();

        String endereco = null;

        if(tipo.toLowerCase().contains("carr")){
            endereco = URL_BASE + "carros/marcas";
        } else if (tipo.toLowerCase().contains("mot")) {
            endereco = URL_BASE + "motos/marcas";
        } else if (tipo.toLowerCase().contains("camin")) {
            endereco = URL_BASE + "caminhoes/marcas";
        }

        var json = consumo.obterDados(endereco);
        //System.out.println(json);
        List<DadosGerais> marcas = conversor.converteLista(json, DadosGerais.class);

        //
        marcas.stream()
                .sorted(Comparator.comparing(DadosGerais::codigo))
                .forEach(m -> System.out.println("Cód:  "+ m.codigo()+" Descrição: "+ m.nome()));

//        Map<String, String> marcasMapa = new HashMap<>();
//
//        for (int i=0; i < marcas.size();i++){
//            DadosGerais dado = marcas.get(i);
//            marcasMapa.put(dado.codigo(), dado.nome());
//        }

        System.out.println("Digite o código da marca desejada: ");
        var marcaEscolhida = leitura.nextLine();

        endereco = endereco+"/"+marcaEscolhida+"/modelos";
        json = consumo.obterDados(endereco);

        System.out.println(json);
        DadosModelos modelos = conversor.converterDados(json, DadosModelos.class);

        modelos.modelos().stream()
                .sorted(Comparator.comparing(DadosGerais::codigo))
                .forEach(m -> System.out.println("Cód:  "+ m.codigo()+" Descrição: "+ m.nome()));


        System.out.println("Escolha o nome do veiculo desejado: ");
        var nomeVeiculo = leitura.nextLine();

        modelos.modelos().stream()
                .filter(v -> v.nome().toUpperCase().contains(nomeVeiculo.toUpperCase()))
                .sorted(Comparator.comparing(DadosGerais::codigo))
                .forEach(m -> System.out.println("Cód:  "+ m.codigo()+" Descrição: "+ m.nome()));

        System.out.println("Digite o código do modelo o qual deseja consultar a tabela FIPE: ");
        var modeloEscolhido = leitura.nextLine();

        endereco = endereco+"/"+modeloEscolhido+"/anos";
        json = consumo.obterDados(endereco);

        List<DadosGerais> listaVeiculoAnos = conversor.converteLista(json, DadosGerais.class);

        List<Veiculo> veiculos = new ArrayList<>();

        for(int i =0; i < listaVeiculoAnos.size(); i++){
            json = consumo.obterDados(endereco+"/"+listaVeiculoAnos.get(i).codigo());
            veiculos.add(conversor.converterDados(json, Veiculo.class));
        }
        System.out.println("Veiculos pelo ano: ");
        veiculos.forEach(System.out::println);

    }
}
