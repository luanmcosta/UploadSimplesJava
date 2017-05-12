package cliente;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import view.cliente.TelaCliente;

public class Cliente {

    // Atributos
    Socket cliente_servidor;
    FileInputStream entradaArq;
    BufferedInputStream entradaArqBuff;
    OutputStream saidaDados;
    File arquivo;
    byte[] bytes;  
    
    // Métodos 
    
    public void iniciarCliente(){
        
        // Tentar conectar com o servidor
        try {
            cliente_servidor = new Socket("localhost", 20171);
        } catch (IOException ex) {
            System.out.println("Erro ao tentar se conectar com o servidor!");
        }
        
        // Tentar criar canal de saida de dados para o servidor
        try {      
            saidaDados = cliente_servidor.getOutputStream();
        } catch (IOException ex) {
            System.out.println("Erro ao tentar criar canal de saida com o servidor");
        }
        
        // Iniciar Interface 
        TelaCliente tc = new TelaCliente(this);
        tc.setVisible(true);
    
    }
    
    public void enviarArquivo(){

        // Tentar instanciar canal de entrada do arquivo a partir do meu arquivo local
        try {    
            entradaArq = new FileInputStream(arquivo);
        } catch (FileNotFoundException ex) {
            System.out.println("Erro ao tentar instanciar canal com o arquivo");
        }
        
        // Instanciar canal de buffer de entrada com meu canal do arquivo
        entradaArqBuff = new BufferedInputStream(entradaArq);
        
        // Tentar ler meu arquivo no canal bufferizado e armazenar no vetor de bytes
        try {          
            entradaArqBuff.read(bytes, 0, bytes.length);
        } catch (IOException ex) {
            System.out.println("Erro ao tentar ler canal de buffer do arquivo");
        }
        
        // Tentar enviar vetor de bytes bufferizados para o servidor
        try {     
            saidaDados.write(bytes, 0, bytes.length);
        } catch (IOException ex) {
            System.out.println("Erro ao tentar enviar arquivo ao servidor (erro ao enviar buffer de bytes)");
        }
        
        // Encerrar conexões  
        try {
            cliente_servidor.close();
            saidaDados.flush();
        } catch (IOException ex) {
            System.out.println("Problema ao limpar e encerrar conexões");
        }
    }
    
    // Getters e Setters

    public File getArquivo() {
        return arquivo;
    }

    public void setArquivo(File arquivo) {
        this.arquivo = arquivo;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }
    
    
}
