/**
 * Aluno: Bruno Yudi Mino Okada
 *
 * Referencia da funcao Hash:
 * https://www.ime.usp.br/~pf/estruturas-de-dados/aulas/st-hash.html
 *
 * Sua tarefa será construir uma hash table usando como estrutura base uma árvore heap que use
 * como índice o resultado de uma função de hash disponível na internet.
 * Esta hash Table deve ser capaz de armazenar senhas (strings) com no mínimo 10 caracteres e
 * no máximo 50 caracteres.
 * No seu trabalho, como comentário, deve constar a referência para a função de hash escolhida
 * para a criação do índice da árvore heap.
 * Você deverá criar, no mínimo, uma função de inclusão, exclusão, e busca. A função de inclusão
 * deve retornar Falso, sempre que o elemento a ser inserido já exista na tabela.
 */

import java.util.ArrayList;
import java.util.List;

class Hash{
    String senha;
    int hash;

    // Construtor Hash
    Hash(String senha){
        this.senha = senha;
        this.hash = calcularHash(senha);
    }

    // Funcao de calculo do hash para strings entre 10 e 50 caracteres
    // Estrutura da funcao adaptada da referencia indicada acima do enunciado
    // Mas a formula do calculo permanece a mesma
    public int calcularHash(String s){
        // Verifica se a string possui entre 10 e 50 caracteres
        if (s.length() >= 10 && s.length() <= 50){
            int h = 0;
            // Para cada caractere, pega o valor do hash anterior, multiplica por 31
            // e soma com o valor ASCII do caractere da posicao atual
            for(int i = 0; i < s.length(); i++){
                h = h*31 + s.charAt(i);
            }
            return h;
        }
        System.out.println("Senha com tamanho invalido!");
        return Integer.MIN_VALUE;
    }
}

class Heap{
    int tamanho;
    int[] array = new int[500];

    // insere um valor determinado no heap
    public boolean inserirValor(int valor){
        for(int i = 0; i < array.length; i++){
            // Se o valor ja esta presente no heap, nao inclui e retorna Falso
            if(array[i] == valor){
                System.out.println("Elemento ja incluso!");
                return false;
            }
        }

        array[tamanho] = valor;
        int indice = tamanho;
        int verticePai = (indice - 1)/2;

        // enquanto o calculo do indice do vertice pai for maior ou igual a zero
        // e valor do vertice pai menor que o vertice em questao
        while(verticePai >= 0 && array[verticePai] < array[indice]){
            // caso satisfaca as condicoes acima,
            // inverte os valores do indice e vertice pai no array
            // desta forma, faz com que o valor do vertice pai seja
            // maior que o valor do vertice em questao
            // garantindo assim que o maior valor fique no topo
            int t = array[verticePai];
            array[verticePai] = array[indice];
            array[indice] = t;

            // calcula novamente para ver se as condicoes ainda
            // sao satisfeitas, e caso seja, continua fazendo
            // as trocas de valores e o calculo das condicoes
            t = verticePai;
            indice = verticePai;
            verticePai = (t - 1)/2;
        }
        tamanho++;
        System.out.println("Elemento incluido com sucesso");
        return true;
    }

    // Retorna o maior valor do array, sendo este o primeiro valor
    public int getMaximo(){
        return array[0];
    }

    // Remove o maior valor do heap
    public int removerMaximo(){
        // pega o maior valor do heap
        int maximo = array[0];
        // copia o ultimo valor para a posição zero
        array[0] = array[tamanho - 1];
        // reduz o tamanho do array
        tamanho = tamanho - 1;
        // chama a funcao maxHeapify
        maxHeapify(0);
        return maximo;
    }

    public boolean removerSenha(String senha){
        Hash hashDeletar = new Hash(senha);
        for(int i = 0; i < array.length; i++){
            if(array[i] == hashDeletar.hash){
                // pega o ultimo valor e coloca na posicao removida
                array[i] = array[tamanho - 1];
                // reduz o tamanho do array
                tamanho = tamanho - 1;

                // Chama a funcao de maxHeapify
                maxHeapify(0);
                System.out.println("Hash removido com sucesso!");
                return true;
            }
        }
        System.out.println("Senha nao encontrada");
        return false;
    }

    public boolean buscarSenha(String senha){
        System.out.println("Busca para senha '" + senha + "':");
        // Para cada senha do array, compara os valores Hash para verificar se
        // a senha existe
        for(int y = 0; y < array.length; y++){
            Hash buscaSenha = new Hash(senha);
            if(array[y] == buscaSenha.hash){
                System.out.println("Diagnostico: Senha Presente");
                System.out.println("Hash da senha = " + buscaSenha.hash);
                System.out.println();
                return true;
            }
        }
        System.out.println("Diagnostico: Senha Ausente");
        System.out.println();
        return false;
    }

    public void maxHeapify(int indice){
        int esquerda, direita;
        // determina qual e o filho da direita e da esquerda
        // ex: posicao 0, filho esq = pos 1 e filho dir = pos 2
        // ex: posicao 3, filho esq = pos 7 e filho dir = pos 8
        esquerda = 2 * indice + 1;
        direita = 2 * indice + 2;

        int maior = indice;
        // se o filho da esquerda for maior que o valor maior
        if(esquerda < this.tamanho && array[esquerda] > array[maior]){
            // armazena o valor da esquerda em maior
            maior = esquerda;
        }
        // se o filho da direita dor maior que o valor maior
        if(direita < this.tamanho && array[direita] > array[maior]){
            // armazena o valor da direita em maior
            maior = direita;
        }
        // se o valor maior for diferente do indice
        if(maior != indice){
            // substitui os valores de indice e maior dentro do array
            int t = array[maior];
            array[maior] = array[indice];
            array[indice] = t;

            // chama a funcao maxHeapify (recursivamente)
            // para verificar se e necessario trocar os valores novamente
            // mas desta vez a partir da posicao trocada e nao do topo
            // (deixar os maiores valores no topo da arvore
            // e os menores mais abaixo)
            maxHeapify(maior);
        }
    }

    // Imprime cada valor do heap
    public void imprimirHeap(){
        for (int i = 0; i < tamanho; i++){
            System.out.println(array[i]);
        }
    }

    public void validarHeap(int indice){
        int esquerda, direita;
        // determina qual e o filho da direita e da esquerda
        // ex: posicao 0, filho esq = pos 1 e filho dir = pos 2
        // ex: posicao 3, filho esq = pos 7 e filho dir = pos 8
        esquerda = 2 * indice + 1;
        direita = 2 * indice + 2;

        int maior = indice;
        // verifica se o valor do filho da esquerda e menor que o valor do pai
        if(esquerda < this.tamanho && array[esquerda] > array[maior]){
            // se for maior, avisa que nao e max heap
            System.out.println("Nao e max heap");
        }
        // verifica se o valor do filho da direita e menor que o valor do pai
        if(direita < this.tamanho && array[direita] > array[maior]){
            // se for maior, avisa que nao e max heap
            System.out.println("Nao e max heap");
        }
    }

    public void construirMaxHeap(int[] array){
        // recebe uma array
        this.array = array;
        // pega o tamanho da array
        this.tamanho = array.length;
        // para cada valor a partir de (tamanho da array / 2) - 1
        // até zero, chama a funcao maxHeapify
        for (int i = tamanho/2 - 1; i >= 0; i--){
            // chama a funcao maxHeapify para posicao do array a partir de (tamanho da array / 2) - 1
            // até a posicao zero (maior valor)
            maxHeapify(i);
            // ao executar a funcao maxHeapify para todos os valores até o topo (maior valor)
            // o heap resultante sera o max heap
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Heap heap = new Heap();
        int[] array = new int[8];
        List<Hash> listaHash = new ArrayList<>();


        // Ao criar um novo hash, ja e automaticamente chamada a funcao calcularHash
        Hash hash1 = new Hash("TestarSenhaHash");
        Hash hash2 = new Hash("SenhaHashTable");
        Hash hash3 = new Hash("EstruturasDeDados");
        Hash hash4 = new Hash("ProgramacaoComEstruturasDeDados");
        Hash hash5 = new Hash("EngenhariaDaComputacao");
        Hash hash6 = new Hash("ProgramacaoComEstruturasDeDadosAvancadas");
        Hash hash7 = new Hash("TrabalhoHashTable");
        Hash hash8 = new Hash("SenhaQualquerParaTeste");
        // hash9 vai avisar que o tamanho da senha e invalido
        Hash hash9 = new Hash("Senha");
        // hash10 quando for incluir o valor no heap vai falhar (return false), pois ja tem no heap
        Hash hash10 = new Hash("TestarSenhaHash");

        listaHash.add(hash1);
        listaHash.add(hash2);
        listaHash.add(hash3);
        listaHash.add(hash4);
        listaHash.add(hash5);
        listaHash.add(hash6);
        listaHash.add(hash7);
        listaHash.add(hash8);
        listaHash.add(hash9);
        listaHash.add(hash10);

        // mostrando o funcionamento da funcao calcularHash apesar do hash
        // ser calculado automaticamente pela funcao no momento da criacao
        System.out.println("\n\nCalculo dos hash:");
        for (int x = 0; x < listaHash.size(); x++){
            int valorHash = listaHash.get(x).calcularHash(listaHash.get(x).senha);
            if (valorHash != 0){
                System.out.println("Valor hash senha '" + listaHash.get(x).senha + "': " + valorHash);
            }
        }

        // Inclusao de valores, caso tenha senhas iguais aparecera uma mensagem e nao incluira o valor
        // (falha na inclusao = return false na funcao de inclusao)
        System.out.println("\nTeste inclusao valores:");
        for(int i = 0; i < listaHash.size(); i++){
            if(listaHash.get(i).hash != Integer.MIN_VALUE){
                boolean hashHeap = heap.inserirValor(listaHash.get(i).hash);
                if(hashHeap == true){
                    array[i] = listaHash.get(i).hash;
                }
            }
        }

        heap.construirMaxHeap(array);

        // Verificacao da existencia das senhas no heap
        System.out.println("\nTeste Busca de Senhas: ");
        heap.buscarSenha("SenhaHashTable");
        heap.buscarSenha("TrabalhoHashTable");
        heap.buscarSenha("SenhaNaoExistente");

        // Valida se e max heap ou nao
        // Caso nao seja, imprime "Nao e max heap"
        for(int j = 0; j < array.length; j++){
            heap.validarHeap(j);
        }

        // Imprime o max heap
        System.out.println("\nImprimindo o Heap: ");
        heap.imprimirHeap();

        // Removendo um valor do heap
        // Ao remover, a funcao chama a funcao de max heapify
        System.out.println("\nRemovendo valor do heap");
        //heap.removerMaximo();
        heap.removerSenha("TrabalhoHashTable");

        // Validando se e max heap apos a exclusao de um valor
        for(int j = 0; j < array.length; j++){
            heap.validarHeap(j);
        }

        // Busca da senha excluida
        System.out.println("\nBusca da senha removida: ");
        heap.buscarSenha("TrabalhoHashTable");

        // Impressao do heap apos a remocao
        System.out.println("\nImprimindo heap apos a remocao:");
        heap.imprimirHeap();
    }
}