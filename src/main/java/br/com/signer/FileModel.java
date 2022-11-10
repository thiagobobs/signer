package br.com.signer;

public class FileModel {

	private Long id;
	private String name;
	private String paciente;

	public FileModel(Long id, String name, String paciente) {
		super();
		this.id = id;
		this.name = name;
		this.paciente = paciente;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPaciente() {
		return paciente;
	}

	public void setPaciente(String paciente) {
		this.paciente = paciente;
	}

}
