package main;

public class Cajero implements Runnable {

    private CentroMedico centroMedico;

    private final int id;

    public Cajero(CentroMedico centroMedico, int id) {
        this.centroMedico = centroMedico;
        this.id = id;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Paciente paciente = centroMedico.liberarPaciente();
                cobrar(paciente);
            }
        } catch (InterruptedException exc) {
            Thread.currentThread().interrupt();
        }
    }

    private void cobrar(Paciente paciente) throws InterruptedException {
        System.out.println("Cajero " + id + " comienza a cobrar a " + paciente.obtenerNombre());
        Thread.sleep(1500); // simula tiempo de caja
        System.out.println("Cajero " + id +  " terminó de cobrar a " + paciente.obtenerNombre());
    }

}
