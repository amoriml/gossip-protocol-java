package br.com.ufabc.GossipProtocol.threads;

import br.com.ufabc.GossipProtocol.model.Peer;
import br.com.ufabc.GossipProtocol.model.PeerState;
import br.com.ufabc.GossipProtocol.util.JSONParse;
import br.com.ufabc.GossipProtocol.util.MensagemUtil;
import br.com.ufabc.GossipProtocol.util.PacoteFactory;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.HashMap;

public class PeerReciveHandler implements Runnable {

    private DatagramSocket socket;
    private Peer peer;
    private DatagramPacket pacote;
    private HashMap<Integer, PeerState> estadosRecebidos;
    private boolean isAlive;

    public PeerReciveHandler(Peer peer) {
        this.peer = peer;
        this.isAlive = true;
        try {
            socket = PacoteFactory.createSocket(peer.getPort());
        } catch (SocketException e) {
            e.printStackTrace();
            System.out.println("PeerReciveHandler: Erro ao criar o socket\n" + 
		             "\n" + e.getClass() + "\n" + e.getMessage() + 
		             "\n" + e.getStackTrace());
            throw new RuntimeException("Peer reciver", e);
        }
    }

    @Override
    public void run() {
            try {
                byte[] buffer = new byte[64000];
                DatagramPacket pacote = PacoteFactory.createPackage(buffer);

                System.out.println("PeerReciveHandler " + peer.getNome() + ": ouvindo em " + peer.getPort());
                socket.receive(pacote);

                System.out.println("PeerReciveHandler " + peer.getNome() +
                        ": recebido -->\n" + new String(pacote.getData()) +
                        "\nFrom: " + pacote.getAddress() + ":" + pacote.getPort() +
                        "\nPacote size:" + pacote.getLength());
                Thread t = new Thread() {
                    @Override
                    public void run() {
                        String data = new String(pacote.getData());
                        try {
                            MensagemUtil mensagem = new MensagemUtil();
                            String[] d = data.split("\n\n");
                            System.out.println("Console peer " + peer.getNome() + ": Recebimento de estados em " + d[0] + "\n");
                            mensagem.trataMensagemRecebida(d[1], peer);
                        } catch (Exception e) {
                            System.out.println("Peer recive message Exception:\n" + e.getStackTrace() + "\n" + e.getMessage() + "\n" + e.getClass() + "\n");
                        }


                    }
                };
                t.run();
                
                
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Peer recive Exception", e);
            }
        }

    public void killThread() {
        this.isAlive = false;
        socket.close();
    }
}
