package br.com.ufabc.GossipProtocol.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PeerState {

	private List<FileAtt> filesStateList;
	private String peerName;
	private Date version;

	public PeerState() {
		filesStateList = new ArrayList<>();
	}

	public List<FileAtt> getStateOfNode() {
		return filesStateList;
	}

	public void setStateOfNode(List<FileAtt> stateOfNode) {
		this.filesStateList = stateOfNode;
	}

	public String getNodeName() {
		return peerName;
	}

	public void setNodeName(String nodeName) {
		this.peerName = nodeName;
	}

	public Date getVersion() {
		return version;
	}

	public void uptadeVersion() {
		this.version = new Date();
	}

	
}
