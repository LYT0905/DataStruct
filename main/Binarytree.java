
import org.junit.Test;

import java.util.LinkedList;

public class Binarytree {



    //遍历方法一：递归版
    //前序遍历
    public void preOrder(TreeNode root){
        if (root == null){
            return;
        }
        System.out.println(root.val);
        preOrder(root.left);
        preOrder(root.right);
    }

    //中序遍历
    public void inOrder(TreeNode root){
        if (root == null){
            return;
        }
        inOrder(root.left);
        System.out.println(root.val);
        inOrder(root.right);
    }

    //后序遍历
    public void postOrder(TreeNode root){
        if (root == null){
            return;
        }
        postOrder(root.left);
        postOrder(root.right);
        System.out.println(root.val);
    }



    //遍历方法二：非递归版
    LinkedList<TreeNode> stack = new LinkedList<>();

    //前序遍历
    public void preOrder1(TreeNode root){
        TreeNode current = root;
        while (current != null ||  !stack.isEmpty()){
            if (current != null){
                System.out.println(current.val);
                stack.push(current);
                current = current.left;
            }else {
                TreeNode pop = stack.pop();
                current = pop.right;
            }
        }
    }

    //中序遍历
    public void inOrder1(TreeNode root){
        TreeNode current = root;
        while (current != null || !stack.isEmpty()){
            if (current != null){
                stack.push(current);
                current = current.left;
            }else {
                TreeNode  pop = stack.pop();
                System.out.println(pop.val);
                current = pop.right;
            }
        }
    }

    //后序遍历
    public void postOrder1(TreeNode root){
        TreeNode current = root;
        TreeNode pop = null;
        while (current != null || !stack.isEmpty()){
            if (current != null){
                stack.push(current);
                current = current.left;
            }else {
                TreeNode peek = stack.peek();
                if (peek.right == null || peek.right == pop){
                    //右子树已经解决
                    pop = stack.pop();
                    System.out.println(pop);
                }else {
                    current = peek.right;
                }
            }
        }
    }

    //三合一
    public void order(TreeNode root){
        TreeNode current = root;
        TreeNode pop = null;
        while (current != null || !stack.isEmpty()){
            if (current != null){
                stack.push(current);

                //处理左子树
                System.out.println(current);//前序遍历
                current = current.left;
            }else {
                TreeNode peek = stack.peek();
                if (peek.right == null){  //右子树为空
                    System.out.println(peek.val);//中序遍历
                    pop = stack.pop();
                    System.out.println(pop.val);//后序遍历
                } else if (peek.right == pop) {   //右子树已经解决
                    pop = stack.pop();
                    System.out.println(pop.val);//后序遍历
                } else {
                    //处理右子树
                    System.out.println(peek.val);//中序遍历
                    current = peek.right;
                }
            }
        }
    }
}
