package com.desafio.desafio;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DiaComparator implements Comparator, java.util.Comparator<Product> {

    @Override
    public int compare(Product p, Product p2) {
        Date data1 = null;
        try {
            //Efetuando o parse da data que esta em String para o formato data
            data1 = new SimpleDateFormat("dd/MM/yyyy").parse(p.getDia());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date date2 = null;
        try {
            //Efetuando o parse da data que esta em String para o formato data
            date2 = new SimpleDateFormat("dd/MM/yyyy").parse(p2.getDia());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //o Objeto data data1 e data2 nao sendo null
        assert data1 != null;
        assert date2 != null;
        //Retorna a comparacao.
        return data1.compareTo(date2);


    }

}
