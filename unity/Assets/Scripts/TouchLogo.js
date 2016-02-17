#pragma strict

public var lightFront : GameObject;
public var lightBack : GameObject;
public var intensityStrenghtenMultiplier = 4;

private var lightComponentFront : Light;
private var lightComponentBack : Light;
private var intensityOriginal = 0.28;

private static var LOGO_OBJECT_NAME : String = "Logo_green1";

function Start () {

  lightComponentFront = lightFront.GetComponent.<Light>();
  lightComponentBack = lightBack.GetComponent.<Light>();
  intensityOriginal = lightComponentFront.intensity;
}

function Update () {

//  print("Logo touch checks");
  if (Input.touchCount > 0 && isLogoTouched(Input.GetTouch(0).position)) {
    var touch : Touch = Input.GetTouch(0);
    switch (touch.phase) {
      // Record initial touch position.
      case TouchPhase.Began:
        print("Logo touch begin.");
          lightComponentFront.intensity = intensityOriginal * intensityStrenghtenMultiplier;
          lightComponentBack.intensity = intensityOriginal * intensityStrenghtenMultiplier;
//        RenderSettings.ambientIntensity = 2.8; // doesn't work
        break;
      // Determine direction by comparing the current touch position with the initial one.
      case TouchPhase.Moved:

        break;
      // Report that a direction has been chosen when the finger is lifted.
      case TouchPhase.Ended:
        print("Logo touch end.");
          lightComponentFront.intensity = intensityOriginal;
          lightComponentBack.intensity = intensityOriginal;
//        RenderSettings.ambientIntensity = 1.0; // doesn't work
        break;
    }

  } else {
    // There is sometimes a bug when there is a touch phase and application changes its
    // orientation, then touch end is never executed so to be sure we have to reset it.
    if (lightComponentFront.intensity > 1.0) {
      lightComponentBack.intensity = intensityOriginal;
      lightComponentFront.intensity = intensityOriginal;
    }
  }

}

function isLogoTouched(position : Vector2) : boolean {
  var ray: Ray = Camera.main.ScreenPointToRay(position);
  var hit: RaycastHit;
  if (Physics.Raycast(ray, hit)) {
    print("Object hit name: " + hit.collider.gameObject.name);
    print("Touch script on object: " + this.name);
    if (hit.collider.gameObject.name == LOGO_OBJECT_NAME) {
      return true;
    }
  }
  return false;
}