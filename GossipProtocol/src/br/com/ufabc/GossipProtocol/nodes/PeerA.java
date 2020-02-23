package br.com.ufabc.GossipProtocol.nodes;

import br.com.ufabc.GossipProtocol.model.Peer;
import br.com.ufabc.GossipProtocol.threads.FileSystemHandler;

public class PeerA {
    public static void main(String[] args) {
        Peer.iniciaPeers();
        Peer peer = new Peer("PeerA", 1);

        FileSystemHandler fileSystem = new FileSystemHandler(peer);

        System.out.println("DIRETORIO DO PEER: --->>>>>" + fileSystem.getFolderPath() + "\n");

        peer.inicia(fileSystem);
    }
}
