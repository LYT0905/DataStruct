import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BSTree {
    @Test
    public void get(){
        BSTNode n1 = new BSTNode(1,"张无忌");
        BSTNode n3 = new BSTNode(3,"宋青书");
        BSTNode n2 = new BSTNode(2,"周芷若",n1,n3);
        BSTNode n5 = new BSTNode(5,"说不得");
        BSTNode n7 = new BSTNode(7,"股利");
        BSTNode n6 = new BSTNode(6,"赵敏",n5,n7);
        BSTNode root = new BSTNode(4,"小昭",n2,n6);
        
        
        BSTree bsTree = new BSTree();
        bsTree.root = root;

        bsTree.put(2,"哇码字");

        System.out.println(bsTree.get(2));

    }
    static class BSTNode {
        int key; // 若希望任意类型作为 key, 则后续可以将其设计为 Comparable 接口
        Object value;
        BSTNode left;
        BSTNode right;

        public BSTNode(int key) {
            this.key = key;
            this.value = key;
        }

        public BSTNode(int key, Object value) {
            this.key = key;
            this.value = value;
        }

        public BSTNode(int key, Object value, BSTNode left, BSTNode right) {
            this.key = key;
            this.value = value;
            this.left = left;
            this.right = right;
        }
    }

    BSTNode root;

    //递归实现
    public Object get(int key){
//        return doget(root,key);

        //非递归实现
        BSTNode node = root;
        while (node != null){
            if (node.key < key){
                node = node.right;
            }else if (key < node.key){
                node = node.left;
            }else {
                return node.value;
            }
        }
        return null;
    }

    public Object doget(BSTNode node,int key){
        if (node == null){
            return null;
        }
        if (node.key < key){
            return doget(node.right,key);
        }else if (key < node.key){
            return doget(node.left,key);
        }else {
            return node.value;
        }
    }

    //查找最大关键字
    //递归
    public Object max(){
        return doMax(root);
    }

    private Object doMax(BSTNode node){
        if (node == null){
            return null;
        }
        if (node.right == null){
            return node.value;
        }
        return doMax(node.right);
    }

    //非递归
    public Object max1(){
        return max2(root);
    }

    public Object max2(BSTNode node){
        if (node == null){
            return null;
        }
        BSTNode p = node;
        while (p.right != null){
            p = p.right;
        }
        return p.value;
    }
    //查找最小关键字
    //递归
    public Object min(){
        return doMin(root);
    }

    private Object doMin(BSTNode node){
        if (node == null){
            return null;
        }
        if (node.left == null){
            return node.value;
        }
        return doMax(node.left);
    }
    //非递归
    public Object min1(){
        return min2(root);
    }

    public Object min2(BSTNode node){
        if (node == null){
            return null;
        }
        BSTNode p = node;
        while (p.left != null){
            p = p.left;
        }
        return p.value;
    }

    //插入
    //非递归
    public void put(int key,Object value){
        BSTNode node = root;
        BSTNode parent = null;
        while (node != null){
            parent = node;
            if (node.key < key){
                node = node.right;
            }else if (key < node.key){
                node = node.left;
            }else {
                //如果有
                node.value = value;
                return;
            }
        }
        //如果没有
        if (parent == null){
            root = new BSTNode(key,value);
        } else if (parent.key < key){
            parent.right = new BSTNode(key,value);
        }else{
            parent.left = new BSTNode(key,value);
        }
    }

    //找前驱节点
    public Object predecessor(int key){
        BSTNode p = root;
        BSTNode ancestor = null;
        while (p != null){
            if (p.key < key){
                p = p.left;
            }else if (key < p.key){
                ancestor = p;
                p = p.right;
            }else {
                break;
            }
        }
        //找到
        if (p == null){
            return null;
        }
        // 情况1 - 有左孩子
        if (p.left != null){
            return max2(p.left);
        }
        // 情况2 - 有祖先自左而来
        return ancestor != null ? (BSTNode) ancestor.value : null;
    }

    //后继
    public Object successor(int key){
        BSTNode p = root;
        BSTNode ancestor = null;
        while (p != null){
            if (p.key < key){
                p = p.right;
            }else if (key < p.key){
                ancestor = p;
                p = p.left;
            }else {
                break;
            }
        }
        if (p == null){
            return null;
        }
        if (p.right != null){
            return min2(p.right);
        }
        return ancestor != null ? ancestor.value : null;
    }

    //删除
    public Object delete(int key){
        BSTNode p = root;
        BSTNode ancestor = null;
        while (p != null){
            if (p.key < key){
                ancestor = p;
                p = p.right;
            }else if (key < p.key){
                ancestor = p;
                p = p.left;
            }else {
                break;
            }
        }
        if (p == null){
            return null;
        }

        if (p.left==null){
            shift(ancestor,p,p.right);
        }else if (p.right==null){
            shift(ancestor,p,p.left);
        }else {
            // 情况4
            // 4.1 被删除节点找后继
            BSTNode s = p.right;
            BSTNode sParent = p;
            while (s.left != null){
                sParent = s;
                s = s.left;
            }
            if (sParent != p){
                shift(sParent,s,s.right); // 不可能有左孩子
                s.right = p.right;
            }
            shift(sParent,p,s);
            s.left = p.left;
        }
        return p.value;
    }

    private void shift(BSTNode parent, BSTNode deleted, BSTNode child){
        if (parent == null){
            return;
        } else if (deleted == parent.left) {
            parent.left = child;
        }else {
            parent.right = child;
        }
    }


    //递归版删除
    public Object delete1(int key){
        ArrayList<Object> result = new ArrayList<>();
        root = (BSTNode) doDelete(root, key, result);
        return result.isEmpty() ? null : result.get(0);
    }

    private BSTNode doDelete(BSTNode node, int key, ArrayList<Object> result){
        if (node == null) {
            return null;
        }
        if (key < node.key) {
            node.left =  doDelete(node.left, key, result);
            return node;
        }
        if (node.key < key) {
            node.right =  doDelete(node.right, key, result);
            return node;
        }
        if (node.left == null){
            return node.right;
        }
        if (node.right == null){
            return node.left;
        }
        BSTNode s = node.right;
        while (s != null){
            s = s.left;
        }
        s.right =  doDelete(node.right,s.key,new ArrayList<>());
        s.left = node.left;
        return s;
    }


    //小于
    public List<Object> less(int key){
        List<Object> result = new ArrayList<>();
        BSTNode p = root;
        LinkedList<BSTNode> stack = new LinkedList<>();
        while (p!=null || !stack.isEmpty()){
            if (p != null){
                stack.push(p);
                p = p.left;
            }else {
                BSTNode pop = stack.pop();
                if (pop.key < key){
                    result.add(pop.value);
                }else {
                    break;
                }
                p = pop.right;
            }
        }
        return result;
    }
    //大于
    public List<Object> greater(int key) {
//        List<Object> result = new ArrayList<>();
//        BSTNode p = root;
//        LinkedList<BSTNode> stack = new LinkedList<>();
//        while (p!=null || !stack.isEmpty()){
//            if (p != null){
//                stack.push(p);
//                p = p.left;
//            }else {
//                BSTNode pop = stack.pop();
//                if (pop.key > key){
//                    result.add(pop.value);
//                }
//                p = pop.right;
//            }
//        }
//        return result;

        //反向中序遍历:此方法在这效率高
        List<Object> result = new ArrayList<>();
        BSTNode p = root;
        LinkedList<BSTNode> stack = new LinkedList<>();
        while (p!=null || !stack.isEmpty()){
            if (p != null){
                stack.push(p);
                p = p.right;
            }else {
                BSTNode pop = stack.pop();
                if (pop.key > key){
                    result.add(pop.value);
                }else if (pop.key < key){
                    break;
                }
                p = pop.left;
            }
        }
        return result;
    }
    // 中间
    public List<Object> between(int key1, int key2){
        List<Object> result = new ArrayList<>();
        BSTNode p = root;
        LinkedList<BSTNode> stack = new LinkedList<>();
        while (p!=null || !stack.isEmpty()){
            if (p != null){
                stack.push(p);
                p = p.left;
            }else {
                BSTNode pop = stack.pop();
                if (pop.key >= key1 && pop.key <= key2){
                    result.add(pop.value);
                } else if (pop.key>key2) {
                    break;
                }
                p = pop.right;
            }
        }
        return result;
    }
}




