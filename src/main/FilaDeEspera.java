package main;

import java.util.LinkedList;
import java.util.Queue;

public class FilaDeEspera {
    private final Queue<Paciente> fila = new LinkedList<>();

    public synchronized void agregarPaciente(Paciente paciente) {
        fila.offer(paciente);
        notify();
    }

    public synchronized Paciente obtenerPaciente() throws InterruptedException {
        while (fila.isEmpty()) {
            wait();
        }
        return fila.poll();
    }

    public synchronized int obtenerCantidad() {
        return fila.size();
    }

}
