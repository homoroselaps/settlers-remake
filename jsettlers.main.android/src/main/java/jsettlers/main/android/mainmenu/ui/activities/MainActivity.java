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

package jsettlers.main.android.mainmenu.ui.activities;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;

import jsettlers.common.menu.IMapDefinition;
import jsettlers.main.android.R;
import jsettlers.main.android.gameplay.ui.activities.GameActivity_;
import jsettlers.main.android.mainmenu.navigation.Actions;
import jsettlers.main.android.mainmenu.navigation.MainMenuNavigator;
import jsettlers.main.android.mainmenu.ui.fragments.MainMenuFragment;
import jsettlers.main.android.mainmenu.ui.fragments.picker.JoinMultiPlayerPickerFragment;
import jsettlers.main.android.mainmenu.ui.fragments.picker.LoadSinglePlayerPickerFragment;
import jsettlers.main.android.mainmenu.ui.fragments.picker.NewMultiPlayerPickerFragment;
import jsettlers.main.android.mainmenu.ui.fragments.picker.NewSinglePlayerPickerFragment;
import jsettlers.main.android.mainmenu.ui.fragments.setup.JoinMultiPlayerSetupFragment;
import jsettlers.main.android.mainmenu.ui.fragments.setup.NewMultiPlayerSetupFragment;
import jsettlers.main.android.mainmenu.ui.fragments.setup.NewSinglePlayerSetupFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity implements MainMenuNavigator {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSupportFragmentManager().addOnBackStackChangedListener(this::setUpButton);

		if (savedInstanceState != null)
			return;

		getSupportFragmentManager().beginTransaction()
				.add(R.id.frame_layout, MainMenuFragment.create())
				.commit();
	}

	@Override
	protected void onPostCreate(@Nullable Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		setUpButton();
	}

	@OptionsItem(android.R.id.home)
	void homeSelected() {
		getSupportFragmentManager().popBackStack();
	}

	/**
	 * MainMenu Navigation
	 */
	@Override
	public void showNewSinglePlayerPicker() {
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.frame_layout, NewSinglePlayerPickerFragment.newInstance())
				.addToBackStack(null)
				.commit();
	}

	@Override
	public void showLoadSinglePlayerPicker() {
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.frame_layout, LoadSinglePlayerPickerFragment.newInstance())
				.addToBackStack(null)
				.commit();
	}

	@Override
	public void showNewSinglePlayerSetup(IMapDefinition mapDefinition) {
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.frame_layout, NewSinglePlayerSetupFragment.create(mapDefinition))
				.addToBackStack(null)
				.commit();
	}

	@Override
	public void showNewMultiPlayerPicker() {
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.frame_layout, NewMultiPlayerPickerFragment.newInstance())
				.addToBackStack(null)
				.commit();
	}

	@Override
	public void showJoinMultiPlayerPicker() {
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.frame_layout, JoinMultiPlayerPickerFragment.create())
				.addToBackStack(null)
				.commit();
	}

	@Override
	public void showJoinMultiPlayerSetup(IMapDefinition mapDefinition) {
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.frame_layout, JoinMultiPlayerSetupFragment.create(mapDefinition))
				.addToBackStack(null)
				.commit();
	}

	@Override
	public void showNewMultiPlayerSetup(IMapDefinition mapDefinition) {
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.frame_layout, NewMultiPlayerSetupFragment.create(mapDefinition))
				.addToBackStack(null)
				.commit();
	}

	@Override
	public void resumeGame() {
		GameActivity_.intent(this).action(Actions.ACTION_RESUME_GAME).start();
		finish();
	}

	@Override
	public void showGame() {
		GameActivity_.intent(this).start();
		finish();
	}

	@Override
	public void popToMenuRoot() {
		getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
	}

	private void setUpButton() {
		boolean isAtRootOfBackStack = getSupportFragmentManager().getBackStackEntryCount() == 0;
		getSupportActionBar().setDisplayHomeAsUpEnabled(!isAtRootOfBackStack);
	}
}
