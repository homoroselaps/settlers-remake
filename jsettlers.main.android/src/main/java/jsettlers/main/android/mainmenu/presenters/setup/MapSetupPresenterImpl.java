/*
 * Copyright (c) 2017
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 */

package jsettlers.main.android.mainmenu.presenters.setup;

import java.util.ArrayList;
import java.util.List;

import jsettlers.common.ai.EPlayerType;
import jsettlers.common.player.ECivilisation;
import jsettlers.logic.map.loading.EMapStartResources;
import jsettlers.logic.map.loading.MapLoader;
import jsettlers.logic.player.PlayerSetting;
import jsettlers.main.android.core.GameStarter;
import jsettlers.main.android.mainmenu.presenters.setup.playeritem.Civilisation;
import jsettlers.main.android.mainmenu.presenters.setup.playeritem.PlayerSlotPresenter;
import jsettlers.main.android.mainmenu.presenters.setup.playeritem.PlayerType;
import jsettlers.main.android.mainmenu.presenters.setup.playeritem.PositionChangedListener;
import jsettlers.main.android.mainmenu.presenters.setup.playeritem.StartPosition;
import jsettlers.main.android.mainmenu.presenters.setup.playeritem.Team;
import jsettlers.main.android.mainmenu.views.MapSetupView;

import java8.util.J8Arrays;

/**
 * Created by tompr on 21/01/2017.
 */
public abstract class MapSetupPresenterImpl implements MapSetupPresenter, PositionChangedListener {
	private final MapSetupView view;
	private final GameStarter gameStarter;
	private final MapLoader mapLoader;

	private final List<PlayerSlotPresenter> playerSlotPresenters = new ArrayList<>();

	private PlayerCount playerCount;
	private StartResources startResources;

	public MapSetupPresenterImpl(MapSetupView view, GameStarter gameStarter, MapLoader mapLoader) {
		this.view = view;
		this.gameStarter = gameStarter;
		this.mapLoader = mapLoader;

		createComputerPlayerSlots();
	}

	@Override
	public void initView() {
		view.setNumberOfPlayersOptions(allowedPlayerCounts());
		view.setPlayerCount(playerCount);

		view.setStartResourcesOptions(startResourcesOptions());
		view.setStartResources(new StartResources(EMapStartResources.MEDIUM_GOODS));

		view.setPeaceTimeOptions(peaceTimeOptions());
		view.setMapImage(mapLoader.getImage());
	}

	@Override
	public void updateViewTitle() {
		view.setMapName(mapLoader.getMapName());
	}

	@Override
	public void viewFinished() {
		if (gameStarter.getStartingGame() == null) {
			abort();
		}
	}

	@Override
	public void dispose() {
	}

	protected void abort() {
	}

	@Override
	public void playerCountSelected(PlayerCount item) {
		playerCount = item;
		updateViewItems();
	}

	@Override
	public void startResourcesSelected(StartResources item) {
		startResources = item;
	}

	protected void updateViewItems() {
		view.setItems(getPlayerSlotPresenters(), playerCount.getNumberOfPlayers());
	}

	protected List<PlayerSlotPresenter> getPlayerSlotPresenters() {
		return playerSlotPresenters;
	}

	protected PlayerCount getPlayerCount() {
		return playerCount;
	}

	/**
	 * Get items for the main map options
	 */
	private PlayerCount[] allowedPlayerCounts() {
		int maxPlayers = mapLoader.getMaxPlayers();
		int minPlayers = mapLoader.getMinPlayers();
		int numberOfOptions = maxPlayers - minPlayers + 1;

		PlayerCount[] allowedPlayerCounts = new PlayerCount[numberOfOptions];

		for (int i = 0; i < numberOfOptions; i++) {
			allowedPlayerCounts[i] = new PlayerCount(minPlayers + i);
		}

		return allowedPlayerCounts;
	}

	private StartResources[] startResourcesOptions() {
		return J8Arrays.stream(EMapStartResources.values())
				.map(StartResources::new)
				.toArray(StartResources[]::new);
	}

	private Peacetime[] peaceTimeOptions() {
		return new Peacetime[] { new Peacetime("Without") };
	}

	/**
	 * Player slots setup Sets up the player slots as computer players. Subclasses can then modify the slots for any single or multi player human players.
	 */
	private void createComputerPlayerSlots() {
		playerCount = new PlayerCount(mapLoader.getMaxPlayers());
		PlayerSetting[] playerSettings = mapLoader.getFileHeader().getPlayerSettings();

		for (byte i = 0; i < playerCount.getNumberOfPlayers(); i++) {
			PlayerSlotPresenter playerSlotPresenter = new PlayerSlotPresenter(this);
			PlayerSetting playerSetting = playerSettings[i];

			playerSlotPresenter.setName("Computer " + i);
			playerSlotPresenter.setShowReadyControl(false);

			setComputerSlotPlayerTypes(playerSlotPresenter);
			setSlotCivilisations(playerSlotPresenter, playerSetting);
			setSlotStartPositions(playerSlotPresenter, playerCount.getNumberOfPlayers(), i);
			setSlotTeams(playerSlotPresenter, playerSetting, playerCount.getNumberOfPlayers(), i);

			playerSlotPresenters.add(playerSlotPresenter);
		}
	}

	protected static void setComputerSlotPlayerTypes(PlayerSlotPresenter playerSlotPresenter) {
		playerSlotPresenter.setPossiblePlayerTypes(new PlayerType[] {
				new PlayerType(EPlayerType.AI_VERY_HARD),
				new PlayerType(EPlayerType.AI_HARD),
				new PlayerType(EPlayerType.AI_EASY),
				new PlayerType(EPlayerType.AI_VERY_EASY)
		});
		playerSlotPresenter.setPlayerType(new PlayerType(EPlayerType.AI_VERY_HARD));
	}

	private static void setSlotCivilisations(PlayerSlotPresenter playerSlotPresenter, PlayerSetting playerSetting) {
		playerSlotPresenter.setPossibleCivilisations(new Civilisation[] { new Civilisation(ECivilisation.ROMAN) });

		if (playerSetting.getCivilisation() != null) {
			playerSlotPresenter.setCivilisation(new Civilisation(playerSetting.getCivilisation()));
		} else {
			playerSlotPresenter.setCivilisation(new Civilisation(ECivilisation.ROMAN));
		}
	}

	private static void setSlotStartPositions(PlayerSlotPresenter playerSlotPresenter, int numberOfPlayers, byte orderNumber) {
		playerSlotPresenter.setPossibleStartPositions(numberOfPlayers);
		playerSlotPresenter.setStartPosition(new StartPosition(orderNumber));
	}

	private static void setSlotTeams(PlayerSlotPresenter playerSlotPresenter, PlayerSetting playerSetting, int numberOfPlayers, byte orderNumber) {
		playerSlotPresenter.setPossibleTeams(numberOfPlayers);
		if (playerSetting.getTeamId() != null) {
			playerSlotPresenter.setTeam(new Team(playerSetting.getTeamId()));
		} else {
			playerSlotPresenter.setTeam(new Team(orderNumber));
		}
	}

	/**
	 * PositionChangedListener implementation
	 */
	@Override
	public void positionChanged(PlayerSlotPresenter updatedPlayerSlotPresenter, StartPosition oldPosition, StartPosition newPosition) {
		for (PlayerSlotPresenter playerSlotPresenter : playerSlotPresenters) {
			if (playerSlotPresenter != updatedPlayerSlotPresenter && playerSlotPresenter.getStartPosition().equals(newPosition)) {
				playerSlotPresenter.setStartPosition(oldPosition);
			}
		}
	}
}
