package com.desafio.desafio;



import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;

public class Product {
    @JsonProperty("item")
    private String item;
    @JsonProperty("quantidade")
    private int quantidade;
    @JsonProperty("total")
    private Double total;
    @JsonProperty("dia")
    private String dia;


    @org.jetbrains.annotations.Contract(pure = true)
    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }


}


