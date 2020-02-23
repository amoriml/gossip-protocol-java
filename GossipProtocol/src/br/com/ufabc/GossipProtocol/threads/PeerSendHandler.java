package br.com.ufabc.GossipProtocol.threads;

import br.com.ufabc.GossipProtocol.model.Peer;
import br.com.ufabc.GossipProtocol.util.JSONParse;
import br.com.ufabc.GossipProtocol.util.PacoteFactory;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.UnknownHostException;

public class PeerSendHandler implements Runnable {
	
	private String destinyHost;
	private int destinyPort;
	private Peer peer;

    public PeerSendHandler(Peer peer) {
		this.peer = peer;
		this.destinyHost = "localhost";
    }

    @Override
    public void run() {
        try {
            handle();
        } catch (IOException e) {
        	e.printStackTrace();
        	System.out.println("PeerSendHandler " + peer.getNome() + "\n" +
        			"Thread: " + Thread.currentThread().getName() + 
        			"Erro no Handler");
			throw new RuntimeException("Peer send Exception", e);
        }
    }

    /**
     * Cria a mensagem que sera enviada e envia para o destino
     * @throws IOException 
     * @throws UnknownHostException 
     */
    private void handle() throws UnknownHostException, IOException {
        String message = JSONParse.HashToJson(peer.estados);
        message = peer.getPort() + "\n\n" + message;
        System.out.println("Estado atual de " + message);
        DatagramSocket socket = PacoteFactory.createSocket();
        destinyPort = Peer.randomPort(peer.getPort());
        socket.send(PacoteFactory.createPackage(message, destinyHost, destinyPort));
        System.out.println("PeerSendHandler " + peer.getNome() + ": Enviando estado atual para -> " + destinyPort);
    }

	
}
