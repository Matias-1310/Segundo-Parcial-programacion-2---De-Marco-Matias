
package pkg2doparcialrobot;



public class RobotDomestico extends Robot {


private int tareas;


public RobotDomestico(String nombre, int energia, int numeroSerie, int tareas) {
super(nombre, energia, numeroSerie);
this.tareas = tareas;
}


@Override
public TipoRobot getTipo() {
return TipoRobot.DOMESTICO;
}


@Override
public String getDatoExtra() {
return String.valueOf(tareas);
}
}