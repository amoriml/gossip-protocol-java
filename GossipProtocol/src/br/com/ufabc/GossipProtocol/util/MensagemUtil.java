package br.com.ufabc.GossipProtocol.util;

import br.com.ufabc.GossipProtocol.model.Peer;
import br.com.ufabc.GossipProtocol.model.PeerState;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MensagemUtil {

    public void trataMensagemRecebida(String data, Peer peer) {
        HashMap<Integer, PeerState> estadosRecebidos = JSONParse.JsonToHash(data.trim());
        System.out.println("MensagemUtil: inicio do tratamento mensagem");
        for (Map.Entry<Integer, PeerState> entidade : Objects.requireNonNull(estadosRecebidos).entrySet()) {
            if (peer.estados.containsKey(entidade.getKey())) {
                if (isNewState(peer.getEstados().get(entidade.getKey()), entidade.getValue(), peer)) {
                    peer.estados.replace(entidade.getKey(), entidade.getValue());
                }
            } else {
                peer.estados.put(entidade.getKey(), entidade.getValue());
            }
            
            System.out.println("MensagemUtil "+ peer.getNome()+": tratando mensagem");
        }
    }

    private boolean isNewState(PeerState estadoAtual, PeerState estadoRecebido, Peer peer) {
        if (estadoAtual.getVersion().equals(estadoRecebido.getVersion())) {
            System.out.println("PeerReciveHandler " + peer.getNome() + ": O estado atual do peer " + estadoRecebido.getNodeName() + " eh igual ao estado recebido");
            return false;
        } else if (estadoAtual.getVersion().after(estadoRecebido.getVersion())) {
            System.out.println("PeerReciveHandler " + peer.getNome() + ": O estado atual do peer " + estadoRecebido.getNodeName() + " eh mais recente do que o estado recebido");
            return false;
        }
        return true;
    }
    
}
