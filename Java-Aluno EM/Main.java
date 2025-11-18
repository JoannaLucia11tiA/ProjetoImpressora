import com.sun.jna.Library;
import com.sun.jna.Native;
import java.util.Scanner;
import javax.swing.JFileChooser;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.io.FileInputStream;

public class Main {

    // Interface que representa a DLL, usando JNA
    public interface ImpressoraDLL extends Library {

        // Caminho completo para a DLL
        ImpressoraDLL INSTANCE = (ImpressoraDLL) Native.load(
                "C:\\Users\\joann\\Downloads\\Java-Aluno EM\\Java-Aluno EM\\E1_Impressora01.dll",
                ImpressoraDLL.class
        );

        int AbreConexaoImpressora(int tipo, String modelo, String conexao, int param);

        int FechaConexaoImpressora();

        int ImpressaoTexto(String dados, int posicao, int estilo, int tamanho);

        int Corte(int avanco);

        int ImpressaoQRCode(String dados, int tamanho, int nivelCorrecao);

        int ImpressaoCodigoBarras(int tipo, String dados, int altura, int largura, int HRI);

        int AvancaPapel(int linhas);

        int StatusImpressora(int param);

        int AbreGavetaElgin();

        int AbreGaveta(int pino, int ti, int tf);

        int SinalSonoro(int qtd, int tempoInicio, int tempoFim);

        int ModoPagina();

        int LimpaBufferModoPagina();

        int ImprimeModoPagina();

        int ModoPadrao();

        int PosicaoImpressaoHorizontal(int posicao);

        int PosicaoImpressaoVertical(int posicao);

        int ImprimeXMLSAT(String dados, int param);

        int ImprimeXMLCancelamentoSAT(String dados, String assQRCode, int param);
    }

    private static boolean conexaoAberta = false;
    private static int tipo;
    private static String modelo;
    private static String conexao;
    private static int parametro;
    private static final Scanner scanner = new Scanner(System.in);

    private static String capturarEntrada(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine();
    }

    public static void configurarConexao() {
        if (!conexaoAberta) {
            Scanner scanner = new Scanner(System.in);

            System.out.println("Digite o tipo de conexão (ex: 1 para USB, 2 para serial, etc.): ");

            tipo = scanner.nextInt();
            System.out.println("Digite o modelo");
            scanner.nextLine();
            modelo = scanner.nextLine();

            System.out.println("Digite a conexão");
            conexao = scanner.nextLine();
            System.out.println("Digite o parametro");
            parametro = scanner.nextInt();

            System.out.println("\n====================================");
            System.out.println(" Conexão configurada com sucesso!");
            System.out.println(" Tipo: " + tipo);
            System.out.println(" Modelo: " + modelo);
            System.out.println(" Conexão: " + conexao);
            System.out.println(" Parâmetro: " + parametro);
            System.out.println("====================================\n");


        }
    }

    public static void abrirConexao () {

        //sempre que for chamar uma funçao da biblioteca, usar como abaixo (ImpressoraDLL.INSTANCE.nomeDaFuncao)

        if (!conexaoAberta) {
            int retorno = ImpressoraDLL.INSTANCE.AbreConexaoImpressora(tipo, modelo, conexao, parametro);
            if (retorno == 0) {
                conexaoAberta = true;
                System.out.println("Conexão aberta com sucesso.");
            } else {
                System.out.println("Erro ao abrir conexão. Código de erro: " + retorno);
            }
        } else {
            System.out.println("Conexão já está aberta.");
        }
    }


    public static void fecharConexao () {
        if (conexaoAberta) {
            int retorno = ImpressoraDLL.INSTANCE.FechaConexaoImpressora();

            if (retorno == 0) {
                conexaoAberta = false;
                System.out.println("Conexão fechada com sucesso.");
            } else {
                System.out.println("Erro ao fechar conexão. Código de erro: " + retorno);
            }
        } else {
            System.out.println("Nenhuma conexão está aberta.");
        }
    }

    public static void SinalSonoro() {
        if (conexaoAberta) {
            int retorno = ImpressoraDLL.INSTANCE.SinalSonoro(4, 5, 5);

            if (retorno == 0) {
                System.out.println("Sinal sonoro emitido.");
            } else {
                System.out.println("Erro ao emitir sinal sonoro. Código de erro: " + retorno);
            }
        } else {
            System.out.println("Precisa abrir a conexão primeiro.");
        }

    }
    public static void ImpressaoTexto() {
        if (conexaoAberta) {
            int retorno = ImpressoraDLL.INSTANCE.ImpressaoTexto(
                    "Teste de impressao",1, 4, 0
            );

            if (retorno == 0) {
                System.out.println("Texto impresso com sucesso.");
            } else {
                System.out.println("Erro ao imprimir texto. Código de erro: " + retorno);
            }
        } else {
            System.out.println("Precisa abrir a conexão primeiro.");
        }
    }


    public static void ImpressaoQRCode() {
        if (conexaoAberta) {
            int retorno = ImpressoraDLL.INSTANCE.ImpressaoQRCode("Teste de impressao", 6, 4);
            if (retorno == 0) {
                System.out.println("Impressao de QRCode");
            } else {
                System.out.println("Erro ao Imprimir o QRCode. Código de erro: " + retorno);
            }
        } else {
            System.out.println("Precisa abrir a conexao primeiro. ");
        }
    }

    public static void abreGaveta() {

        try {

            int statusGaveta = ImpressoraDLL.INSTANCE.AbreGaveta(1, 5, 10);
            int retorno = statusGaveta;

            if(retorno == 0) {

                System.out.println("Requisição bem sucedida!!!");

            } else {

                System.out.println("Erro na camada interna do sistema. ERRO: " + retorno);

            }

        } catch (Exception e) {

            System.out.println("Ocorreu um erro interno na execução da função!!!");

        }


    }

    public static void AbreGavetaElgin() {
        if (conexaoAberta) {
            int retorno = ImpressoraDLL.INSTANCE.AbreGavetaElgin();
            if (retorno == 0) {
                System.out.println("Gaveta aberta elgin");
            } else {
                System.out.println("Erro abrir gaveta elgin. Código de erro: " + retorno);
            }
        } else {
            System.out.println("Precisa abrir a conexao primeiro.");
        }
    }

    public static void impressaoCodigoBarras() {

        try {

            // Criando instância da função presente no arquivo DLL
            int statusImpressao = ImpressoraDLL.INSTANCE.ImpressaoCodigoBarras(8, "{A012345678912", 100, 2, 3);

            // Processo de refatoração de código para bom entendimento
            int retorno = statusImpressao;


            if (retorno == 0) {

                System.out.println("Requisição de impressão bem sucedida!!!");

            } else {

                System.out.println("Requisição de impressão mal sucedida!!! ERRO: " + retorno);

            }

        } catch (Exception e) {

            System.out.println("Ocorreu um erro interno na execução da função!!!");

        }

    }

    public static void AvancaPapel() {
        if (!conexaoAberta) {
            System.out.println("Abra a conexão primeiro.");
            return;
        }

        ImpressoraDLL.INSTANCE.AvancaPapel(2);
    }

    //criar o restante das funçoes aqui!

	/* - `ImpressaoTexto()`          ("Teste de impressao", 1, 4, 0);
	- `Corte()`						(2)  usar sempre após a impressao de algum documento
	- `ImpressaoQRCode()`            ("Teste de impressao", 6, 4)
	- `ImpressaoCodigoBarras()`    (8, "{A012345678912", 100, 2, 3)
	- `AvancaPapel()`                 (2)  usar sempre após a impressao de algum documento
	- `AbreGavetaElgin()`            (1, 50, 50)
	- `AbreGaveta()`                  (1, 5, 10)
	- `SinalSonoro()`				 (4,5,5)
	- `ImprimeXMLSAT()`
	- `ImprimeXMLCancelamentoSAT()`    (assQRCode = "Q5DLkpdRijIRGY6YSSNsTWK1TztHL1vD0V1Jc4spo/CEUqICEb9SFy82ym8EhBRZjbh3btsZhF+sjHqEMR159i4agru9x6KsepK/q0E2e5xlU5cv3m1woYfgHyOkWDNcSdMsS6bBh2Bpq6s89yJ9Q6qh/J8YHi306ce9Tqb/drKvN2XdE5noRSS32TAWuaQEVd7u+TrvXlOQsE3fHR1D5f1saUwQLPSdIv01NF6Ny7jZwjCwv1uNDgGZONJdlTJ6p0ccqnZvuE70aHOI09elpjEO6Cd+orI7XHHrFCwhFhAcbalc+ZfO5b/+vkyAHS6CYVFCDtYR9Hi5qgdk31v23w==";)
	*/


    public static void main (String[]args){
        while (true) {
            System.out.println("\n*************************************************");
            System.out.println("**************** MENU IMPRESSORA *******************");
            System.out.println("*************************************************\n");

            System.out.println("1  - Configurar Conexao");
            System.out.println("2  - Abrir Conexao");
            System.out.println("3  - Impressao Texto");
            System.out.println("4 - Impressao QRCode");
            System.out.println("5 - Impressao Cod Barras");
            System.out.println("6 - Impressao XML SAT");
            System.out.println("7 - Impressao XML Canc SAT");
            System.out.println("8 - Abrir Gaveta Elgin");
            System.out.println("9 - Abrir Gaveta");
            System.out.println("10 - Sinal Sonoro");
            System.out.println("0 - Fechar Conexao e Sair");



            String escolha = capturarEntrada("\nDigite a opção desejada: ");

            if (escolha.equals("0")) {
                fecharConexao();
                System.out.println("Programa encerrado.");
                break;
            }

            switch (escolha) {
                case "1":
                    configurarConexao();
                    break;
                case "2":
                    abrirConexao ();
                    break;
                case "3":
                    ImpressaoTexto();
                    break;

                case "4":
                    ImpressaoQRCode();
                    break;
                case "5":
                    impressaoCodigoBarras();
                    break;

                case "6":
                    if (conexaoAberta) {
                        JFileChooser fileChooser = new JFileChooser();
                        fileChooser.setCurrentDirectory(new File(".")); // Diretório atual do programa
                        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Arquivos XML", "xml"));

                        int result = fileChooser.showOpenDialog(null);

                        if (result == JFileChooser.APPROVE_OPTION) {
                            File selectedFile = fileChooser.getSelectedFile();
                            String path = selectedFile.getAbsolutePath();

                            try {
                                String conteudoXML = lerArquivoComoString(path);
                                int retImpXMLSAT = ImpressoraDLL.INSTANCE.ImprimeXMLSAT(conteudoXML, 0);
                                ImpressoraDLL.INSTANCE.Corte(5);
                                System.out.println(retImpXMLSAT == 0 ? "Impressão de XML realizada" : "Erro ao realizar a impressão do XML SAT! Retorno: " + retImpXMLSAT);
                            } catch (IOException e) {
                                System.out.println("Erro ao ler o arquivo XML: " + e.getMessage());
                            }
                        } else {
                            System.out.println("Nenhum arquivo selecionado.");
                        }
                    } else {
                        System.out.println("Erro: Conexão não está aberta.");
                    }
                    break;

                case "7":
                    if (conexaoAberta) {
                        JFileChooser fileChooser = new JFileChooser();
                        fileChooser.setCurrentDirectory(new File(".")); // Diretório atual do programa
                        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Arquivos XML", "xml"));
                        String assQRCode = "Q5DLkpdRijIRGY6YSSNsTWK1TztHL1vD0V1Jc4spo/CEUqICEb9SFy82ym8EhBRZjbh3btsZhF+sjHqEMR159i4agru9x6KsepK/q0E2e5xlU5cv3m1woYfgHyOkWDNcSdMsS6bBh2Bpq6s89yJ9Q6qh/J8YHi306ce9Tqb/drKvN2XdE5noRSS32TAWuaQEVd7u+TrvXlOQsE3fHR1D5f1saUwQLPSdIv01NF6Ny7jZwjCwv1uNDgGZONJdlTJ6p0ccqnZvuE70aHOI09elpjEO6Cd+orI7XHHrFCwhFhAcbalc+ZfO5b/+vkyAHS6CYVFCDtYR9Hi5qgdk31v23w==";

                        int result = fileChooser.showOpenDialog(null);

                        if (result == JFileChooser.APPROVE_OPTION) {
                            File selectedFile = fileChooser.getSelectedFile();
                            String path = selectedFile.getAbsolutePath();

                            try {
                                String conteudoXML = lerArquivoComoString(path);
                                int retImpCanXMLSAT = ImpressoraDLL.INSTANCE.ImprimeXMLCancelamentoSAT(conteudoXML, assQRCode, 0);
                                ImpressoraDLL.INSTANCE.Corte(5);
                                System.out.println(retImpCanXMLSAT == 0 ? "Impressão de XML de Cancelamento realizada" : "Erro ao realizar a impressão do XML de Cancelamento SAT! Retorno: " + retImpCanXMLSAT);
                            } catch (IOException e) {
                                System.out.println("Erro ao ler o arquivo XML: " + e.getMessage());
                            }
                        } else {
                            System.out.println("Nenhum arquivo selecionado.");
                        }
                    } else {
                        System.out.println("Erro: Conexão não está aberta.");
                    }
                    break;


                case "8":
                    AbreGavetaElgin();
                    break;

                case "9":
                    abreGaveta();
                    break;

                case "10":
                    SinalSonoro();
                    break;
                default:
                    System.out.println("OPÇÃO INVÁLIDA");
            }
        }

        scanner.close();
    }

    private static String lerArquivoComoString (String path) throws IOException {
        FileInputStream fis = new FileInputStream(path);
        byte[] data = fis.readAllBytes();
        fis.close();
        return new String(data, StandardCharsets.UTF_8);
    }
}


