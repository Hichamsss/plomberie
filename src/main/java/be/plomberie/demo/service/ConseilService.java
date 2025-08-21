package be.plomberie.demo.service;

import be.plomberie.demo.model.ConseilEntretien;

import java.util.List;

public interface ConseilService {
    List<ConseilEntretien> list(String q, String categorie);
    ConseilEntretien get(Long id);
}