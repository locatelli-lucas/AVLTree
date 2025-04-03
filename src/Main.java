import tree.AVLTree;
import tree.TreeNode;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AVLTree tree = new AVLTree();
        String response;

        List<TreeNode> list = List.of(
                new TreeNode(15),
                new TreeNode(20),
                new TreeNode(30),
                new TreeNode(40),
                new TreeNode(45),
                new TreeNode(35)
        );
        list.forEach(tree::insert);

        tree.runTree();

//        do {
//            System.out.println("Selecione a operação desejada: \n" +
//                    "1 - Inserir um nó\n" +
//                    "2 - Remover um nó\n" +
//                    "3 - Buscar um nó\n" +
//                    "4 - Imprimir a árvore\n" +
//                    "5 - Sair"
//            );
//            response = sc.nextLine();
//
//            switch (response) {
//                case "1":
//                    System.out.println("Digite o valor do nó a ser inserido: ");
//                    int value = sc.nextInt();
//                    sc.nextLine();
//                    tree.insert(new TreeNode(value));
//                    break;
//                case "2":
//                    System.out.println("Digite o valor do nó a ser removido: ");
//                    int valueToRemove = sc.nextInt();
//                    sc.nextLine();
//                    TreeNode nodeToRemove = tree.search(valueToRemove);
//                    if(nodeToRemove.hasDoubleChild()) {
//                        System.out.println("Escolha o método de remoção: \n" +
//                                "1 - Remoção por fusão\n" +
//                                "2 - Remoção por cópia"
//                        );
//                        String removalMethod = sc.nextLine();
//                        if(Objects.equals(removalMethod, "1")) {
//                            tree.delete(nodeToRemove.getNodeData(), 1);
//                        } else if(Objects.equals(removalMethod, "2")) {
//                            tree.delete(nodeToRemove.getNodeData(), 2);
//                        }
//                    } else
//                        tree.delete(nodeToRemove.getNodeData(), 0);
//                    break;
//                case "3":
//                    System.out.println("Digite o valor do nó a ser buscado: ");
//                    int valueToSearch = sc.nextInt();
//                    sc.nextLine();
//                    TreeNode node = tree.search(valueToSearch);
//                    if(node == null)
//                        System.out.println("Nó não encontrado");
//                    else
//                        System.out.println(node);
//                    break;
//                case "4":
//                    System.out.println(tree.printTree());
//                    break;
//                case "5":
//                    System.out.println("Saindo...");
//                    break;
//                default:
//                    System.out.println("Opção inválida");
//            }
//        } while(!Objects.equals(response, "5"));
//
//        sc.close();
    }
}