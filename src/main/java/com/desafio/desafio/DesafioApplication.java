package com.desafio.desafio;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import java.util.*;
import java.util.stream.Collectors;


@SpringBootApplication
public class DesafioApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(DesafioApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception{
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        //Criando cabecalho da requisao
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        String URL = "https://eventsync.portaltecsinapse.com.br/public/recrutamento/input?email=nibri.kcond2011@gmail.com";
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        //ArrayList para armazenamento dos produtos provinientes da api.
        ArrayList<Product> product = new ArrayList<Product>();
        //ArrayList para ordenacao dos itens por data.
        DiaComparator diaComparator = new DiaComparator();

        //Lista encadeada para armazenar os valores.
        LinkedHashMap<String,Double> map = new LinkedHashMap<>();
        try {
            //Criando requisao get para recuperar os dados provinentes da api de produtos e transforma-la em objeto.

            /*------------------------------------------------------------------------------------------*/
            /*-------------------------- REQUISICAO GET ------------------------------------------------*/
            /*------------------------------------------------------------------------------------------*/
            ResponseEntity<Product[]> responseEntity = restTemplate.exchange(URL, HttpMethod.GET,entity, Product[].class);
            if(responseEntity.getStatusCode()==HttpStatus.OK){
                // Adicionando produtos a lista e requirindo que a resposta nao esteja vai no body da requisao
                Collections.addAll(product, Objects.requireNonNull(responseEntity.getBody()));

                // Ordenacao do vetor por data a partir da conversao do dia de String para o Formato Date atraves de uma
                // interface
                product.sort(diaComparator);

                //Rodando Lista de produtos
                for (Product productF : product) {

                    //Filtrando os registro com final de dezembro de 2018
                    if(productF.getDia().endsWith("12/2018")){

                        //System.out.println(produto.getItem() +" "+ produto.getDia() +"  " + produto.getTotal());

                        //String auxiliar para armazenar o nome do item
                        String item = productF.getItem();
                        //ordenacao do item com valor
                        Double val = map.getOrDefault(item,0.);
                        //somando valores dos produtos
                        val+=productF.getTotal();
                        //adicionando valores a lista encadeada
                        map.put(item,val);
                    }
                }
            }

        }catch (Exception e){
            //Mostra Caso Ocorreu algum erro mostra o StackTrace da excessao em questao.
            System.out.println(Arrays.toString(e.getStackTrace()));
        }

        /* Procurando o maior na Lista encadeada*/
        Double max = map.entrySet()
                .stream()
                .max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1)
                .get()
                .getValue();

        /* Efetuando o filtro do maior valor e transformando um collection List*/
        List listOfMax = map.entrySet()
                .stream()
                .filter(entry -> entry.getValue() == max)
                .collect(Collectors.toList());

        /* EXIBINDO O ITEM O SEU TOTAL PARA VERIFICACAO*/
        /*  System.out.println(listOfMax); */
        /*------------------------------------------------------------------------------------------*/
        /*-------------------------- REQUISICAO POST------------------------------------------------*/
        /*------------------------------------------------------------------------------------------*/
        RestTemplate restTemplatePost = new RestTemplate();
        HttpHeaders headersPost = new HttpHeaders();
        headersPost.setAccept(Collections.singletonList(MediaType.TEXT_PLAIN));
        String URLPOST = "https://eventsync.portaltecsinapse.com.br/public/recrutamento/finalizar?email=nibri.kcond2011@gmail.com";
        HttpEntity<String> entityPost = new HttpEntity<>(headersPost);

        HttpEntity<List> listofmax = new HttpEntity<>(listOfMax);
        try {
            //efetua a requisao post.
            ResponseEntity<Object> responseEntity = restTemplatePost.postForEntity(URLPOST, listofmax, Object.class);
        }catch (Exception e){
            System.out.println(Arrays.toString(e.getStackTrace()));
        }


    }




}
