package com.agh.is.systemmonitor.screens;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.widget.TextView;

import com.agh.is.systemmonitor.AESTestRunner;
import com.agh.is.systemmonitor.R;

@RunWith(AESTestRunner.class)
public class MainScreenTest {

	private MainScreen mainScreen;

	@Before 
	public void setUp() {
		mainScreen = new MainScreen() {
			@Override
			void initialize() {
			}
		};
		mainScreen.onCreate(null);
	}

	@Test
	public void shouldHaveTitleLabel() {
		//given
		String title = mainScreen.getResources().getString(R.string.title_logo);

		//when
		TextView titleLabel = (TextView)mainScreen.findViewById(R.id.main_screen_logo);

		//then
		assertThat(titleLabel.getText().toString()).isEqualTo(title);
	}

}
