package br.com.ufabc.GossipProtocol.model;

import java.nio.file.attribute.BasicFileAttributes;
import java.util.Date;

public class FileAtt {
	public Date dataCriacao;
	public Date ultimoAcesso;
	public long tamanhoArquivo;
	public Date ultimaModificacao;
	public Object fileKey;

	public FileAtt() {
		
	}
	
	public FileAtt(BasicFileAttributes atributes) {
		dataCriacao = new Date(atributes.creationTime().toMillis());
		ultimoAcesso = new Date(atributes.lastAccessTime().toMillis());
		tamanhoArquivo = atributes.size();
		ultimaModificacao = new Date(atributes.lastModifiedTime().toMillis());
		fileKey = atributes.fileKey();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof FileAtt)) {
			return false;
		}
		return this.getFileKey().toString().equals(((FileAtt) obj).getFileKey().toString());
	}
	
	public Object getFileKey() {
		return fileKey;
	}
}
