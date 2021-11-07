import java.util.Random;

class Jardin{
    private final int MAX = 50;
    private static int total;

    public Jardin(){
        this.total = this.MAX;
    }

    public void entra(){
        System.out.println(++total + " en Jardín");
    }

    public void sale(){
        System.out.println(--total + " en Jardín");
    }

    public int cuantos(){return total;}
}

class Persona extends Thread{
    private Jardin jardin;
    private Random aleat = new Random();

    public Persona(Jardin j, String nombre){
        this.jardin = j;
        this.setName(nombre);
    }

    public void run(){
        synchronized (this.jardin){
            int accion = aleat.nextInt(100) + 1;
            synchronized (jardin){
                if(accion % 2 == 0){
                    if(jardin.cuantos() == 50){
                        try {
                            jardin.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }else{
                        jardin.entra();
                        System.out.println(getName() + " entra");
                        jardin.notify();
                    }
                }else{
                    jardin.sale();
                    System.out.println(getName() + " sale");
                    jardin.notify();
                }
            }

        }
    }
}

public class Principal {
    public static void main(String[] args) {
        Jardin j = new Jardin();
        Persona p = null;

        for(int i = 0; i < 50; i++){
            p = new Persona(j, "Hilo " + (i + 1));
            p.start();
        }
    }
}
