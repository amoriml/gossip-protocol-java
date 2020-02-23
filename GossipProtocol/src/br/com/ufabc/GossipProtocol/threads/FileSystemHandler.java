package br.com.ufabc.GossipProtocol.threads;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import br.com.ufabc.GossipProtocol.model.FileAtt;
import br.com.ufabc.GossipProtocol.model.Peer;
import br.com.ufabc.GossipProtocol.model.PeerState;
import br.com.ufabc.GossipProtocol.util.PeerStateFactory;

public class FileSystemHandler implements Runnable {

	private String folderPath;
	private List<FileAtt> metadata;
	private Peer handlerPeer;

	public FileSystemHandler(Peer handlerPeer) {
		this.folderPath = System.getProperty("user.home") + File.separator + "GossipProtocol" + File.separator
				+ handlerPeer.getNome();
		this.handlerPeer = handlerPeer;
		this.metadata = new ArrayList<FileAtt>();
	}

	@Override
	public void run() {
		try {
			File folder = new File(folderPath);
			File[] filesList = folder.listFiles();
			//limpar lista para obter nova lista
			this.metadata.clear();
			
			if (filesList != null) {
				for (File f : filesList) {
					BasicFileAttributes atributes = Files.readAttributes(f.toPath(), BasicFileAttributes.class);
					FileAtt fileAtt = new FileAtt(atributes);
					this.metadata.add(fileAtt);
					System.out.println("FileSystemHandler " + handlerPeer.getNome() + ": thread "
							+ Thread.currentThread().getName() + ":arquivo: " + f.getName() + " atualizado");
				}
			}

			if (handlerPeer.estados.containsKey(handlerPeer.getPort())) {
				handlerPeer.estados.replace(handlerPeer.getPort(),
						PeerStateFactory.create(this.metadata, handlerPeer.getNome()));
			} else {
				handlerPeer.estados.put(handlerPeer.getPort(),
						PeerStateFactory.create(this.metadata, handlerPeer.getNome()));
			}

			verificaUltimaAtualizacao();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("FileSystemHandler" + handlerPeer.getNome() + ": thread: "
					+ Thread.currentThread().getName() + " Exception: " + e.getClass() + "\n" + e.getStackTrace());
			throw new RuntimeException("File reader Exception", e);
		}
	}

	public String getFolderPath() {
		return this.folderPath;
	}

	public void verificaUltimaAtualizacao() {
		HashMap<Integer, PeerState> estados = handlerPeer.getEstados();
		Date now = new Date();
		long MAX_DURATION = TimeUnit.MINUTES.toMillis(1);

		try {
			for (Map.Entry<Integer, PeerState> entidade : Objects.requireNonNull(estados).entrySet()) {
				Date date = entidade.getValue().getVersion();
				if (date != null) {
					long duration = now.getTime() - date.getTime();
					if (duration > MAX_DURATION) {
						estados.remove(entidade.getKey());
						System.out.println("FileSystemHandler " + handlerPeer.getNome() + ": Eliminando o estado de "
								+ entidade.getValue().getNodeName());
					}
				}
			}
		} catch (Exception e) {
			System.out.println(e.getClass() + "\n" + e.getStackTrace() + "\n" + e.getMessage());
		}

		return;
	}
}
