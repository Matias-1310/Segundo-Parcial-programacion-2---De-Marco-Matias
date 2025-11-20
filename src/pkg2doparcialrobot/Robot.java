
package pkg2doparcialrobot;

import java.io.Serializable;

public abstract class Robot implements Serializable {

    private String nombre;
    private int energia;
    private int numeroSerie;

    public Robot(String nombre, int energia, int numeroSerie) {
        this.nombre = nombre;
        this.energia = energia;
        this.numeroSerie = numeroSerie;
    }



    public String getNombre() {
        return nombre;
    }

    public int getEnergia() {
        return energia;
    }

    public int getNumeroSerie() {
        return numeroSerie;
    }

    public abstract TipoRobot getTipo();
    public abstract String getDatoExtra();
}