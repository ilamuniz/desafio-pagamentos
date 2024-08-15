package br.com.bb.f4353448.scripts;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;
import java.util.Scanner;

public class DataGenerator {

    private static volatile boolean running = true;

    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            String url = "jdbc:mysql://localhost:3306/pagamentos";
            String user = "f4353448";
            String password = "wsl";

            try (Connection connection = DriverManager.getConnection(url, user, password)) {
                String sql = "INSERT INTO Cartao (tipo_de_pessoa, cpf_cnpj, pagamento, nome_do_titular, numero_do_cartao, mes_vencimento, ano_vencimento, codigo_de_seguranca) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement statement = connection.prepareStatement(sql);

                Random random = new Random();

                while (running) {
                    int tipoPessoa = random.nextInt(2) + 1;
                    String cpfOuCnpj = gerarCpfOuCnpj(tipoPessoa);
                    BigDecimal pagamento = BigDecimal.valueOf(random.nextDouble() * 1000).setScale(2, BigDecimal.ROUND_HALF_UP);
                    String nomeTitular = gerarNomeTitular(tipoPessoa);
                    String numeroDoCartao = gerarNumeroDoCartao();
                    int mesVencimento = random.nextInt(12) + 1;
                    int anoVencimento = 2025 + random.nextInt(10); // Ano acima do atual
                    String codigoDeSeguranca = gerarCodigoDeSeguranca();

                    statement.setInt(1, tipoPessoa);
                    statement.setString(2, cpfOuCnpj);
                    statement.setBigDecimal(3, pagamento);
                    statement.setString(4, nomeTitular);
                    statement.setString(5, numeroDoCartao);
                    statement.setInt(6, mesVencimento);
                    statement.setInt(7, anoVencimento);
                    statement.setString(8, codigoDeSeguranca);

                    statement.executeUpdate();

                    // Pausa para evitar inserções muito rápidas
                    Thread.sleep(1000);
                    System.out.println("Executando...");
                }

                System.out.println("Script parado.");

            } catch (SQLException | InterruptedException e) {
                e.printStackTrace();
            }
        });

        thread.start();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Pressione ENTER para parar o script.");
        scanner.nextLine();

        running = false;
    }

    private static String gerarCpfOuCnpj(int tipoPessoa) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        int length = (tipoPessoa == 1) ? 11 : 14;
        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    private static String gerarNomeTitular(int tipoPessoa) {
        if (tipoPessoa == 1) {
            return "Nome Pessoa " + new Random().nextInt(1000);
        } else {
            return "Empresa " + new Random().nextInt(1000);
        }
    }

    private static String gerarNumeroDoCartao() {
        Random random = new Random();
        int length = 13 + random.nextInt(7);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    private static String gerarCodigoDeSeguranca() {
        Random random = new Random();
        int length = 3 + random.nextInt(2);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

}
