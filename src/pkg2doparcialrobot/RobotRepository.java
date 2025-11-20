
package pkg2doparcialrobot;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RobotRepository {

    private static final String ARCHIVO = "robots.dat";
    private List<Robot> robots = new ArrayList<>();

    public RobotRepository() {
        cargar();
    }

    public List<Robot> getRobots() {
        return robots;
    }

    public void agregar(Robot r) throws NumeroSerieDuplicadoException, EnergiaInvalidaException, NumeroSerieInvalidoException {

        if (r.getEnergia() < 0 || r.getEnergia() > 100)
            throw new EnergiaInvalidaException("La energía debe estar entre 0 y 100.");

        if (r.getNumeroSerie() < 0)
            throw new NumeroSerieInvalidoException("El número de serie debe ser positivo.");

        for (Robot x : robots)
            if (x.getNumeroSerie() == r.getNumeroSerie())
                throw new NumeroSerieDuplicadoException("Número de serie duplicado.");

        robots.add(r);
        guardar();
    }

    public void eliminar(Robot r) {
        robots.remove(r);
        guardar();
    }

    public void guardar() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO))) {
            oos.writeObject(robots);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cargar() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARCHIVO))) {
            robots = (List<Robot>) ois.readObject();
        } catch (Exception e) {
            robots = new ArrayList<>();
        }
    }

    public List<Robot> getLowEnergyRobots() {
        List<Robot> low = new ArrayList<>();
        for (Robot r : robots)
            if (r.getEnergia() < 20)
                low.add(r);
        return low;
    }
}
