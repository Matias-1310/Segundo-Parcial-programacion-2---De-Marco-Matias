
package pkg2doparcialrobot;


public class RobotIndustrial extends Robot {


private int cargaMaxima;


public RobotIndustrial(String nombre, int energia, int numeroSerie, int cargaMaxima) {
super(nombre, energia, numeroSerie);
this.cargaMaxima = cargaMaxima;
}


@Override
public TipoRobot getTipo() {
return TipoRobot.INDUSTRIAL;
}


@Override
public String getDatoExtra() {
return String.valueOf(cargaMaxima);
}
}