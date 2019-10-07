import java.util.*;
public class aiTicTacToe {
	public static int lookahead = 2;
	public int player; //1 for player 1 and 2 for player 2
	private static List<positionTicTacToe> board = new ArrayList<>();

	static int javaMax = 1000000;
	static int javaMin = -1000000;
	
	private int getStateOfPositionFromBoard(positionTicTacToe position, List<positionTicTacToe> board){
		//a helper function to get state of a certain position in the Tic-Tac-Toe board by given position TicTacToe
		int index = position.x*16+position.y*4+position.z;
		return board.get(index).state;
	}

	public positionTicTacToe myAIAlgorithm(List<positionTicTacToe> board, int player){
		int value = javaMin;
		int alpha = javaMin;
		int beta = javaMin;
		//TODO: this is where you are going to implement your AI algorithm to win the game. The default is an AI randomly choose any available move.
		positionTicTacToe myNextMove = new positionTicTacToe(0,0,0);


		for(int i = 0; i<board.size(); i++){
			if(board.get(i).state == 0) {
				board.get(i).state = player;
				int moveVal = minimax(player, 3, false, board, javaMin, javaMin);

				board.get(i).state = 0;
				alpha = Integer.min(alpha,moveVal);
				if (moveVal > value) { 
					myNextMove.x = board.get(i).x; 
					myNextMove.y = board.get(i).y; 
					myNextMove.z = board.get(i).z; 
                    value = moveVal; 
                }
				if(beta <= alpha) break;
			}
		}
		System.out.println("Next Move by player " + myNextMove.x + myNextMove.y + myNextMove.z);
		return myNextMove;

			
		
	}

	// class Node {
	// 	int key;
	// 	List<Node> children;
	// 	positionTicTacToe position;
	// 	int maxValue;
	// 	boolean isMaxPlayer;


	// 	public Node(int item){
	// 		key = item;
	// 		for(int i=0; i<3; i++){
	// 			children[i] = null;
	// 		}
	// 	}

		

	// }

	

    private int evaluateHeuristic (List<positionTicTacToe> board) {
		
		int score = 0;
		List<List<positionTicTacToe>> winningLines = initializeWinningLines();

		for(int i = 0; i < winningLines.size(); i++){
			
			ArrayList<Integer> states = new ArrayList<>();

			positionTicTacToe p0 = winningLines.get(i).get(0);
			positionTicTacToe p1 = winningLines.get(i).get(1);
			positionTicTacToe p2 = winningLines.get(i).get(2);
			positionTicTacToe p3 = winningLines.get(i).get(3);

			int state0 = getStateOfPositionFromBoard(p0,board);
			int state1 = getStateOfPositionFromBoard(p1,board);
			int state2 = getStateOfPositionFromBoard(p2,board);
			int state3 = getStateOfPositionFromBoard(p3,board);

			states.add(state0);
			states.add(state1);
			states.add(state2);
			states.add(state3);

			int maxscore = 0;
			int minscore = 0;

			for (int j : states){
				if (j == player) maxscore += 1;
				if(j == (3-player)) minscore += 1;
			}

			if (!(maxscore > 0 && (minscore > 0))){
				if(maxscore == 0){
					if(minscore == 1) score -= 10;
					else if(minscore == 2) score -= 100;
					else if(minscore == 3) score -= 200;
					else if(minscore == 4) score -= 500;
				}
				else if(minscore == 0){
					if(maxscore == 1) score += 10;
					else if(maxscore == 2) score += 100;
					else if(maxscore == 3) score += 200;
					else if(maxscore == 4) score += 500;
				}
			}
		}
		return score;
	}

	private boolean GameEnded(List<positionTicTacToe> board){
		List<List<positionTicTacToe>> winningLines = runTicTacToe.initializeWinningLines();
		for(int i=0;i<winningLines.size();i++){
			
			positionTicTacToe p0 = winningLines.get(i).get(0);
			positionTicTacToe p1 = winningLines.get(i).get(1);
			positionTicTacToe p2 = winningLines.get(i).get(2);
			positionTicTacToe p3 = winningLines.get(i).get(3);
			
			int state0 = getStateOfPositionFromBoard(p0,board);
			int state1 = getStateOfPositionFromBoard(p1,board);
			int state2 = getStateOfPositionFromBoard(p2,board);
			int state3 = getStateOfPositionFromBoard(p3,board);
			
			//if they have the same state (marked by same player) and they are not all marked.
			if(state0 == state1 && state1 == state2 && state2 == state3 && state0 != 0){
				return true;
			}
		}
		for(int i=0;i<board.size();i++){
			if(board.get(i).state==0){
				//game is not ended, continue
				return false;
			}
		}
		return true; //call it a draw
	}


	public int minimax(int player, int depth, boolean maximizingPlayer, List<positionTicTacToe> board, int alpha, int beta){
		int score = evaluateHeuristic(board);
		if(depth == 0 || GameEnded(board)){
			if(this.player == player){
				return 500;
			}
			else{
				return -500;
			}
		}
		if(maximizingPlayer) {
			int maxValue = javaMin;
			for(int i = 0; i<64; i++){
				if(board.get(i).state == 0) {
					board.get(i).state = player;
					maxValue = Math.max(maxValue, minimax(3 - player, depth-1, false, board, alpha, beta));
					board.get(i).state = 0;
					alpha = Math.max(alpha, maxValue);
					if (beta <= alpha){
						break;
					}
				}
			}
			return maxValue;
		}
		else {
			int minValue = javaMax;
			for(int i = 0; i<64; i++){
				if(board.get(i).state == 0) {
					if(player == 1){
						board.get(i).state = 2;
					}
					else if(player == 2){
						board.get(i).state = 1;
					}
					minValue = Math.min(minValue, minimax(3 - player, depth-1, false, board, alpha, beta));
					board.get(i).state = 0;
					beta = Math.min(beta, minValue);
					if (beta <= alpha) break;
				}
			}		
			return minValue;
		}
	
	}

	/** The heuristic evaluation function for the current board
       @Return +1000, +100, +10, +1 for EACH 4-, 3-, 2-, 1-in-a-line for computer.
               -1000, -100, -10, -1 for EACH 4-, 3-, 2-, 1-in-a-line for opponent.
               0 otherwise   */
	



	private List<List<positionTicTacToe>> initializeWinningLines()
	{
		//create a list of winning line so that the game will "brute-force" check if a player satisfied any 	winning condition(s).
		List<List<positionTicTacToe>> winningLines = new ArrayList<List<positionTicTacToe>>();
		
		//48 straight winning lines
		//z axis winning lines
		for(int i = 0; i<4; i++)
			for(int j = 0; j<4;j++)
			{
				List<positionTicTacToe> oneWinCondtion = new ArrayList<positionTicTacToe>();
				oneWinCondtion.add(new positionTicTacToe(i,j,0,-1));
				oneWinCondtion.add(new positionTicTacToe(i,j,1,-1));
				oneWinCondtion.add(new positionTicTacToe(i,j,2,-1));
				oneWinCondtion.add(new positionTicTacToe(i,j,3,-1));
				winningLines.add(oneWinCondtion);
			}
		//y axis winning lines
		for(int i = 0; i<4; i++)
			for(int j = 0; j<4;j++)
			{
				List<positionTicTacToe> oneWinCondtion = new ArrayList<positionTicTacToe>();
				oneWinCondtion.add(new positionTicTacToe(i,0,j,-1));
				oneWinCondtion.add(new positionTicTacToe(i,1,j,-1));
				oneWinCondtion.add(new positionTicTacToe(i,2,j,-1));
				oneWinCondtion.add(new positionTicTacToe(i,3,j,-1));
				winningLines.add(oneWinCondtion);
			}
		//x axis winning lines
		for(int i = 0; i<4; i++)
			for(int j = 0; j<4;j++)
			{
				List<positionTicTacToe> oneWinCondtion = new ArrayList<positionTicTacToe>();
				oneWinCondtion.add(new positionTicTacToe(0,i,j,-1));
				oneWinCondtion.add(new positionTicTacToe(1,i,j,-1));
				oneWinCondtion.add(new positionTicTacToe(2,i,j,-1));
				oneWinCondtion.add(new positionTicTacToe(3,i,j,-1));
				winningLines.add(oneWinCondtion);
			}
		
		//12 main diagonal winning lines
		//xz plane-4
		for(int i = 0; i<4; i++)
			{
				List<positionTicTacToe> oneWinCondtion = new ArrayList<positionTicTacToe>();
				oneWinCondtion.add(new positionTicTacToe(0,i,0,-1));
				oneWinCondtion.add(new positionTicTacToe(1,i,1,-1));
				oneWinCondtion.add(new positionTicTacToe(2,i,2,-1));
				oneWinCondtion.add(new positionTicTacToe(3,i,3,-1));
				winningLines.add(oneWinCondtion);
			}
		//yz plane-4
		for(int i = 0; i<4; i++)
			{
				List<positionTicTacToe> oneWinCondtion = new ArrayList<positionTicTacToe>();
				oneWinCondtion.add(new positionTicTacToe(i,0,0,-1));
				oneWinCondtion.add(new positionTicTacToe(i,1,1,-1));
				oneWinCondtion.add(new positionTicTacToe(i,2,2,-1));
				oneWinCondtion.add(new positionTicTacToe(i,3,3,-1));
				winningLines.add(oneWinCondtion);
			}
		//xy plane-4
		for(int i = 0; i<4; i++)
			{
				List<positionTicTacToe> oneWinCondtion = new ArrayList<positionTicTacToe>();
				oneWinCondtion.add(new positionTicTacToe(0,0,i,-1));
				oneWinCondtion.add(new positionTicTacToe(1,1,i,-1));
				oneWinCondtion.add(new positionTicTacToe(2,2,i,-1));
				oneWinCondtion.add(new positionTicTacToe(3,3,i,-1));
				winningLines.add(oneWinCondtion);
			}
		
		//12 anti diagonal winning lines
		//xz plane-4
		for(int i = 0; i<4; i++)
			{
				List<positionTicTacToe> oneWinCondtion = new ArrayList<positionTicTacToe>();
				oneWinCondtion.add(new positionTicTacToe(0,i,3,-1));
				oneWinCondtion.add(new positionTicTacToe(1,i,2,-1));
				oneWinCondtion.add(new positionTicTacToe(2,i,1,-1));
				oneWinCondtion.add(new positionTicTacToe(3,i,0,-1));
				winningLines.add(oneWinCondtion);
			}
		//yz plane-4
		for(int i = 0; i<4; i++)
			{
				List<positionTicTacToe> oneWinCondtion = new ArrayList<positionTicTacToe>();
				oneWinCondtion.add(new positionTicTacToe(i,0,3,-1));
				oneWinCondtion.add(new positionTicTacToe(i,1,2,-1));
				oneWinCondtion.add(new positionTicTacToe(i,2,1,-1));
				oneWinCondtion.add(new positionTicTacToe(i,3,0,-1));
				winningLines.add(oneWinCondtion);
			}
		//xy plane-4
		for(int i = 0; i<4; i++)
			{
				List<positionTicTacToe> oneWinCondtion = new ArrayList<positionTicTacToe>();
				oneWinCondtion.add(new positionTicTacToe(0,3,i,-1));
				oneWinCondtion.add(new positionTicTacToe(1,2,i,-1));
				oneWinCondtion.add(new positionTicTacToe(2,1,i,-1));
				oneWinCondtion.add(new positionTicTacToe(3,0,i,-1));
				winningLines.add(oneWinCondtion);
			}
		
		//4 additional diagonal winning lines
		List<positionTicTacToe> oneWinCondtion = new ArrayList<positionTicTacToe>();
		oneWinCondtion.add(new positionTicTacToe(0,0,0,-1));
		oneWinCondtion.add(new positionTicTacToe(1,1,1,-1));
		oneWinCondtion.add(new positionTicTacToe(2,2,2,-1));
		oneWinCondtion.add(new positionTicTacToe(3,3,3,-1));
		winningLines.add(oneWinCondtion);
		
		oneWinCondtion = new ArrayList<positionTicTacToe>();
		oneWinCondtion.add(new positionTicTacToe(0,0,3,-1));
		oneWinCondtion.add(new positionTicTacToe(1,1,2,-1));
		oneWinCondtion.add(new positionTicTacToe(2,2,1,-1));
		oneWinCondtion.add(new positionTicTacToe(3,3,0,-1));
		winningLines.add(oneWinCondtion);
		
		oneWinCondtion = new ArrayList<positionTicTacToe>();
		oneWinCondtion.add(new positionTicTacToe(3,0,0,-1));
		oneWinCondtion.add(new positionTicTacToe(2,1,1,-1));
		oneWinCondtion.add(new positionTicTacToe(1,2,2,-1));
		oneWinCondtion.add(new positionTicTacToe(0,3,3,-1));
		winningLines.add(oneWinCondtion);
		
		oneWinCondtion = new ArrayList<positionTicTacToe>();
		oneWinCondtion.add(new positionTicTacToe(0,3,0,-1));
		oneWinCondtion.add(new positionTicTacToe(1,2,1,-1));
		oneWinCondtion.add(new positionTicTacToe(2,1,2,-1));
		oneWinCondtion.add(new positionTicTacToe(3,0,3,-1));
		winningLines.add(oneWinCondtion);	
		
		return winningLines;
		
	}

	public aiTicTacToe(int setPlayer){
		player = setPlayer;
	}
}
