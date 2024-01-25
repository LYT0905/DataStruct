import org.junit.Test;

public class AvlTree {
    @Test
    public void Avlt(){
        AVLTree avlTree = new AVLTree();

    }
}

class AVLTree{
    static class AvlNode {
        int key;
        Object val;
        AvlNode left;
        AvlNode right;
        int height = 1;
        public AvlNode(){}
        public AvlNode(int key,Object val){
            this.key = key;
            this.val = val;
        }
        public void setKey(int key) {
            this.key = key;
        }
        public void setVal(Object val) {
            this.val = val;
        }
    }
    AvlNode root;
    //求高度
    private int height(AvlNode node){
        return node == null ? 0 : node.height;
    }
    //更新高度
    private void upGrade(AvlNode node){
        node.height =  Integer.max(height(node.left),height(node.right)) + 1;
    }
    //平衡因子balance factor
     private int bc(AvlNode node){
        return height(node.left) - height(node.right);
    }
    //左旋
    public AvlNode leftRotate(AvlNode red){
        AvlNode yellow = red.right;
        AvlNode green = yellow.left;
        yellow.left = red;
        red.right = green;
        upGrade(red);
        upGrade(yellow);
        return yellow;
    }
    //右旋
    public AvlNode rightRotate(AvlNode red){
        AvlNode yellow = red.left;
        AvlNode green = yellow.right;
        yellow.right = red;
        red.left = green;
        upGrade(red);
        upGrade(yellow);
        return yellow;
    }
    //左右旋
    public AvlNode leftRightRotate(AvlNode node){
        node.left = leftRotate(node.left);
        return rightLeftRotate(node);
    }
    //右左旋
    public AvlNode rightLeftRotate(AvlNode node){
        node.right = rightRotate(node.right);
        return leftRotate(node);
    }
    //判断是否平衡
    private AvlNode balance(AvlNode node){
        if (node == null){
            return null;
        }
        int height = bc(node);
        if (height > 1 && bc(node.left) >= 0){ //LL
            return leftRotate(node);
        } else if (height > 1 && bc(node.left) < 0 ) { //LR
            return leftRightRotate(node);
        } else if (height < -1 && bc(node.right) > 0) { //RR
            return rightRotate(node);
        } else if (height < -1 && bc(node.right) <= 0) {  //RL
            return rightLeftRotate(node);
        }
        return node;
    }
    //新增
    public void put(int key,Object val){
        root = doPut(root,key,val);
    }
    private AvlNode doPut(AvlNode node,int key,Object val){
        //如果node为空，创建新节点
        if (node == null){
            return new AvlNode(key,val);
        }
        //如果值相等，更新
        if (node.key == key){
            node.val = val;
            return node;
        }
        //继续查找
        if (key < node.key){
            node.left = doPut(node.left,key,val);
        }else {
            node.right = doPut(node.right,key,val);
        }
        upGrade(node);
        return balance(node);
    }
    //删除
    public void remove(int key){
        root = doRemove(root,key);
    }
    private AvlNode doRemove(AvlNode node,int key){
        if (node == null){
            return null;
        }
        if (key < node.key){
            node.left = doRemove(node.left,key);
        }else if (node.key < key){
            node.right = doRemove(node.right,key);
        }else {
            if (node.left == null && node.right == null){
                return null;
            } else if (node.left == null) {
                node = node.right;
            } else if (node.right == null) {
                node = node.left;
            }else {
                AvlNode s = node.right;
                while (s.left != null){
                    s = s.left;
                }
                //找到后继
                s.right = doRemove(node.right,s.key);
                s.left = node.left;
                node = s;
            }
        }
        upGrade(node);
        return balance(node);
    }

}


