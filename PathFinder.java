import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import javax.swing.JComboBox;

public class PathFinder {
    JFrame frame;
    private int cells=20;
    private int delay=30;
    private double density = (cells*cells)*10;
	private int startx = -1;
	private int starty = -1;
	private int finishx = -1;
	private int finishy = -1;
	private int tool = 0;
	private int checks = 0;
	private int length = 0;
	private int curAlg = 0;
	private int WIDTH = 850;
	private final int HEIGHT = 650;
	private final int MSIZE = 600;
	private int CSIZE = MSIZE/cells;
    private double dense=.5;
	//UTIL ARRAYS
	private String[] algorithms = {"Dijkstra"};
	private String[] tools = {"Start","Finish","Wall", "Eraser"};
	//BOOLEANS
	private boolean solving = false;
	//UTIL
	Node[][] map;
	Algorithm Alg = new Algorithm();
	Random r = new Random();
	//SLIDERS
	JSlider size = new JSlider(1,5,2);
	JSlider speed = new JSlider(0,500,delay);
	JSlider obstacles = new JSlider(1,100,50);
	//LABELS
	JLabel algL = new JLabel("Algorithms");
    JLabel algtex=new JLabel("Dijkstra");

	JLabel toolL = new JLabel("Toolbox");
	JLabel sizeL = new JLabel("Size:");
	JLabel cellsL = new JLabel(cells+"x"+cells);
	JLabel delayL = new JLabel("Delay:");
	JLabel msL = new JLabel(delay+"ms");
	JLabel obstacleL = new JLabel("Dens:");
	JLabel densityL = new JLabel(obstacles.getValue()+"%");
	JLabel checkL = new JLabel("Checks: "+checks);
	JLabel lengthL = new JLabel("Path Length: "+length);
	//BUTTONS
	JButton searchB = new JButton("Start Search");
	JButton resetB = new JButton("Reset");
	JButton genMapB = new JButton("Generate Map");
	JButton clearMapB = new JButton("Clear Map");
	JButton creditB = new JButton("Credit");
	//DROP DOWN
	JComboBox algorithmsBx = new JComboBox(algorithms);
	JComboBox toolBx = new JComboBox(tools);
	//PANELS
	JPanel toolP = new JPanel();
	//CANVAS
	Map canvas;
	//BORDER
	Border loweredetched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
public static void main(String[] args){
    new PathFinder();
}
public PathFinder(){
   clearMap();
    initazigation();
}
public void clearMap(){
    finishx=-1;
    finishy=-1;	
    startx=-1;
    starty=-1;
    map=new Node[cells][cells];
    for(int i=0;i<cells;i++){
        for(int j=0;i<cells;j++){
            map[i][j]=new Node(3,i,j);
        }
    }
	reset();
}
public void generateMap(){
    clearMap();
    for(int i=0;i<density;i++){
        Node curr;
        do{
            int x=r.nextInt(cells);
            int y=r.nextInt(cells);
            curr=map[x][y];
        }while(curr.getType()==2);
        curr.setType(2);
    }
}
public void resetMap() {
    for(int i=0;i<cells;i++){
        for(int j=0;j<cells;j++){
            Node curr=map[i][j];
            if(curr.getType()==4|| curr.getType()==5)
            map[i][j]=new Node(3,i,j);
        }
    }
    if(startx>-1 && starty>-1 ){
        map[startx][starty]=new Node(0,startx,starty);
        map[startx][starty].setHops(0);
    }
    if(finishx>-1 && finishy>-1){
        map[finishx][finishy]=new Node(0,finishx,finishy);
        reset();
    }
}
public void reset(){
    solving=false;
    length=0;
    checks=0;
}
public void Update(){
    density=(cells*cells)*density;
    CSIZE=MSIZE/cells;
    canvas.repaint();
    cellsL.setText(cells+"x"+cells);
    msL.setText(delay+"ms");
    lengthL.setText("Path Length"+ length);
    densityL.setText(obstacles.getValue()+"%");
    checkL.setText("check"+checks);
}
public void pause(){
    int i=0;
    while(!solving){
        i++;
        if(i>500)
        i=0;
        try{
            Thread.sleep(1);
        }catch(Exception e){

        }
    }
    startSearch();
}
private void initazigation(){
    frame=new JFrame();
    frame.setVisible(true);
    frame.setResizable(false);
    frame.setSize(WIDTH, HEIGHT);
    frame.setTitle("PATH FINDER USING DIJISKA");
    frame.setLocationRelativeTo(null);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().setLayout(null);

    toolP.setBorder(BorderFactory.createTitledBorder(loweredetched,"Controles"));
    int space=20;
    int buff= 45;

    toolP.setLayout(null);
    toolP.setBounds(10, 10, 210, 600);
    searchB.setBounds( 40,space ,120, 25);
    toolP.add(searchB);
    space+=buff;

    resetB.setBounds(40, space, 120, 25);
    toolP.add(resetB);
    space+=buff;

    genMapB.setBounds(40, space, 120, 25);
    toolP.add(genMapB);
    space += buff;

      clearMapB.setBounds(40, space, 120, 25);
		toolP.add(clearMapB);
		space += 40;

        algL.setBounds(40,space,120,25);
        toolP.add(algL);
        space+=25;
        algtex.setBounds(40, space, 120, 25);
        toolP.add(algtex);
        space+=40;

        algorithmsBx.setBounds(40,space,120,25);
        toolP.add(algorithmsBx);
        space+=40;

        toolL.setBounds(40,space,120,25);
        toolP.add(toolL);
        space+=25;

        toolBx.setBounds(40,space,120,25);
        toolP.add(toolBx);
        space+=buff;

        delayL.setBounds(15, space, 50, 25);
		toolP.add(delayL);
		speed.setMajorTickSpacing(5);
		speed.setBounds(50, space, 100, 25);
		toolP.add(speed);
		msL.setBounds(160, space, 40, 25);
		toolP.add(msL);
		space += buff;

        obstacleL.setBounds(15, space, 100, 25);
		toolP.add(obstacleL);
		obstacles.setMajorTickSpacing(5);
		obstacles.setBounds(50, space, 100, 25);
		toolP.add(obstacles);
		densityL.setBounds(160, space, 100, 25);
		toolP.add(densityL);
		space += buff;

		    checkL.setBounds(15, space, 100, 25);
		toolP.add(checkL);
		space += buff;

		lengthL.setBounds(15, space, 100, 25);
		toolP.add(lengthL);
		space += buff;

		creditB.setBounds(40, space, 120, 25);
		toolP.add(creditB);

		frame.getContentPane().add(toolP);
        canvas=new Map();
        canvas.setBounds(230,10,MSIZE+1,MSIZE+1);
        frame.getContentPane().add(canvas);

        searchB.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                reset();
                if((startx>-1 && starty>-1)&&(finishx> -1 && finishy>-1))
                solving=true;
                
            }
        });

        resetB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				resetMap();
				Update();
			}
		});
		genMapB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				generateMap();
				Update();
			}
		});
		clearMapB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clearMap();
				Update();
			}
		});
		algorithmsBx.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				curAlg = algorithmsBx.getSelectedIndex();
				Update();
			}
		});
		toolBx.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				tool = toolBx.getSelectedIndex();
			}
		});
		size.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				cells = size.getValue() * 10;
				clearMap();
				reset();
				Update();
			}
		});
		speed.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				delay = speed.getValue();
				Update();
			}
		});
		obstacles.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				dense = (double) obstacles.getValue() / 100;
				Update();
			}
		});

		startSearch(); // START STATE
	}

	public void startSearch() { // START STATE
		if (solving) {
			switch (curAlg) {
				case 0:
					Alg.Dijkstra();
					break;
			}
		}
		pause(); // PAUSE STATE
	}
	class Map extends JPanel implements MouseListener, MouseMotionListener { // MAP CLASS

		public Map() {
			addMouseListener(this);
			addMouseMotionListener(this);
		}

		public void paintComponent(Graphics g) { // REPAINT
			super.paintComponent(g);
			for (int x = 0; x < cells; x++) { // PAINT EACH NODE IN THE GRID
				for (int y = 0; y < cells; y++) {
					switch (map[x][y].getType()) {
						case 0:
							g.setColor(Color.GREEN);
							break;
						case 1:
							g.setColor(Color.RED);
							break;
						case 2:
							g.setColor(Color.BLACK);
							break;
						case 3:
							g.setColor(Color.WHITE);
							break;
						case 4:
							g.setColor(Color.CYAN);
							break;
						case 5:
							g.setColor(Color.YELLOW);
							break;
					}
					g.fillRect(x * CSIZE, y * CSIZE, CSIZE, CSIZE);
					g.setColor(Color.BLACK);
					g.drawRect(x * CSIZE, y * CSIZE, CSIZE, CSIZE);
					// DEBUG STUFF
					/*
					 * if(curAlg == 1)
					 * g.drawString(map[x][y].getHops()+"/"+map[x][y].getEuclidDist(),
					 * (x*CSIZE)+(CSIZE/2)-10, (y*CSIZE)+(CSIZE/2));
					 * else
					 * g.drawString(""+map[x][y].getHops(), (x*CSIZE)+(CSIZE/2),
					 * (y*CSIZE)+(CSIZE/2));
					 */
				}
			}
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			try {
				int x = e.getX() / CSIZE;
				int y = e.getY() / CSIZE;
				Node current = map[x][y];
				if ((tool == 2 || tool == 3) && (current.getType() != 0 && current.getType() != 1))
					current.setType(tool);
				Update();
			} catch (Exception z) {
			}
		}

		@Override
		public void mouseMoved(MouseEvent e) {
		}

		@Override
		public void mouseClicked(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}

		@Override
		public void mousePressed(MouseEvent e) {
			resetMap(); // RESET THE MAP WHENEVER CLICKED
			try {
				int x = e.getX() / CSIZE; // GET THE X AND Y OF THE MOUSE CLICK IN RELATION TO THE SIZE OF THE GRID
				int y = e.getY() / CSIZE;
				Node current = map[x][y];
				switch (tool) {
					case 0: { // START NODE
						if (current.getType() != 2) { // IF NOT WALL
							if (startx > -1 && starty > -1) { // IF START EXISTS SET IT TO EMPTY
								map[startx][starty].setType(3);
								map[startx][starty].setHops(-1);
							}
							current.setHops(0);
							startx = x; // SET THE START X AND Y
							starty = y;
							current.setType(0); // SET THE NODE CLICKED TO BE START
						}
						break;
					}
					case 1: {// FINISH NODE
						if (current.getType() != 2) { // IF NOT WALL
							if (finishx > -1 && finishy > -1) // IF FINISH EXISTS SET IT TO EMPTY
								map[finishx][finishy].setType(3);
							finishx = x; // SET THE FINISH X AND Y
							finishy = y;
							current.setType(1); // SET THE NODE CLICKED TO BE FINISH
						}
						break;
					}
					default:
						if (current.getType() != 0 && current.getType() != 1)
							current.setType(tool);
						break;
				}
				Update();
			} catch (Exception z) {
			} // EXCEPTION HANDLER
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}
	}


	public void delay() { // DELAY METHOD
		try {
			Thread.sleep(delay);
		} catch (Exception e) {
		}
	}

	class Algorithm{
		public void Dijkstra(){
			ArrayList<Node> priority=new ArrayList<>();
			priority.add(map[startx][starty]);
			while(solving){
				if(priority.size()<=0){
					solving=false;
					break;
				}
				int hops=priority.get(0).getHops()+1;
				ArrayList<Node>explore=exploreNeighbors(priority.get(0),hops);
				if(explore.size()>0){
					priority.remove(0);
					priority.addAll(explore);
					Update();
					delay();
				}else{
					priority.remove(0);
				}
			}
		}

		public ArrayList<Node>exploreNeighbors(Node curr,int hops){
			ArrayList<Node>explore=new ArrayList<>();
			for(int i=-1;i<=1;i++){
				for(int j=-1;j<=1;j++){
					int xbound=curr.getX()+i;
					int ybound=curr.getY()+j;
					if((xbound>-1 && xbound<cells)&& (ybound>-1 && ybound<cells)){
						Node neighbore=map[xbound][ybound];
						if((neighbore.getHops()==-1 || neighbore.getHops()>hops  ) && neighbore.getType()!=2){
							explored(neighbore,curr.getX(),curr.getY(),hops);
							explore.add(neighbore);
						}
					}
				}
			}
			return explore;
		}
		public void explored( Node curr,int lastX,int lastY, int hops){
			if(curr.getType()!=0 && curr.getType()!=1){
				curr.setType(4);
				curr.setLastNode(lastX, lastY);
				curr.setHops(hops);
				checks++;
				if(curr.getType()==1){
					backTrack(curr.getLastX(), curr.getLastY(),hops);
				}
			}
		}
		public void backTrack(int lx,int ly,int hops){
			length=hops;
			while(hops>1){
				Node curr=map[lx][ly];
				curr.setType(5);
				lx=curr.getLastX();
				ly=curr.getLastY();
				hops--;

			}
			solving=false;
		}
	}
		


class Node{
    private int cellType=0;
    private int hops;
    private int x,y,lastX,lastY;
    private double dToEnd=0;

    public Node(int type, int x,int y){
        cellType=type;
        this.x=x;
        this.y=y;
        hops=-1;
        
    }
    public double getEuclidDist(){
        int xdif=Math.abs(x-finishx);
        int ydif=Math.abs(y-finishy);
        dToEnd=Math.sqrt((xdif*xdif)+(ydif*ydif));
        return dToEnd;
    }
    public int getX(){return x;}
    public int getY(){return y;}
    public int getLastX(){return lastX;}
    public int getLastY(){return lastY;}
    public int getType() {return cellType;}
    public int getHops() {return hops;}
    public void setType(int type) {cellType = type;}		//SET METHODS
    public void setLastNode(int x, int y) {lastX = x; lastY = y;}
    public void setHops(int hops) {this.hops = hops;}
}	

}