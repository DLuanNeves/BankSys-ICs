

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Classe Pessoa - representa o cliente
class Pessoa {
    private String nome;
    private String cpf;

    public Pessoa(String nome, String cpf) {
        this.nome = nome;
        this.cpf = cpf;
    }

    public String getNome() { return nome; }
    public String getCpf()  { return cpf;  }
}

// Classe Conta - representa a conta bancária
class Conta {
    private int numero;
    private Pessoa titular;
    private double saldo;

    public Conta(int numero, Pessoa titular) {
        this.numero = numero;
        this.titular = titular;
        this.saldo = 0.0;
    }

    public int getNumero()     { return numero; }
    public Pessoa getTitular() { return titular; }
    public double getSaldo()   { return saldo; }

    // Depositar dinheiro
    public void depositar(double valor) {
        if (valor > 0) {
            saldo += valor;
            System.out.println("Depósito de R$ " + valor + " realizado com sucesso na conta " + numero + " do titular " + titular.getNome() + "!");
        } else {
            System.out.println("Valor inválido para depósito.");
        }
    }

    // Sacar dinheiro (retorna true se conseguir)
    public boolean sacar(double valor) {
        if (valor > 0 && valor <= saldo) {
            saldo -= valor;
            System.out.println("Saque de R$ " + valor + " realizado com sucesso na conta " + numero + " do titular " + titular.getNome() + "!");
            return true;
        } else {
            System.out.println("Saldo insuficiente ou valor inválido.");
            return false;
        }
    }

    // Transferir dinheiro para outra conta
    public void transferir(Conta destino, double valor) {
        if (this.sacar(valor)) {
            destino.depositar(valor);
            System.out.println("Transferência de R$ " + valor + " realizada com sucesso da conta " + this.numero + 
                             " (" + this.titular.getNome() + ") para a conta " + destino.getNumero() + 
                             " (" + destino.getTitular().getNome() + ")!");
        } else {
            System.out.println("Transferência não realizada.");
        }
    }

    // Mostrar dados da conta
    public void mostrarDados() {
        System.out.println("\nConta nº: " + numero);
        System.out.println("Titular : " + titular.getNome());
        System.out.println("CPF     : " + titular.getCpf());
        System.out.println("Saldo   : R$ " + saldo);
    }
}

// Classe AgenciaBancaria - gerencia as contas
class AgenciaBancaria {
    private String nomeAgencia;
    private List<Conta> contas;

    public AgenciaBancaria(String nomeAgencia) {
        this.nomeAgencia = nomeAgencia;
        this.contas = new ArrayList<>();
    }

    public void boasVindas() {
        System.out.println("------------------------------------------------------");
        System.out.println("-------------Bem vindos a nossa Agência---------------");
        System.out.println("------------------------------------------------------");
        System.out.println("Agência: " + nomeAgencia);
        System.out.println("------------------------------------------------------");
    }

    // Cadastrar uma nova conta (evita duplicidade de número)
    public void cadastrarConta(Conta conta) {
        if (buscarConta(conta.getNumero()) != null) {
            System.out.println("Já existe uma conta com esse número.");
            return;
        }
        contas.add(conta);
        System.out.println("Conta cadastrada com sucesso!");
    }

    // Buscar conta pelo número
    public Conta buscarConta(int numero) {
        for (Conta conta : contas) {
            if (conta.getNumero() == numero) {
                return conta;
            }
        }
        return null;
    }

    // Listar todas as contas
    public void listarContas() {
        if (contas.isEmpty()) {
            System.out.println("Nenhuma conta cadastrada.");
        } else {
            System.out.println("\n----- LISTA DE CONTAS -----");
            for (Conta conta : contas) {
                conta.mostrarDados();
                System.out.println("---------------------------");
            }
        }
    }
}

// Classe principal - o nome do arquivo deve ser ProgramaPrincipal.java
public class ProgramaPrincipal {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AgenciaBancaria agencia = new AgenciaBancaria("Agência Central");
        agencia.boasVindas();

        int opcao;
        do {
            System.out.println("\n***** Selecione uma operação que deseja realizar *****");
            System.out.println("------------------------------------------------------");
            System.out.println("|   Opção 1 - Criar conta   |");
            System.out.println("|   Opção 2 - Depositar     |");
            System.out.println("|   Opção 3 - Sacar         |");
            System.out.println("|   Opção 4 - Transferir    |");
            System.out.println("|   Opção 5 - Listar        |");
            System.out.println("|   Opção 6 - Sair          |");
            System.out.println("------------------------------------------------------");
            System.out.print("Escolha uma opção: ");

            while (!scanner.hasNextInt()) {
                System.out.print("Opção inválida. Digite um número: ");
                scanner.next();
            }
            opcao = scanner.nextInt();
            scanner.nextLine(); // limpar o \n

            switch (opcao) {
                case 1: // Cadastro
                    System.out.println("\n----- CRIAR CONTA -----");
                    System.out.print("Número da conta: ");
                    while (!scanner.hasNextInt()) {
                        System.out.print("Número inválido. Tente novamente: ");
                        scanner.next();
                    }
                    int numero = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Nome do titular: ");
                    String nome = scanner.nextLine();

                    System.out.print("CPF do titular: ");
                    String cpf = scanner.nextLine();

                    Pessoa pessoa = new Pessoa(nome, cpf);
                    Conta novaConta = new Conta(numero, pessoa);
                    agencia.cadastrarConta(novaConta);
                    break;

                case 2: // Depósito
                    System.out.println("\n----- DEPOSITAR -----");
                    System.out.print("Número da conta: ");
                    int numDeposito = scanner.nextInt();
                    Conta contaDep = agencia.buscarConta(numDeposito);
                    if (contaDep != null) {
                        System.out.print("Valor do depósito: ");
                        while (!scanner.hasNextDouble()) {
                            System.out.print("Valor inválido. Tente novamente: ");
                            scanner.next();
                        }
                        double valorDep = scanner.nextDouble();
                        contaDep.depositar(valorDep);
                    } else {
                        System.out.println("Conta não encontrada.");
                    }
                    break;

                case 3: // Saque
                    System.out.println("\n----- SACAR -----");
                    System.out.print("Número da conta: ");
                    int numSaque = scanner.nextInt();
                    Conta contaSaq = agencia.buscarConta(numSaque);
                    if (contaSaq != null) {
                        System.out.print("Valor do saque: ");
                        while (!scanner.hasNextDouble()) {
                            System.out.print("Valor inválido. Tente novamente: ");
                            scanner.next();
                        }
                        double valorSaq = scanner.nextDouble();
                        contaSaq.sacar(valorSaq);
                    } else {
                        System.out.println("Conta não encontrada.");
                    }
                    break;

                case 4: // Transferência
                    System.out.println("\n----- TRANSFERIR -----");
                    System.out.print("Conta de origem: ");
                    int numOrigem = scanner.nextInt();
                    Conta contaOrigem = agencia.buscarConta(numOrigem);

                    System.out.print("Conta de destino: ");
                    int numDestino = scanner.nextInt();
                    Conta contaDestino = agencia.buscarConta(numDestino);

                    if (contaOrigem != null && contaDestino != null) {
                        System.out.print("Valor da transferência: ");
                        while (!scanner.hasNextDouble()) {
                            System.out.print("Valor inválido. Tente novamente: ");
                            scanner.next();
                        }
                        double valorTransf = scanner.nextDouble();
                        contaOrigem.transferir(contaDestino, valorTransf);
                    } else {
                        System.out.println("Conta de origem ou destino não encontrada.");
                    }
                    break;

                case 5: // Listar contas
                    System.out.println("\n----- LISTAR CONTAS -----");
                    agencia.listarContas();
                    break;

                case 6:
                    System.out.println("\nEncerrando o programa. Até logo!");
                    break;

                default:
                    System.out.println("Opção inválida, tente novamente.");
                    break;
            }

        } while (opcao != 6);

        scanner.close();
    }
}