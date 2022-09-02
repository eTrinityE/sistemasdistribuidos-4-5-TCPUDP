package Atividade_Aula5;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Servidor {
    public static void main(String[] args) {
        try {
            DatagramSocket s = new DatagramSocket(4547);
            System.out.println("Servidor esperando conexão...");

            String envio;
            DatagramPacket recebe = new DatagramPacket(new byte[512], 512);

            while (true){
                envio = "";
                s.receive(recebe);
                System.out.print("Mensagem recebida da porta "+ recebe.getPort()+" : ");
                for (int i = 0; i < recebe.getLength(); i++){
                    System.out.print((char) recebe.getData()[i]);
                }
                System.out.println();

                DatagramPacket resp = new DatagramPacket(recebe.getData(), recebe.getLength(), recebe.getAddress(), recebe.getPort());
                s.send(resp);
            }
        } catch (Exception e){
            System.out.println(e);
        }
    }
}
