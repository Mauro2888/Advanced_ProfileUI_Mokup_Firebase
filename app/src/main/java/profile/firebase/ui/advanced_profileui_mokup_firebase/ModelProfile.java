package profile.firebase.ui.advanced_profileui_mokup_firebase;

/**
 * Created by Mauro on 06/01/2018.
 */

public class ModelProfile {
  String name;
  String image;

  public ModelProfile() {
  }

  public ModelProfile(String name, String image) {
    this.name = name;
    this.image = image;
  }

  public String getName() {
    return name;
  }

  public String getImage() {
    return image;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setImage(String image) {
    this.image = image;
  }
}
