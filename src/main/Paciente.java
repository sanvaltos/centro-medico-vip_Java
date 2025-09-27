package main;

public class Paciente {
    private final String nombre;
    private boolean categoriaVip;
    private int turno;

    public Paciente(String nombre, boolean categoriaVip, int turno) {
        this.nombre = nombre;
        this.categoriaVip = categoriaVip;
        this.turno = turno;
    }

    public String obtenerNombre() {
        return nombre;
    }

    public int obtenerTurno() {
        return turno;
    }

    public boolean esVip() {
        return categoriaVip;
    }

}
