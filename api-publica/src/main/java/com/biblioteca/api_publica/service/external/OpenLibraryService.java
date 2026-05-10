package com.biblioteca.api_publica.service.external;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OpenLibraryService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String OPEN_LIBRARY_URL = "https://openlibrary.org/api/books?bibkeys=ISBN:%s&format=json&jscmd=data";

    public String[] buscarDadosCompletos(String isbn) {
        try {
            String isbnLimpo = isbn.replaceAll("[^0-9]", "");
            String url = String.format(OPEN_LIBRARY_URL, isbnLimpo);

            String json = restTemplate.getForObject(url, String.class);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(json);
            JsonNode book = root.path("ISBN:" + isbnLimpo);

            if (!book.isMissingNode()) {
                String titulo = book.path("title").asText();

                // Lógica de Resumo: Prioridade para 'subtitle', depois 'notes'
                String resumo = "Resumo não disponível.";
                if (!book.path("subtitle").isMissingNode()) {
                    resumo = book.path("subtitle").asText();
                } else if (!book.path("notes").isMissingNode()) {
                    resumo = book.path("notes").asText();
                }

                // Lógica de Data: Pegar a string bruta para processar no Service
                String dataBruta = book.path("publish_date").asText(null);

                return new String[] { titulo, resumo, dataBruta };
            }
        } catch (Exception e) {
            System.err.println("Open Library falhou: " + e.getMessage());
        }
        return null;
    }
}