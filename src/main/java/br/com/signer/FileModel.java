package br.com.signer;

public class FileModel {

	private Long id;
	private String name;
	private String paciente;
	private String dataPedido;

	public FileModel(Long id, String name, String paciente, String dataPedido) {
		super();
		this.id = id;
		this.name = name;
		this.paciente = paciente;
		this.dataPedido = dataPedido;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getPaciente() {
		return paciente;
	}

	public String getDataPedido() {
		return dataPedido;
	}

	@Override
	public String toString() {
		return name;
	}

}
