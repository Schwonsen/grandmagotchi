package com.uniks.grandmagotchi;

import java.util.Random;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TicTacToeGameActivity extends Activity {

	// this array holds the status of each button whether
	// it is an X (=1), an O(=0), or blank (=2)
	int board[][];

	// Think of it as buttons[ROW][COLUMN]
	Button buttons[][];

	int i, j, k = 0;
	TextView textView;
	AI ai;

	private void init() {
		ai = new AI();
		buttons = new Button[4][4];
		board = new int[4][4];

		textView = (TextView) findViewById(R.id.tv_game_dialogue);
		buttons[1][3] = (Button) findViewById(R.id.one);
		buttons[1][2] = (Button) findViewById(R.id.two);
		buttons[1][1] = (Button) findViewById(R.id.three);
		buttons[2][3] = (Button) findViewById(R.id.four);
		buttons[2][2] = (Button) findViewById(R.id.five);
		buttons[2][1] = (Button) findViewById(R.id.six);
		buttons[3][3] = (Button) findViewById(R.id.seven);
		buttons[3][2] = (Button) findViewById(R.id.eight);
		buttons[3][1] = (Button) findViewById(R.id.nine);

		// set the values of the board to 2 (empty)
		for (i = 1; i <= 3; i++) {
			for (j = 1; j <= 3; j++)
				board[i][j] = 2;
		}
		textView.setText(R.string.tv_click_button);

		// add the click listeners for each button
		for (i = 1; i <= 3; i++) {
			for (j = 1; j <= 3; j++) {
				buttons[i][j].setOnClickListener(new MyClickListener(i, j));
				if (!buttons[i][j].isEnabled()) {
					buttons[i][j].setText(R.string.empty_Field);
					buttons[i][j].setEnabled(true);
				}
			}
		}
	}

	class MyClickListener implements View.OnClickListener {
		int x;
		int y;

		public MyClickListener(int x, int y) {
			this.x = x;
			this.y = y;
		}

		// handle the click event
		public void onClick(View view) {
			if (buttons[x][y].isEnabled()) {
				buttons[x][y].setEnabled(false);
				buttons[x][y].setText(R.string.player_Field);
				buttons[x][y].setTextColor(Color.rgb(200, 0, 0));
				board[x][y] = 0;

				// check to see if the user has won, if not let AI take turn
				if (!checkBoard()) {
					ai.takeTurn();
				}
			}
		}
	}

	// check the board to see if there is a winner
	private boolean checkBoard() {
		boolean gameOver = false;

		// first check all possible combinations to see if the user has won.
		if ((board[1][1] == 0 && board[2][2] == 0 && board[3][3] == 0)
				|| (board[1][3] == 0 && board[2][2] == 0 && board[3][1] == 0)
				|| (board[1][2] == 0 && board[2][2] == 0 && board[3][2] == 0)
				|| (board[1][3] == 0 && board[2][3] == 0 && board[3][3] == 0)
				|| (board[1][1] == 0 && board[1][2] == 0 && board[1][3] == 0)
				|| (board[2][1] == 0 && board[2][2] == 0 && board[2][3] == 0)
				|| (board[3][1] == 0 && board[3][2] == 0 && board[3][3] == 0)
				|| (board[1][1] == 0 && board[2][1] == 0 && board[3][1] == 0)) {

			// user has won
			textView.setText(R.string.player_wins);
			gameOver = true;

		} else if ((board[1][1] == 1 && board[2][2] == 1 && board[3][3] == 1)
				|| (board[1][3] == 1 && board[2][2] == 1 && board[3][1] == 1)
				|| (board[1][2] == 1 && board[2][2] == 1 && board[3][2] == 1)
				|| (board[1][3] == 1 && board[2][3] == 1 && board[3][3] == 1)
				|| (board[1][1] == 1 && board[1][2] == 1 && board[1][3] == 1)
				|| (board[2][1] == 1 && board[2][2] == 1 && board[2][3] == 1)
				|| (board[3][1] == 1 && board[3][2] == 1 && board[3][3] == 1)
				|| (board[1][1] == 1 && board[2][1] == 1 && board[3][1] == 1)) {

			// computer has won
			textView.setText(R.string.cpu_wins);
			gameOver = true;

		}
		// check for empty fields and draw
		else {
			boolean isEmpty = true;
			for (i = 1; i <= 3; i++) {
				for (j = 1; j <= 3; j++) {
					if (board[i][j] == 2) {
						isEmpty = false;
						break;
					}
				}
			}

			if (isEmpty) {
				gameOver = true;
				textView.setText(R.string.play_draw);
			}
		}
		return gameOver;
	}

	// The AI inner class
	private class AI {
		// the computer checks the board and takes its turn
		public void takeTurn() {
			if (board[1][1] == 2
					&& ((board[1][2] == 0 && board[1][3] == 0)
							|| (board[2][2] == 0 && board[3][3] == 0) 
							|| (board[2][1] == 0 && board[3][1] == 0))) {
				markSquare(1, 1);
			} else if (board[1][2] == 2
					&& ((board[2][2] == 0 && board[3][2] == 0) 
							|| (board[1][1] == 0 && board[1][3] == 0))) {
				markSquare(1, 2);
			} else if (board[1][3] == 2
					&& ((board[1][1] == 0 && board[1][2] == 0)
							|| (board[3][1] == 0 && board[2][2] == 0) 
							|| (board[2][3] == 0 && board[3][3] == 0))) {
				markSquare(1, 3);
			} else if (board[2][1] == 2
					&& ((board[2][2] == 0 && board[2][3] == 0) 
							|| (board[1][1] == 0 && board[3][1] == 0))) {
				markSquare(2, 1);
			} else if (board[2][2] == 2
					&& ((board[1][1] == 0 && board[3][3] == 0)
							|| (board[1][2] == 0 && board[3][2] == 0)
							|| (board[3][1] == 0 && board[1][3] == 0) 
							|| (board[2][1] == 0 && board[2][3] == 0))) {
				markSquare(2, 2);
			} else if (board[2][3] == 2
					&& ((board[2][1] == 0 && board[2][2] == 0) 
							|| (board[1][3] == 0 && board[3][3] == 0))) {
				markSquare(2, 3);
			} else if (board[3][1] == 2
					&& ((board[1][1] == 0 && board[2][1] == 0)
							|| (board[3][2] == 0 && board[3][3] == 0) 
							|| (board[2][2] == 0 && board[1][3] == 0))) {
				markSquare(3, 1);
			} else if (board[3][2] == 2
					&& ((board[1][2] == 0 && board[2][2] == 0) 
							|| (board[3][1] == 0 && board[3][3] == 0))) {
				markSquare(3, 2);
			} else if (board[3][3] == 2
					&& ((board[1][1] == 0 && board[2][2] == 0)
							|| (board[1][3] == 0 && board[2][3] == 0) 
							|| (board[3][1] == 0 && board[3][2] == 0))) {
				markSquare(3, 3);
			}
			// There is nothing to block so choose a random square
			else {
				Random rand = new Random();
				int a = rand.nextInt(4);
				int b = rand.nextInt(4);
				while (a == 0 || b == 0 || board[a][b] != 2) {
					a = rand.nextInt(4);
					b = rand.nextInt(4);
				}
				markSquare(a, b);
			}
		}

		// Mark the selected square
		private void markSquare(int x, int y) {
			buttons[x][y].setEnabled(false);
			buttons[x][y].setText(R.string.cpu_Field);
			buttons[x][y].setTextColor(Color.rgb(0, 200, 0));
			board[x][y] = 1;
			checkBoard();
		}
	}
	
	public void btnOnClickGame(View view) {
		init();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tic_tac_toe_game);
		init();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tic_tac_toe_game, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
		init();
		return true;
	}

}
