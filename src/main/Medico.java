package main;

import java.util.Random;

public class Medico implements Runnable {
    private final CentroMedico centroMedico;

    private final FilaDeEspera filaPacientes;

    private int atendidos = 0;

    private final String nombre;

    private final int consultorio;

    public Medico(CentroMedico centroMedico, String nombre, FilaDeEspera filaPacientes, int id) {
        this.centroMedico = centroMedico;
        this.nombre = nombre;
        this.filaPacientes = filaPacientes;
        this.consultorio = id;
    }


    @Override
    public void run() {
        try {
            while (true) {
                Paciente paciente = null;

                if (tocaVIP() && centroMedico.hayFilaVip()) {
                    paciente = centroMedico.obtenerPacienteVip();

                    // 2. Si tengo pacientes en mi fila → atenderlos
                } else if (tengoPacientes()) {
                    paciente = filaPacientes.obtenerPaciente();
                    // 3. Si no tengo pacientes pero hay VIPs → atender VIP
                }
                else if (centroMedico.hayFilaVip()) {
                    paciente = centroMedico.obtenerPacienteVip();
                    // 4. Si no hay nadie → esperar en mi fila (se bloquea ahí)
                }
                else {
                    paciente = filaPacientes.obtenerPaciente();
                    // cuando llegue alguien, se despierta aquí
                }

                if (paciente != null) {
                    atenderPaciente(paciente);
                    if (!paciente.esVip()) {
                        registrarAtencion();
                    }
                    centroMedico.mandarPaciente(paciente);
                }
            }
        } catch (InterruptedException exc) {
            throw new RuntimeException(exc);
        }
    }

    public void atenderPaciente(Paciente paciente) throws InterruptedException {
        Random random = new Random();

        if (paciente.esVip()) {
            System.out.println(nombre + " le toca " + paciente.toString() + " que es VIP en consultorio" + consultorio);
        } else {
            System.out.println(nombre + " comienza a atender a " + paciente.toString() + " en consultorio" + consultorio);
        }

        int tiempoAtencion = random.nextInt(10) + 1;

        Thread.sleep(tiempoAtencion * 1000);

        System.out.println( nombre + " termino con " + paciente.toString() +  " en " + tiempoAtencion + " segundos.");

    }

    public boolean tengoPacientes() {
        boolean retorno = true;
        if (filaPacientes.calcularCantidad() == 0){
            retorno = false;
        }
        return retorno;
    }

    public synchronized void registrarAtencion() {
        atendidos++;
    }


    public synchronized boolean tocaVIP() {
        boolean retorno = false;
        if (atendidos >= 3) {
            atendidos = 0;
            retorno = true;
        }
        return retorno;
    }

}
