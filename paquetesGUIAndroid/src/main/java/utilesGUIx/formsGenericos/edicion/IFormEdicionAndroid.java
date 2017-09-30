package utilesGUIx.formsGenericos.edicion;

import android.app.Activity;
import android.content.Intent;

public interface IFormEdicionAndroid extends IFormEdicion {
	public void onResume();
	public void onActivityResult(int requestCode, int resultCode, Intent data);
	public void startActivityForResult(Intent intent, int requestCode);
	public void setActivity(Activity poActivity);
	public Activity getActivity();
	

}
