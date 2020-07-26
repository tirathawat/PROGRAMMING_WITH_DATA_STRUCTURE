import java.util.ArrayList;
import java.util.LinkedHashMap;

class HuffmanNode implements Comparable<HuffmanNode>{
    char alpha;
    int freq;
    String str;
    HuffmanNode left, right;

    HuffmanNode () {}

    HuffmanNode(char alpha, int freq) {
        this.alpha = alpha;
        this.freq = freq;
        this.str = String.valueOf(alpha);
        left = right = null;
    }

    @Override
    public int compareTo(HuffmanNode o) {
        return this.freq - o.freq;
    }
}

class MyPriority {
    private HuffmanNode[] data;
    private int count, max;

    MyPriority (int max) {
        data = new HuffmanNode[max];
        this.max = max;
        count = 0;
    }

    int size () {
        return count;
    }

    private boolean isFull() {
        return count == max;
    }

    void insert (HuffmanNode node) {
        int i;
        if(!isFull()){
            if(count == 0){
                data[count++] = node;
            }else{

                for(i = count - 1; i >= 0; i-- ){
                    if(node.compareTo(data[i]) < 0) data[i+1] = data[i];
                    else break;
                }

                data[i+1] = node;
                count++;
            }
        }
    }

    HuffmanNode remove () {
        HuffmanNode removeNode = data[0];
        if (count - 1 >= 0) System.arraycopy(data, 1, data, 0, count - 1);
        count--;
        return removeNode;
    }

    void show () {
        System.out.print("ข้อมูลในPriorityQueue = ");
        for (int i = 0; i < count; i++) System.out.print(data[i].str + "(" + data[i].freq + ") ");
        System.out.println();
    }
}


public class HuffmanExam {
    private ArrayList<HuffmanNode> table;
    private MyPriority q;
    private LinkedHashMap<Character, String> charToCode;
    private LinkedHashMap<String, Character> codeToChar;

    private HuffmanExam (ArrayList<HuffmanNode> table) {
        this.table = table;
        q = new MyPriority(50);
        charToCode = new LinkedHashMap<>();
        codeToChar = new LinkedHashMap<>();
    }

    private HuffmanNode huffmanTree() {
        while (q.size() > 1) {
            q.show();
            HuffmanNode z = new HuffmanNode();
            z.left = q.remove();
            z.right = q.remove();
            assert z.left != null;
            assert z.right != null;
            System.out.printf("จับคู่ระหว่าง %s(%d), %s(%d)\n\n", z.left.str, z.left.freq, z.right.str, z.right.freq);
            z.freq = z.left.freq + z.right.freq;
            z.str = z.left.str + z.right.str;
            //System.out.println("ได้ผลลัพธ์" + z.str);
            q.insert(z);
        }
        return q.remove();
    }

    private void createHuffmanTable(HuffmanNode root, LinkedHashMap<Character, String> charToCode, LinkedHashMap<String, Character> codeToChar) {
        FillPostOrderPath(root, "", charToCode, codeToChar);
    }

    private void FillPostOrderPath(HuffmanNode n, String s, LinkedHashMap<Character, String> charToCode, LinkedHashMap<String, Character> codeToChar) {
        if (n == null) return;
        FillPostOrderPath(n.left, s + "0", charToCode, codeToChar);
        FillPostOrderPath(n.right, s + "1", charToCode, codeToChar);
        if (n.alpha != '\0') {
            charToCode.put(n.alpha, s);
            codeToChar.put(s, n.alpha);
        }
    }

    private void convertTableToPriorityQ () {
        for (HuffmanNode node : table) q.insert(node);
    }

    private void encrypt () {
        convertTableToPriorityQ();
        HuffmanNode root = huffmanTree();
        createHuffmanTable(root, charToCode, codeToChar);
    }

    private String createHeader () {
        int count = 0;
        StringBuilder header = new StringBuilder("[" + charToCode.keySet().size() + "]");
        for (HuffmanNode node : table) {
            String codeString = charToCode.get(node.alpha);
            System.out.println(node.alpha + " code string = " + codeString);
            header.append("[").append(node.alpha).append("]").append("[").append(codeString.length())
                    .append("]").append("[").append(Integer.parseInt(codeString, 2)).append("]");
            count += codeString.length() * node.freq;
        }
        header.append("[").append(count).append("]");
        return header.toString();
    }

    private void showBinarySeqence (String text) {
        System.out.println(text);
        for (int i = 0; i < text.length(); i++) System.out.print(charToCode.get(text.charAt(i)) + " ");
        System.out.println();
    }

    public static void main (String[] args) {
        String text = "CPE 30 YEARS ANNIVERSARY";

        ///Exam
        ArrayList<HuffmanNode> table = new ArrayList<>();
        table.add(new HuffmanNode(' ', 3));
        table.add(new HuffmanNode('0', 1));
        table.add(new HuffmanNode('3', 1));
        table.add(new HuffmanNode('A', 3));
        table.add(new HuffmanNode('C', 1));
        table.add(new HuffmanNode('E', 3));
        table.add(new HuffmanNode('I', 1));
        table.add(new HuffmanNode('N', 2));
        table.add(new HuffmanNode('P', 1));
        table.add(new HuffmanNode('R', 3));
        table.add(new HuffmanNode('S', 2));
        table.add(new HuffmanNode('V', 1));
        table.add(new HuffmanNode('Y', 2));


        HuffmanExam huffman = new HuffmanExam(table);
        huffman.encrypt();
        huffman.createHeader();
        huffman.showBinarySeqence(text);
    }
}
