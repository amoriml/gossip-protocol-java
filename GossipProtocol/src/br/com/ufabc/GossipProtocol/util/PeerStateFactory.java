package br.com.ufabc.GossipProtocol.util;

import java.util.List;

import br.com.ufabc.GossipProtocol.model.FileAtt;
import br.com.ufabc.GossipProtocol.model.PeerState;

public abstract class PeerStateFactory {

    public static PeerState create(List<FileAtt> metadados, String peerName) {
        PeerState state = new PeerState();
        state.setStateOfNode(metadados);
        state.setNodeName(peerName);
        state.uptadeVersion();
        return state;
    }
}
