package com.biblioteca.api_publica.service.external;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.biblioteca.api_publica.domain.dto.google.GoogleBooksResponse;

@Service
public class GoogleBooksService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String GOOGLE_API_URL = "https://www.googleapis.com/books/v1/volumes?q=isbn:";

    public GoogleBooksResponse.VolumeInfo buscarDadosPorIsbn(String isbn) {
        try {
            String url = GOOGLE_API_URL + isbn.replace("-", "");
            System.out.println("Consultando Google Books: " + url);
            GoogleBooksResponse response = restTemplate.getForObject(url, GoogleBooksResponse.class);

            if (response != null && response.getItems() != null && !response.getItems().isEmpty()) {
                return response.getItems().get(0).getVolumeInfo();
            } else {
                System.out.println("Google Books: Nenhum livro encontrado para o ISBN: " + isbn);
            }
        } catch (Exception e) {
            // Se a API falhar, o sistema não deve parar. Apenas logamos.
            System.err.println("Erro ao consultar Google Books: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
        return null;
    }
}