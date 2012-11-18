package com.agh.is.systemmonitor;
import java.io.File;

import org.junit.runners.model.InitializationError;

import roboguice.RoboGuice;
import roboguice.test.RobolectricRoboTestRunner;
import android.app.Application;

import com.google.inject.Injector;
import com.google.inject.util.Modules;
import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.RobolectricTestRunner;


public class AESTestRunner extends RobolectricTestRunner {

	public AESTestRunner(Class<?> testClass)
			throws InitializationError {
		super(testClass, new File("/home/adrian/Desktop/AES_AndroidKlient/AndroidManifest.xml"), new File("/home/adrian/Desktop/AES_AndroidKlient/res/"));
	}
}
