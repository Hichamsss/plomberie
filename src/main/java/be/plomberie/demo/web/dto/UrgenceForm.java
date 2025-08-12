package be.plomberie.demo.web.dto;

import lombok.Data;

@Data
public class UrgenceForm {
    private String prenom;
    private String telephone;
    private String disponibilite; 
    private String description;
    private String contactEmail; }
