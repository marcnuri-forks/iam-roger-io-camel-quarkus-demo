package com.redhat.consulting.demo.routes;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.redhat.consulting.demo.model.PessoaModel;


public class RouteDemo extends RouteBuilder {

	private static final Logger logger = LogManager.getLogger(RouteDemo.class);
	
	
	@Override
	public void configure() throws Exception {
		
        restConfiguration()
        .bindingMode(RestBindingMode.json)
		.apiContextPath("/api-doc/swagger.json").apiContextRouteId("rest-documentation")
        .apiProperty("api.title", "API Pessoa ")
        .apiProperty("api.version", "1.0.0")
        .apiProperty("api.description", "Camel Quarkus Demo")        
        
        .enableCORS(true)
        .corsHeaderProperty("Access-Control-Allow-Origin", "*")
        .corsHeaderProperty("Access-Control-Allow-Credentials", "*")
		.corsHeaderProperty("Access-Control-Allow-Methods", "GET, HEAD, OPTIONS, POST, PUT, DELETE")
        .corsHeaderProperty("Access-Control-Request-Headers", "Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers, api_key, authorization, Referer, User-Agent")
        .corsHeaderProperty("Access-Control-Allow-Headers", "Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers, authorization, Referer, User-Agent");
        
        rest("/pessoa")        	
        		.get("/{pessoaId}")
                .produces("application/json")
                .outType(PessoaModel.class)     
                .bindingMode(RestBindingMode.json)	
                .to("direct:get-pessoa-by-id")
                .responseMessage().code(200).endResponseMessage()
                .responseMessage().code(500).message("Sistema indisponível").endResponseMessage()
                
        		.get()
                .produces("application/json")                     
                .bindingMode(RestBindingMode.json)	
                .to("direct:get-all-pessoas")
                .responseMessage().code(200).endResponseMessage()                
                .responseMessage().code(500).message("Sistema indisponível").endResponseMessage()
                
        		.delete("/{pessoaId}")        		
        		.produces("application/json")        		  
        		.bindingMode(RestBindingMode.json)		        
        		.to("direct:delete-pessoa")
        
                .post()
            	.produces("text/plain")
            	.consumes("application/json")
            	.type(PessoaModel.class)     
		        .bindingMode(RestBindingMode.json)		        
		        .to("direct:criar-pessoa")		        
		        .responseMessage().code(201).message("criado").endResponseMessage()
		        .responseMessage().code(422).message("Parâmetro inválido na request").endResponseMessage()
		        .responseMessage().code(500).message("Sistema indisponível").endResponseMessage()

                .put()
            	.produces("text/plain")
            	.consumes("application/json")
            	.type(PessoaModel.class)     
		        .bindingMode(RestBindingMode.json)		        
		        .to("direct:criar-pessoa")		        
		        .responseMessage().code(201).message("criado").endResponseMessage();		        
		        

		

		/*
		 * Consultar pessoa pelo ID
		 * Exemplo com jpq query
		 */
        from("direct:get-pessoa-by-id").routeId("get-pessoa-by-id")
        .toD("jpa://com.redhat.consulting.demo.entity.PessoaModel?query=select a from PessoaModel a where id = ${header.pessoaId}");
                
        /*
         * Obter lista de pessoas
         * Exemplo com named query
         */
		from("direct:get-all-pessoas").id("get-all-pessoas")		
		.to("jpa://com.redhat.consulting.demo.entity.PessoaModel?namedQuery=findAll");	
                
        /*
         * Remove uma pessoa
         * Exemplo com query nativa
         */
        from("direct:delete-pessoa").routeId("delete-pessoa")
        .toD("jpa://com.redhat.consulting.demo.entity.PessoaModel?nativeQuery=delete from tb_pessoa  where pessoaID = ${header.pessoaId}")
        .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200));
        
        /*
         * Insert nova pessoa na base de dados.
         */
        from("direct:criar-pessoa").routeId("criar-pessoa")
        .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(201))
        .process(exchange -> {        	
            
        	PessoaModel pessoa = exchange.getIn().getBody(PessoaModel.class);
        	
        	exchange.getIn().setBody(pessoa);
        })
        .choice()
        	.when(header(Exchange.HTTP_RESPONSE_CODE).isEqualTo(201))
        		.to("jpa://com.redhat.consulting.demo.entity.Pessoa")        		
    	.endChoice().end()
    	.setBody(header(Exchange.HTTP_RESPONSE_TEXT));
                
        
	}
	
}