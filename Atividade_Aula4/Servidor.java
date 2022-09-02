package Atividade_Aula4;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Enumeration;
import java.util.Vector;

public class Servidor extends Thread {
    private static Vector clientes;
    private Socket conexao;
    private String meuNome;

    public Servidor(Socket s) {
        conexao = s;
    }

    public static void main(String[] args) {
        try {
            clientes = new Vector();

            ServerSocket s = new ServerSocket(2001);

            while (true) {
                System.out.println("Esperando conectar...");
                Socket conexao = s.accept();
                System.out.println("Conectou!");
                Thread t = new Servidor(conexao);
                t.start();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void run() {
        try {
            BufferedReader entrada = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
            PrintStream saida = new PrintStream(conexao.getOutputStream());

            meuNome = entrada.readLine();
            if (meuNome == null) {
                return;
            }

            clientes.add(saida);
            sendToAll(saida, " entrou ", " no chat");
            System.out.println("Endereco do cliente: "+ conexao.getInetAddress()+":"+conexao.getPort());

            String linha = entrada.readLine();


            while ((linha != null) && (!linha.trim().equals(""))) {
                sendToAll(saida, " dissse: ", linha);
                linha = entrada.readLine();
            }
            sendToAll(saida, " saiu ", " do Chat");
            clientes.remove(saida);
            conexao.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void sendToAll(PrintStream saida, String acao, String linha) {
        Enumeration e = clientes.elements();
        while (e.hasMoreElements()) {
            PrintStream chat = (PrintStream) e.nextElement();
            if (chat != saida) {
                chat.println(meuNome + acao + linha);
            }
            if (acao.equals(" saiu ")) {
                if (chat == saida)
                    chat.println("");
            }
        }
    }
}
