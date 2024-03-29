import javax.swing.JPanel;
import java.awt.*;
import java.util.ArrayList;

public class DemoPanel extends JPanel {
    final int maxCol = 25;
    final int maxRow = 14;
    final int nodeSize = 45;
    final int screenWidth = (nodeSize * maxCol);
    final int screenHeight = (nodeSize * maxRow);
    Node[][] node = new Node[maxCol][maxRow];
    Node startNode, goalNode, currentNode;
    ArrayList<Node> openList = new ArrayList<>();
    ArrayList<Node> checkedList = new ArrayList<>();
    boolean goalReached = false;
    int step = 0;


    public DemoPanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setLayout(new GridLayout(maxRow,maxCol));
        this.addKeyListener(new KeyHandler(this));
        this.setFocusable(true);

        int col =0;
        int row=0;
        while((col< maxCol)&&(row < maxRow)){
            node[col][row] = new Node(col,row);
            this.add(node[col][row]);

            col++;
            if(col == maxCol){
                col = 0;
                row++;
            }
        }
        setStartNode(3,5);
        setGoalNode(21,10);

        setSolidNode(1,2);
        setSolidNode(2,2);
        setSolidNode(3,2);
        setSolidNode(3,3);
        setSolidNode(3,4);
        setSolidNode(4,5);
        setSolidNode(3,6);
        setSolidNode(3,7);
        setSolidNode(4,7);
        setSolidNode(5,7);
        setSolidNode(6,7);
        setSolidNode(6,9);
        setSolidNode(5,8);
        setSolidNode(5,9);
        setSolidNode(6,2);
        setSolidNode(13,9);
        setSolidNode(12,9);
        setSolidNode(11,9);
        setSolidNode(10,9);
        setSolidNode(9,9);
        setSolidNode(3,10);
        setSolidNode(3,11);
        setSolidNode(3,12);
        setSolidNode(5,10);
        setSolidNode(5,12);
        setSolidNode(5,13);

        setCostOnNodes();
    }

    private void setStartNode(int col, int row){
        node[col][row].setAsStart();
        startNode = node[col][row];
        currentNode = startNode;
    }
    private void setGoalNode(int col, int row){
        node[col][row].setAsGoal();
        goalNode = node[col][row];
    }
    private void setSolidNode(int col, int row){
        node[col][row].setAsSolid();
    }
    private void setCostOnNodes(){
        int col =0;
        int row = 0;
        while((col < maxCol) && (row < maxRow)){
            getCost(node[col][row]);
            col++;
            if(col == maxCol){
                col=0;
                row++;
            }
        }
    }
    private void getCost(Node node){
        int xDistance = Math.abs(node.col - startNode.col);
        int yDistance = Math.abs(node.row - startNode.row);
        node.gCost = xDistance+yDistance;

        xDistance = Math.abs(node.col - goalNode.col);
        yDistance = Math.abs(node.row - goalNode.row);
        node.hCost = xDistance+yDistance;

        node.fCost = node.gCost + node.hCost;

        if(node != startNode && node!= goalNode){
            node.setText("<html>F:" + node.fCost + "<br>G:" + node.gCost + "</html>");
        }
    }
    public void search(){
        if(goalReached == false && step <300){
            int col = currentNode.col;
            int row = currentNode.row;

            currentNode.setAsChecked();
            checkedList.add(currentNode);
            openList.remove(currentNode);

            if(row-1 >= 0) {
                openNode(node[col][row - 1]);
            }
            if(col-1 >= 0) {
                openNode(node[col - 1][row]);
            }
            if(row + 1 < maxRow) {
                openNode(node[col][row + 1]);
            }
            if(col + 1 < maxCol) {
                openNode(node[col + 1][row]);
            }

            int bestNodeIndex =0;
            int bestNodefCost = 999;
            for (int i =0; i < openList.size() ; i++){
                if(openList.get(i).fCost < bestNodefCost){
                    bestNodeIndex =i;
                    bestNodefCost = openList.get(i).fCost;
                } else if (openList.get(i).fCost == bestNodefCost) {
                    if(openList.get(i).gCost < openList.get(bestNodeIndex).gCost){
                        bestNodeIndex = i;
                    }
                }
            }
            currentNode = openList.get(bestNodeIndex);
            if(currentNode == goalNode){
                goalReached = true;
            }
        }
        step++;
    }
    public void autoSearch(){
        while(goalReached == false && step < 300){
            int col = currentNode.col;
            int row = currentNode.row;

            currentNode.setAsChecked();
            checkedList.add(currentNode);
            openList.remove(currentNode);

            if(row-1 >= 0) {
                openNode(node[col][row - 1]);
            }
            if(col-1 >= 0) {
                openNode(node[col - 1][row]);
            }
            if(row + 1 < maxRow) {
                openNode(node[col][row + 1]);
            }
            if(col + 1 < maxCol) {
                openNode(node[col + 1][row]);
            }

            int bestNodeIndex =0;
            int bestNodefCost = 999;
            for (int i =0; i < openList.size() ; i++){
                if(openList.get(i).fCost < bestNodefCost){
                    bestNodeIndex =i;
                    bestNodefCost = openList.get(i).fCost;
                } else if (openList.get(i).fCost == bestNodefCost) {
                    if(openList.get(i).gCost < openList.get(bestNodeIndex).gCost){
                        bestNodeIndex = i;
                    }
                }
            }
            currentNode = openList.get(bestNodeIndex);
            if(currentNode == goalNode){
                goalReached = true;
                trackThePath();
            }
            step++;
        }
    }
    private void openNode(Node node){
        if((node.open == false) && (node.checked == false) && (node.solid == false)){
            node.setAsOpen();
            node.parent = currentNode;
            openList.add(node);
        }
    }
    private void trackThePath(){
        Node current = goalNode;
        while(current != startNode){
            current = current.parent;
            if(current != startNode){
                current.setAsPath();
            }
        }
    }
}
