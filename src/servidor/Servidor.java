package servidor;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    
    // Atributos
    private ServerSocket servidor;
    private Socket cliente;
    private FileOutputStream arqLocal;
    private BufferedOutputStream saidaArq;
    private String caminho_arquivo;
    private InputStream entradaDados;
    private byte[] bytes;
    
    public void iniciarServidor(){
        try {
            // Iniciar Socket de Servidor
            servidor = new ServerSocket(20171);

            // Aguardar cliente
            cliente = servidor.accept();
            
            // Instanciar canal de entrada
            entradaDados = cliente.getInputStream(); 
            
            // Instanciar local do arquivo recebido
            arqLocal = new FileOutputStream("recebido.txt");
            
            // Instanciar canal local com buffer
            saidaArq = new BufferedOutputStream(arqLocal);
            
            // Instanciar vetor de bytes
            bytes = new byte[4096];
  
            // Ler arquivo
            int bytesLidos = entradaDados.read(bytes, 0, bytes.length);
            
            // Escrever meus bytes (através do buffer) no arquivo local
            saidaArq.write(bytes, 0, bytesLidos);
            
            // Fechar instancias
            saidaArq.close();
            cliente.close();

        } catch (IOException ex) {
            System.out.println("Erro ao iniciar servidor");
        }
        
    }
    
}
