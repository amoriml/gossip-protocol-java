package br.com.ufabc.GossipProtocol.nodes;


import br.com.ufabc.GossipProtocol.model.Peer;
import br.com.ufabc.GossipProtocol.threads.FileSystemHandler;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class PeerB {
    public static void main(String[] args) {
        Peer.iniciaPeers();
        Peer peer = new Peer("PeerB", 2);

        FileSystemHandler fileSystem = new FileSystemHandler(peer);

        System.out.println("DIRETORIO DO PEER: --->>>>>" + fileSystem.getFolderPath() + "\n\n");

        peer.inicia(fileSystem);
    }
}