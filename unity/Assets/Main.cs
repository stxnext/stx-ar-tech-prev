using UnityEngine;
using System.Collections;
using System;

public class Main : MonoBehaviour {

    public GameObject logo;
    public GameObject dinosaur;
    public GameObject cat;
    public GameObject cameraGameObject;

    void Start() {
        /**
         * there is a need for cat to be active at beginning, so we have to deactive it here. If cat is not active
         * at beginning it causes strange problem with not being able to activate it.
         * A dinousaur stays active till one of activate functions is executed. It's because the dinosaur has got z axis
         * at minus value and it implicates that there is a little switch visible at the moment of activation but only if it has
         * not been activated before. After first activation everything is fine. So in practice the best choice was to leave
         * it activated and give Unity time to make z axis minus value. At the time when one of the activation functions
         * below is executed the dinosaur has got already z axis reverted.
         * The dinousaur would be with z axis with positive value but then it is turned to the user by its worse side (less
         * effective one). The only way is to negate the z or x axis. So that's why all of this.
         */
        cat.SetActive(false);

//        logo.SetActive(false);
//        cat.SetActive(true);
//        dinosaur.SetActive(false);
//        flipCamera("");
    }

    public void flipCamera(String arg) {
        CameraFlip cameraFlip = cameraGameObject.GetComponent<CameraFlip>();
        cameraFlip.enabled = true;
    }

	public void activateLogo(String arg) {
        dinosaur.SetActive(false);
        cat.SetActive(false);
		logo.SetActive(true);
    }

    public void activateDinosaur(String arg) {
        logo.SetActive(false);
        cat.SetActive(false);
        dinosaur.SetActive(true);
    }

    public void activateCat(String arg) {
        logo.SetActive(false);
        dinosaur.SetActive(false);
        cat.SetActive(true);
    }

}
