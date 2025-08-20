package be.plomberie.demo.api.dto;

public class RetourDto {
    private Long idRetour;
    private String contenu;
    private Integer note;

    public RetourDto() { }

    public RetourDto(Long idRetour, String contenu, Integer note) {
        this.idRetour = idRetour;
        this.contenu = contenu;
        this.note = note;
    }

    public Long getIdRetour() { return idRetour; }
    public void setIdRetour(Long idRetour) { this.idRetour = idRetour; }

    public String getContenu() { return contenu; }
    public void setContenu(String contenu) { this.contenu = contenu; }

    public Integer getNote() { return note; }
    public void setNote(Integer note) { this.note = note; }
}
