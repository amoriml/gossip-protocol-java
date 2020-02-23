package br.com.ufabc.GossipProtocol.model;

import br.com.ufabc.GossipProtocol.threads.FileSystemHandler;
import br.com.ufabc.GossipProtocol.threads.PeerReciveHandler;
import br.com.ufabc.GossipProtocol.threads.PeerSendHandler;
import br.com.ufabc.GossipProtocol.util.ExecutaThreadFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Peer {


    private static ArrayList<Integer> ports = new ArrayList<>();

    public HashMap<Integer, PeerState> estados;
    private String nome;
    private int port;

    public Peer() {
        estados = new HashMap<>();
    }

    public Peer(String nome, int port) {
        this.nome = nome;
        this.port = ports.get(port - 1);
        estados = new HashMap<>();
    }

    public static void iniciaPeers() {
        int initialPort = 9876;
        ports.add(initialPort);
        ports.add(initialPort + 1);
        ports.add(initialPort + 2);
        ports.add(initialPort + 3);
        ports.add(initialPort + 4);
    }

    public static int randomPort(int port) {
        Random rand = new Random();
        int portaAlvo;
        do {
            portaAlvo = ports.get(rand.nextInt(5));
        } while (portaAlvo == port);
        return portaAlvo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void inicia(FileSystemHandler fileSystem) {
        PeerSendHandler sendPeer1 = new PeerSendHandler(this);
        PeerSendHandler sendPeer2 = new PeerSendHandler(this);
        PeerReciveHandler recieverPeer = new PeerReciveHandler(this);

        ScheduledExecutorService executor = 
        		Executors.newSingleThreadScheduledExecutor(
        				new ExecutaThreadFactory("fileSystem"));
        ScheduledExecutorService executor2 = 
        		Executors.newSingleThreadScheduledExecutor(
        				new ExecutaThreadFactory("sendHandler"));
        ScheduledExecutorService executor3 = 
        		Executors.newSingleThreadScheduledExecutor(
        				new ExecutaThreadFactory("sendHandler"));
        ScheduledExecutorService executor4 = 
        		Executors.newSingleThreadScheduledExecutor(
        				new ExecutaThreadFactory("receiverHandler"));

        executor.scheduleAtFixedRate(fileSystem, 0, 3, TimeUnit.SECONDS);
        executor2.scheduleAtFixedRate(sendPeer1, 0, 5, TimeUnit.SECONDS);
        executor3.scheduleAtFixedRate(sendPeer2, 0, 6, TimeUnit.SECONDS);
        executor4.scheduleAtFixedRate(recieverPeer, 0, 1, TimeUnit.SECONDS);
    }

    public HashMap<Integer, PeerState> getEstados() {
        return estados;
    }

}
