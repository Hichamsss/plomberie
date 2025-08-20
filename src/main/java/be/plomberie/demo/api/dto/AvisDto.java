package be.plomberie.demo.api.dto;

public class AvisDto {
    private Long idAvis;
    private String contenu;
    private Integer note;

    public AvisDto() { }

    public AvisDto(Long idAvis, String contenu, Integer note) {
        this.idAvis = idAvis;
        this.contenu = contenu;
        this.note = note;
    }

    public Long getIdAvis() { return idAvis; }
    public void setIdAvis(Long idAvis) { this.idAvis = idAvis; }

    public String getContenu() { return contenu; }
    public void setContenu(String contenu) { this.contenu = contenu; }

    public Integer getNote() { return note; }
    public void setNote(Integer note) { this.note = note; }
}
