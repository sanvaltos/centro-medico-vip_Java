package main;

public class Cajero implements Runnable {

    private final CentroMedico centroMedico;

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
                centroMedico.liberarLugar();
            }
        } catch (InterruptedException exc) {
            Thread.currentThread().interrupt();
        }
    }

    private void cobrar(Paciente paciente) throws InterruptedException {
        System.out.println("Cajero " + id + " comienza a cobrar a " + paciente.toString());
        Thread.sleep(2000);
        System.out.println("Cajero " + id +  " terminó de cobrar a " + paciente.toString());
    }

}
