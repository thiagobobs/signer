package br.com.signer;

public class FileModel {

	private Long id;
	private String name;

	public FileModel(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
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

	@Override
	public String toString() {
		return "FileModel [id=" + id + ", name=" + name + "]";
	}

}
