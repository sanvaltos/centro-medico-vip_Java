package main;

public class Paciente {
    private final String nombre;
    private boolean categoriaVip;
    private Integer consultorio;

    public Paciente(String nombre, boolean categoriaVip, Integer consultorio) {
        this.nombre = nombre;
        this.categoriaVip = categoriaVip;
        this.consultorio = consultorio;
    }

    public String obtenerNombre() {
        return nombre;
    }

    public int obtenerTurno() {
        return consultorio;
    }

    public boolean esVip() {
        return categoriaVip;
    }

}
