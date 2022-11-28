package br.com.signer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.signer.client.dto.PacienteDto;
import br.com.signer.client.dto.RespostaDto;

public class ApiClient implements Serializable {

	private static final long serialVersionUID = 1L;
//	private static final List<FileModel> FILES;

//	static {
//		FILES = new LinkedList<>();
//
//		for (int i = 1; i <= 50; i++) {
//			FILES.add(new FileModel(Long.valueOf(i), "Receituario_" + i + ".pdf", "Fulano Beltrano da Silva"));
//		}
//	}

	private List<PacienteDto> autenticate(String instancia, String cpf, String senha) throws IOException {
		ObjectMapper objMapper = new ObjectMapper();
		Authenticate auth = new Authenticate(instancia, cpf, senha);

		HttpPost post = new HttpPost("http://212.47.238.224/infoservices/infocert/api.php");
		post.setEntity(new StringEntity(objMapper.writeValueAsString(auth)));

		try (
				CloseableHttpClient httpClient = HttpClients.createDefault();
				CloseableHttpResponse response = httpClient.execute(post);
			) 
		{
			RespostaDto resposta = objMapper.readValue(response.getEntity().getContent(), RespostaDto.class);

			if (BooleanUtils.isTrue(resposta.getSuccess())) {
				return resposta.getPacientes();
			}

			throw new IOException(resposta.getError());
		}
	}

	public List<FileModel> getFiles(String instancia, String cpf, String senha) throws IOException {
		List<FileModel> files = new ArrayList<>();

//		try {
		List<PacienteDto> pacientes = this.autenticate(instancia, cpf, senha);

		pacientes.forEach(p -> {
			p.getReceitas().forEach(r -> {
				files.add(new FileModel(Long.valueOf(p.getId()), r.getPdf(), p.getNome(), new SimpleDateFormat("dd/MM/yyyy").format(r.getPedido())));					
			});
		});
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		this.downloadFile(pacientes.get(0).getReceitas().get(0).getPdf());
//		return Collections.unmodifiableList(FILES);
		return files;
	}

	public void downloadFile(String uri) throws IOException {
		HttpGet get = new HttpGet(uri);
				
		try (
				CloseableHttpClient httpClient = HttpClients.createDefault();
				CloseableHttpResponse response = httpClient.execute(get);
			) 
		{
			OutputStream outStream = new FileOutputStream(new File("/home/thiago/Downloads/InfoDental/" + uri.substring(uri.lastIndexOf("/") + 1)));
			response.getEntity().writeTo(outStream);
			outStream.close();
		}
	}

	private class Authenticate {

		private String token = "c20ef152b81e559ef400f564fd0e14651a4e6e76";
		private String act = "autenticacao";
		private String instancia;
		private String cpf;
		private String senha;

		public Authenticate(String instancia, String cpf, String senha) {
			this.instancia = instancia;
			this.cpf = cpf;
			this.senha = senha;
		}

		public String getToken() {
			return token;
		}

		public String getAct() {
			return act;
		}

		public String getInstancia() {
			return instancia;
		}

		public String getCpf() {
			return cpf;
		}

		public String getSenha() {
			return senha;
		}

	}

}
