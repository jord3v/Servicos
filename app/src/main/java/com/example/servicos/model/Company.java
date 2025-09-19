package com.example.servicos.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Company {

    private String cnpj;
    @SerializedName("razao_social")
    private String razaoSocial;
    @SerializedName("nome_fantasia")
    private String nomeFantasia;
    @SerializedName("natureza_juridica")
    private String naturezaJuridica;
    private Status situacao;
    private Address endereco;
    @SerializedName("atividade_principal")
    private Activity atividadePrincipal;
    @SerializedName("atividades_secundarias")
    private List<Activity> atividadesSecundarias;

    public String formatter() {
        String result = "CNPJ: ".concat(cnpj != null ? cnpj : "")
            .concat("\nRazão Social: ").concat(razaoSocial != null ? razaoSocial : "")
            .concat(nomeFantasia != null && !nomeFantasia.isEmpty() ? "\nNome Fantasia: ".concat(nomeFantasia) : "")
            .concat("\nNatureza Jurídica: ").concat(naturezaJuridica != null ? naturezaJuridica : "")
            .concat("\n\n--- Situação ---\n")
            .concat("Status: ").concat(situacao != null && situacao.getNome() != null ? situacao.getNome() : "")
            .concat("\nData: ").concat(situacao != null && situacao.getData() != null ? situacao.getData() : "")
            .concat("\n\n--- Endereço ---\n")
            .concat(endereco != null && endereco.getLogradouro() != null ? endereco.getLogradouro() : "")
            .concat(", ").concat(endereco != null && endereco.getNumero() != null ? endereco.getNumero() : "")
            .concat("\n")
            .concat(endereco != null && endereco.getBairro() != null ? endereco.getBairro() : "")
            .concat(", ").concat(endereco != null && endereco.getMunicipio() != null ? endereco.getMunicipio() : "")
            .concat(" - ").concat(endereco != null && endereco.getUf() != null ? endereco.getUf() : "")
            .concat("\nCEP: ").concat(endereco != null && endereco.getCep() != null ? endereco.getCep() : "")
            .concat("\n\n--- Atividade Principal ---\n")
            .concat(atividadePrincipal != null && atividadePrincipal.getDescricao() != null ? atividadePrincipal.getDescricao() : "")
            .concat("\n");
        return result;
    }

    // Nested Classes
    public static class Status {
        private String nome;
        private String data;
        private String motivo;

        public String getNome() { return nome; }
        public String getData() { return data; }
        public String getMotivo() { return motivo; }
    }

    public static class Address {
        private String tipo_logradouro;
        private String logradouro;
        private String numero;
        private String complemento;
        private String bairro;
        private String cep;
        private String uf;
        private String municipio;

        public String getTipo_logradouro() { return tipo_logradouro; }
        public String getLogradouro() { return logradouro; }
        public String getNumero() { return numero; }
        public String getComplemento() { return complemento; }
        public String getBairro() { return bairro; }
        public String getCep() { return cep; }
        public String getUf() { return uf; }
        public String getMunicipio() { return municipio; }
    }

    public static class Activity {
        private String codigo;
        private String descricao;

        public String getCodigo() { return codigo; }
        public String getDescricao() { return descricao; }
    }
}
