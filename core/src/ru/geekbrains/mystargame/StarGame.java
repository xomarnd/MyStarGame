package ru.geekbrains.mystargame;

import com.badlogic.gdx.Game;

import ru.geekbrains.mystargame.screen.MenuScreen;

public class StarGame extends Game {
	
	@Override
	public void create () {	setScreen(new MenuScreen(this));
	}
}
