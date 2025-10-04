package main;

public class Paciente {
    private final String nombre;
    private boolean categoriaVip;
    private Integer turno;

    public Paciente(String nombre, boolean categoriaVip, Integer consultorio) {
        this.nombre = nombre;
        this.categoriaVip = categoriaVip;
        this.turno = consultorio;
    }

    public String toString() {
        return nombre;
    }

    public int obtenerTurno() {
        return turno;
    }

    public boolean esVip() {
        return categoriaVip;
    }

}
